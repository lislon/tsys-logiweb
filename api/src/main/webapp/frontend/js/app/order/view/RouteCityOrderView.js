/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';
import CityCollection from "app/order/models";
import CityView from "./CityView";
import $ from "jquery";
import "jquery-ui/themes/base/core.css";
import "jquery-ui/themes/base/theme.css";
import "jquery-ui/themes/base/sortable.css";
import "jquery-ui/ui/core";
import "jquery-ui/ui/widgets/sortable";

/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: CityCollection,
    el: $(".route-cities"),

    events: {
        'update-sort': 'updateSort',
    },


    initialize: function (options) {
        // cargo collection required to verify permutations
        this.cargoCollection = options.cargoCollection;

        this.listenTo(this.collection, "add", this.render);
        this.listenTo(this.collection, "remove", this.render);

        this.$el.sortable({
            stop: function( event, ui ) {
                ui.item.trigger('update', ui.item.index());
            },
        });
    },

    isNewSortValid: function (model, newPosition) {

        // case 1: newModel position is on edge
        if (newPosition == 0) {
            return this.canBeFirstInRoute(model);
        }
        if (newPosition == this.collection.length - 1) {
            return this.canBeLastInRoute(model);
        }

        // case 2: newModel position is in middle. So other elements now can be on edge positions.
        var newFirst = _.first(this.collection.without(model));
        if (!this.canBeFirstInRoute(newFirst)) {
            return false;
        }

        var newLast = _.last(this.collection.without(model));
        if (!this.canBeLastInRoute(newLast)) {
            return false;
        }

        return true;
    },

    isCityOrderValid: function () {
        return this.canBeFirstInRoute(this.collection.first()) &&
            this.canBeLastInRoute(this.collection.last());
    },

    // Check that given city can be start of route
    canBeFirstInRoute: function (city) {
        return this.cargoCollection.findWhere({srcCityId: city.get('id')}) != null;
    },
    canBeLastInRoute: function (city) {
        return this.cargoCollection.findWhere({dstCityId: city.get('id')}) != null;
    },

    add: function (city) {
        var cityView = new CityView({ model: city });
        this.$el.append(cityView.render().el);
    },

    //
    // remove: function (city) {
    //     var cityView = new CityView({ model: city });
    //     this.$el.append(cityView.render().el);
    // },

    render: function () {
        this.$el.html("");

        for (var i = 0; i < this.collection.length; i++) {
            this.add(this.collection.at(i));
        }
        this.$el.sortable(this.collection.length > 2 ? "enable" : "disable");

        return this;
    },

    updateSort: function(event, model, position) {

        if (!this.isNewSortValid(model, position)) {
            this.$el.sortable("cancel");
            return;
        }

        this.collection.remove(model);

        this.collection.each(function (model, index) {
            var ordinal = index;
            if (index >= position) {
                ordinal += 1;
            }
            model.set('ordinal', ordinal);
        });

        model.set('ordinal', position);
        this.collection.add(model, {at: position});

        // to update ordinals on server:
        // var ids = this.collection.pluck('id');
        // $('#post-data').html('post ids to server: ' + ids.join(', '));

        this.render();
    }

});
