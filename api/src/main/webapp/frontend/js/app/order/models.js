/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';
import App from "app/order/App";

let Model = module.exports = {};

Model.Driver = Backbone.Model.extend({
    defaults: {
        id: "", name: "", workedHours: 0
    },
});
Model.Order = Backbone.Model.extend({
    defaults: {
        cargoCollection: {},
        cityOrderCollection: {},
        selectedTruckId : null,
        selectedDriversCollection: {},
        routeLength: null,
        requiredCapacity: null,
    },

    getMaxDrivers: function (truckCollection) {
        var truckId = this.get('selectedTruckId');
        if (truckId != null) {
            return truckCollection.findWhere({id: truckId}).get('maxDrivers')
        }
        return null;
    }
});

Model.Cargo = Backbone.Model.extend({
    defaults: {
        name: "",
        title: "",
        weight: 0,
        srcCityId: 0,
        srcCityName: "",
        dstCityId: 0,
        dstCityName: "",
    }
});

Model.Truck = Backbone.Model.extend({
    defaults: {
        id: "", name: "", maxDrivers: 0, capacityTon: 0
    },
});

Model.City = Backbone.Model.extend({
    defaults: {
        name: "",
        ordinal: 0,
    }
});

Model.CargoCollection = Backbone.Collection.extend({
    model: Model.Cargo,
});

Model.CityCollection = Backbone.Collection.extend({
    model: Model.City,

    // Updates cities list base on cargoCollection
    // called when cargo row is changed or cityOrder is dragged
    updateCities: function (cargoCollection) {

        var oldSeqCities = this.pluck('id');

        // Loop through cargoes. For each cargo check that it exists in collection array.
        // If cargo city is not found in collection array, append it to end of collection.
        _.forEach(['src', 'dst'], function (dir) {
            cargoCollection.forEach(function (cargo) {

                var existingCity = this.find({id: +cargo.get(dir + 'CityId')});
                // If city from cargo collection is not present in sequence collection
                if (existingCity == null) {
                    // Add it to collection
                    var newSeqCity = new Model.City({
                        name: cargo.get(dir + 'CityName'),
                        id: +cargo.get(dir + 'CityId'),
                        ordinal: this.length,
                    });
                    this.add(newSeqCity);
                } else {
                    // existing city
                    oldSeqCities = _.without(oldSeqCities, existingCity.get('id'));
                }
            }, this);
        }, this);

        // Remove cities from collection that are not present in cargoes
        _.forEach(oldSeqCities, function (id) {
            this.remove(this.where({'id': id}))
        }, this);
    },

    comparator: function(model) {
        return model.get('ordinal');
    },

});

Model.DriverCollection = Backbone.Collection.extend({
    model: Model.Driver,
});

Model.TruckCollection = Backbone.Collection.extend({
    model: Model.Truck,
});
