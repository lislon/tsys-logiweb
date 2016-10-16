/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';
import City from "app/order/models";

/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: City,
    tagName: 'li',
    className: 'panel panel-info',
    template: require("templates/order/city-li-template"),
    events: {
        'update' : 'updateSort',
    },

    updateSort: function(event, index) {
        this.$el.trigger('update-sort', [this.model, index]);
    },

    render: function () {
        this.$el.html(this.template(this.model.attributes));
        return this;
    }
});
