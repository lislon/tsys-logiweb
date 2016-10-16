/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import Cargo from "app/order/models";
import EditCargoDialogView from "./EditCargoDialogView";

export default Backbone.View.extend({

    tagName: "tr",
    template: require("templates/order/cargo-item-template"),
    model: Cargo,
    events: {
        "click .edit": "editClicked",
        "click .remove": "removeClicked",
    },

    initialize: function () {
        this.listenTo(this.model, "change", this.render);
        this.listenTo(this.model, "remove", this.remove);
    },

    editClicked: function () {

        var viewEdit = new EditCargoDialogView({ model: this.model });
        viewEdit.render();

        // $modal.modal("show")
        // alert("Clicked" + this.model.get("title"));
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
