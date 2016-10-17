/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

import DriverCollection from "app/order/models";

'use strict';
/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: DriverCollection,
    el: $(".driver-selector"),
    template: require("templates/order/driver-select-template"),

    initialize: function (options) {
        this.order = options.order;

        // truckCollection is needed just to get order.maxDrivers
        this.trucksCollection = options.trucksCollection;
        this.listenTo(this.collection, "update reset", this.render);
        this.listenTo(this.order, "change:selectedTruckId", this.resetSelectionToNone);
    },

    events: {
        'change': 'driverSelected',
    },

    resetSelectionToNone: function () {
        this.$el.selectpicker('deselectAll');
    },

    driverSelected: function () {
        var drivers = this.$el.val();
        if (typeof drivers !== 'object') {
            drivers = new Array(drivers);
        }

        var selectedDrivers = _.map(drivers, function (id) {
            return this.collection.findWhere({ id: +id })
        }, this);


        this.order.get('selectedDriversCollection').set(selectedDrivers);
    },

    render: function () {
        var $select = this.$el;



        $select.html("");
        if (this.collection.length == 0) {
            $select.append("<option>No available drivers</option>");
        } else {
            this.collection.forEach(function (driver) {
                $select.append(this.template(driver.attributes));
            }, this)
        }
        $select.prop("disabled", this.collection.length == 0);
        // set max variants to choose
        if (this.order.get('selectedTruckId') != null) {
            this.$el.selectpicker({
                maxOptions: this.order.getMaxDrivers(this.trucksCollection)
            });
            if (this.order.get('selectedDriversCollection')) {
                $select.val(this.order.get('selectedDriversCollection').pluck('id'));
            }
        }

        this.$el.selectpicker('refresh');
    }
});
