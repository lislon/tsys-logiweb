/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import {CargoCollection, DriverCollection, CityCollection, TruckCollection, Order} from "./models";
import CargoTableView from "./view/CargoTableView";
import RouteSummaryView from "./view/RouteSummaryView";
import TruckListView from "./view/TruckListView";
import DriverListView from "./view/DriverListView";
import RouteCityOrderView from "./view/RouteCityOrderView";
import Utils from "app/common/Utils";
import apiUrls from "app/common/apiUrls";
import showAlert from "app/common/showAlert";
import "./order.css";
import RouteDataConverter from "./RouteDataConverter";
import App from "app/order/App";
import Logger from "js-logger";


    let OrderEditController = function (options) {
    options || (options = {});
    this.initialize.apply(this, [ options ]);
};

_.extend(OrderEditController.prototype, Backbone.Events, {

    initialize: function (options) {

        this.order = new Order({
            cargoCollection: new CargoCollection(),
            driverCollection: new DriverCollection(),
            selectedDriversCollection: new DriverCollection(),
            cityOrderCollection: new CityCollection(),
        });

        this.trucksCollection = new TruckCollection();
        this.driversCollection = new DriverCollection();
    },

    /**
     * Called when cargo src/dst changes and updates cities order pane
     * Then calls route information on server side
     */
    updateCitiesListAfterCargoChanges: function () {
        //
        this.order.get('cityOrderCollection').updateCities(this.order.get('cargoCollection'));
    },

    refreshRouteLengthAndGetTrucks: function () {
        Logger.debug("refreshMetaAndGetDriver");

        var request = RouteDataConverter.convert(
            this.order.get('cargoCollection'),
            this.order.get('cityOrderCollection').pluck('id'));

        $.ajax(apiUrls.urlOrderRouteMetaApi(Utils.getIdFromUrl()), {
            method: 'POST',
            context: this,
            data: JSON.stringify(request),
            contentType: "application/json",
        }).done(function (json) {
            Logger.debug("Route meta arrived. " + json.trucks.length + " trucks");
            this.order.set('routeLength', json.length);
            this.order.set('requiredCapacity', json.requiredCapacity);

            this.order.set('selectedTruckId', null);
            this.order.get('selectedDriversCollection').reset();
            this.trucksCollection.reset(json.trucks);


        }).fail(function (jqXhr, textStatus) {
            showAlert(jqXhr.responseText, "danger");
        })
    },

    bootstrap: function () {
        if (window.bootstrap != null) {
            this.trucksCollection.reset(window.bootstrap.truckCollection);
            this.driversCollection.reset(window.bootstrap.driverCollection);

            this.order.get('cargoCollection').reset(window.bootstrap.cargoCollection);
            this.order.get('selectedDriversCollection').reset(window.bootstrap.selectedDriverCollection);
            this.order.set('routeLength', window.bootstrap.routeLength);
            this.order.set('requiredCapacity', window.bootstrap.requiredCapacity);
            this.order.set('selectedTruckId', window.bootstrap.selectedTruckId);

            this.updateCitiesListAfterCargoChanges();

            return true;
        }
        return false;
    },

    /**
     * Initialize form
     */
    start: function () {

        this.bootstrap();

        // update meta when changing city order
        this.listenTo(this.order, 'change:selectedTruckId', this.updateDriversList);

        // App.on("driver.changed", this.updateDriversList);
        this.listenTo(App, "cargo.changed", this.updateCitiesListAfterCargoChanges);
        this.listenTo(App, "cargo.changed", this.refreshRouteLengthAndGetTrucks);
        this.listenTo(App, "cityOrder.changedByHand", this.refreshRouteLengthAndGetTrucks);


        // cargo table
        var viewCargoes = new CargoTableView({collection: this.order.get('cargoCollection')});
        // cities order
        this.viewCities = new RouteCityOrderView({
            collection: this.order.get('cityOrderCollection'),
            cargoCollection: this.order.get('cargoCollection')
        });

        // order length info
        var viewRouteSummary = new RouteSummaryView({ model: this.order });

        var viewTruckList = new TruckListView({ collection: this.trucksCollection, order: this.order });
        var viewDriverList = new DriverListView({ collection: this.driversCollection, order: this.order, trucksCollection: this.trucksCollection });

        $("#submit").click(_.bind(this.onSubmitClicked, this));

        if (window.bootstrap != null) {
            viewCargoes.render();
            this.viewCities.render();
        }
        viewRouteSummary.render();
        viewTruckList.render();
        viewDriverList.render();
    },


    updateDriversList: function () {

        if (this.order.get('selectedTruckId') == null) {
            this.driversCollection.reset();
            Logger.debug("Truck not selected. Reset drivers list");
            return;
        }
        // hack check
        if (!this.viewCities.isCityOrderValid()) {
            showAlert("First or last city is not valid. Please change order.", "danger");
            return;
        }


        if (this.order.get('routeLength') != null && this.trucksCollection.length > 0) {

            Logger.debug("Ajax request drivers...");

            // truck selected, load the drivers
            $.ajax(apiUrls.urlDriverListAvailableApi(Utils.getIdFromUrl()), {
                context: this,
                data: {
                    cityId: this.order.get('cityOrderCollection').first().get('id'),
                    routeLength: this.order.get('routeLength'),
                    maxDrivers: this.order.getMaxDrivers(this.trucksCollection),
                }
            })
                .done(function (driverList) {
                    Logger.debug("List of drivers arrived ", driverList);
                    this.driversCollection.reset(driverList);
                })
                .fail(function (jqXhr) {
                    showAlert("ajax error: " + jqXhr.responseText, "danger");
                });
        }
    },

    onSubmitClicked: function () {
        var request = RouteDataConverter.convert(
            this.order.get('cargoCollection'),
            this.order.get('cityOrderCollection').pluck('id'));

        request.selectedTruckId = this.order.get('selectedTruckId');
        request.selectedDrivers = this.order.get('selectedDriversCollection').pluck('id');

        // truck selected, load the drivers
        var id = Utils.getIdFromUrl();
        $.ajax(id == null ? apiUrls.urlOrderCreateApi() : apiUrls.urlOrderUpdateApi(id), {
            context: this,
            method: "POST",
            data: JSON.stringify(request),
            contentType: "application/json",
        })
            .done(function () {
                showAlert("Saved", "success");
                // give time to user to see success message
                setTimeout(function(){
                    window.location.replace(apiUrls.urlOrderListPage());
                }, 500);
            })
            .fail(function (jqXhr) {
                showAlert("ajax error: " + jqXhr.responseText, 'danger');
            });
    }

});

export default OrderEditController;