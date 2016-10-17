/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import TruckCollection from "app/order/models";
import Logger from "js-logger";

/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: TruckCollection,
    el: $(".truck-selector"),
    template: require("templates/order/truck-select-template"),

    initialize: function (options) {
        this.order = options.order;
        this.listenTo(this.collection, "reset", this.render);
        this.listenTo(this.collection, "reset", this.resetSelectionToNone);
    },

    events: {
        'change': 'onTruckSelected',
    },

    resetSelectionToNone: function () {
        if (!this.collection.findWhere({ id: +this.order.get('selectedTruckId') })) {
            Logger.debug("Reset selectedTruckId from TruckListView, because collection changed/reseted");
            this.order.set('selectedTruckId', null);
        }
    },

    onTruckSelected: function () {
        var id = this.$el.val();
        this.order.set('selectedTruckId', id === "" ? null : +id);
    },

    render: function () {

        var $select = this.$el;

        $select.html('');

        if (this.collection.length == 0) {
            $select.append("<option>No available trucks</option>");
        } else {
            $select.append("<option value=''>No truck selected</option>");
            this.collection.forEach(function (truck) {
                $select.append(this.template(truck.attributes));
            }, this);
            Logger.debug("Render trucks done. Selected truck id=" + this.order.get('selectedTruckId'));
            if (this.order.get('selectedTruckId') != null) {
                $select.val(this.order.get('selectedTruckId'));
            }
        }
        $select.prop("disabled", this.collection.length == 0);
        this.$el.selectpicker('refresh')
    }
});
