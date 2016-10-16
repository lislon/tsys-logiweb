/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import Order from "app/order/models";

/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: Order,
    el: $(".route-summary"),
    template: require("templates/order/route-summary-template"),

    initialize: function () {
        this.listenTo(this.model, "change:routeLength change:requiredCapacity", this.render);
        // this.listenTo(this.collection, "remove", this.render);
    },

    render: function () {
        this.$el.html(this.template(this.model.attributes));
        return this;
    },
});
