/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import Cargo from "app/order/models";
import EditCargoDialogView from "./EditCargoDialogView";
import App from "app/order/App";

export default Backbone.View.extend({

    tagName: "tr",
    template: require("templates/order/cargo-item-template"),
    model: Cargo,
    events: {
        "click .edit": "editClicked",
        "click .remove": "removeClicked",
    },

    initialize: function () {
        this.listenTo(this.model, "change reset", this.render);
        this.listenTo(this.model, "remove", this.remove);
    },

    editClicked: function () {
        var viewEdit = new EditCargoDialogView({ model: this.model });
        this.listenTo(viewEdit, "submitted", model => App.trigger("cargo.changed"));
        viewEdit.render();
    },

    removeClicked: function () {
        if (confirm('Are you sure to delete this item?')) {
            this.model.destroy();
        }
    },

    render: function() {
        this.$el.html(this.template(this.model.attributes));
        return this;
    },
});
