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

    refreshCitiesOrder: function () {
        // console.log("refreshCitiesOrder", this.order.get('cargoCollection'));

        this.order.get('cityOrderCollection').updateCities(this.order.get('cargoCollection'));
    },

    refreshMetaAndGetDrivers: function () {
        console.log("refreshMetaAndGetDriver");

        var request = RouteDataConverter.convert(
            this.order.get('cargoCollection'),
            this.order.get('cityOrderCollection').pluck('id'));

        $.ajax(apiUrls.urlOrderRouteMetaApi(), {
            method: 'POST',
            context: this,
            data: JSON.stringify(request),
            contentType: "application/json",
        }).done(function (json) {
            console.log("Route meta arrived. " + json.trucks.length + " trucks");
            this.order.set('routeLength', json.length);
            this.order.set('requiredCapacity', json.requiredCapacity);
            this.trucksCollection.reset(json.trucks);
        }).fail(function (jqXhr, textStatus) {
            showAlert("Ajax error:" + jqXhr, "danger");
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

            this.refreshCitiesOrder();

            return true;
        }
        return false;
    },

    start: function () {

        this.bootstrap();


        // refresh cities when something changes in cargoes
        this.listenTo(this.order.get('cargoCollection'), [
            'update', 'reset',
            'change:srcCityName',
            'change:dstCityName'
        ].join(' '), this.refreshCitiesOrder);

        this.listenTo(this.order.get('cargoCollection'), [
            'update', 'reset',
            'change:weight',
            'change:srcCityName',
            'change:dstCityName'
        ].join(' '), this.refreshMetaAndGetDrivers);

        // update meta when changing city order
        this.listenTo(this.order.get('cityOrderCollection'), 'sync', this.refreshMetaAndGetDrivers);

        this.listenTo(this.order, 'change:selectedTruckId', this.updateDriversList);

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
            viewRouteSummary.render();
            viewTruckList.render();
            viewDriverList.render();
        }
    },


    updateDriversList: function () {

        console.log("Try update drivers list");

        if (this.order.get('selectedTruckId') == null) {
            this.driversCollection.reset();
            console.log("Truck not selected. Reset drivers list");
            return;
        }
        // hack check
        if (!this.viewCities.isCityOrderValid()) {
            showAlert("First or last city is not valid. Please change order.", "danger");
            return;
        }


        if (this.order.get('routeLength') != null && this.trucksCollection.length > 0) {

            console.log("Ajax request drivers...");

            // truck selected, load the drivers
            $.ajax(apiUrls.urlDriverListAvailableApi(), {
                context: this,
                data: {
                    cityId: this.order.get('cityOrderCollection').first().get('id'),
                    routeLength: this.order.get('routeLength'),
                    maxDrivers: this.order.getMaxDrivers(this.trucksCollection),
                }
            })
                .done(function (driverList) {
                    console.log("List of drivers arrived ", driverList);
                    this.driversCollection.reset(driverList);
                })
                .fail(function (jqXhr) {
                    showAlert("ajax error: " + jqXhr.errorText, "danger");
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
        var id = Utils.parseUrl().id;
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
            .fail(function (jqXhr, text) {
                showAlert("ajax error: " + jqXhr.errorText, 'danger');
            });
    }

});

export default OrderEditController;