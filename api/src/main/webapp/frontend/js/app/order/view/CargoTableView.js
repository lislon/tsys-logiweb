/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';
import {Cargo, CargoCollection} from "app/order/models";
import EditCargoDialogView from "app/order/view/EditCargoDialogView";
import CargoRowView from "app/order/view/CargoRowView";
import App from "app/order/App";


/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: CargoCollection,
    el: $("#cargoTable tbody"),

    initialize: function () {
        this.listenTo(this.collection, "add reset", this.render);

        var that = this;
        $(".addCargo").click(function () {
            var newCargo = new Cargo({title: ''});
            var cargoDialog = new EditCargoDialogView({ model: newCargo });
            that.listenTo(cargoDialog, "submitted", that.onNewCargo);
            cargoDialog.render();
        });
    },

    onNewCargo: function (cargo) {
        this.collection.add(cargo);
        App.trigger("cargo.changed");
    },

    // Re-render the titles of the t item.
    render: function() {

        this.$el.html("");

        for (var i = 0; i < this.collection.length; i++) {
            var rowView = new CargoRowView({ model: this.collection.at(i) });
            this.$el.append(rowView.$el);
            rowView.render();
        }

        // this.input = this.$('.edit');
        return this;
    },
});
