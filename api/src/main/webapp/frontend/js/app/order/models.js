/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

let App = module.exports = {};

App.Driver = Backbone.Model.extend({
    defaults: {
        id: "", name: "", workedHours: 0
    },
});
App.Order = Backbone.Model.extend({
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

App.Cargo = Backbone.Model.extend({
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

App.Truck = Backbone.Model.extend({
    defaults: {
        id: "", name: "", maxDrivers: 0, capacityTon: 0
    },
});

App.City = Backbone.Model.extend({
    defaults: {
        name: "",
        ordinal: 0,
    }
});

App.CargoCollection = Backbone.Collection.extend({
    model: App.Cargo,
});

App.CityCollection = Backbone.Collection.extend({
    model: App.City,

    // Updates cities list base on cargoCollection
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
                    var newSeqCity = new App.City({
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

        this.trigger('sync');
    },


    comparator: function(model) {
        return model.get('ordinal');
    },

});

App.DriverCollection = Backbone.Collection.extend({
    model: App.Driver,
});

App.TruckCollection = Backbone.Collection.extend({
    model: App.Truck,
});
