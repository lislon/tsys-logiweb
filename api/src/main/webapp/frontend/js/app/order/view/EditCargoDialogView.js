/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import Cargo from "app/order/models";
import autocomplete from "app/common/cityAutoComplete";

/**
 * Created by ele on 10/13/16.
 */
export default Backbone.View.extend({
    model: Cargo,
    tagName: "div",

    template: require("templates/order/edit-cargo-template"),
    events: {
        // 'submit': "editCargoSubmit",
    },

    initialize: function () {
        var that = this;
        this.$el.html(this.template(this.model.attributes));
        this.$modal = this.$el.find('#modal');

        this.$modal.on("hidden.bs.modal", function () {
            that.remove();
        });
        this.$modal.on('shown.bs.modal', function () {
            $("#cargoName").focus();
        });

        this.$modal.find('form').on('submit', function (e) {
            if (!e.isDefaultPrevented()) {
                e.preventDefault();
                that.editCargoSubmit();
            }
        })
    },

    editCargoSubmit: function () {
        var that = this;
        this.$el.find('input[name]').each(function () {
            that.model.set($(this).attr('name'), $(this).val());
        });
        // workaround:
        // Make sure id's is numerics, otherwise underscore method like findWhere fails
        that.model.set({
            weight: +this.$el.find('#cargoWeight').val(),
            srcCityId: +this.$el.find('#srcCityId').val(),
            dstCityId: +this.$el.find('#dstCityId').val(),
        });

        // TODO: Change validator to some better alternative
        if (that.model.get('srcCityId') == 0) {
            $('#srcCityId + .help-block').text('Required');
            return;
        }
        if (that.model.get('dstCityId') == 0) {
            $('#dstCityId + .help-block').text('Required');
            return;
        }
        if (that.model.get('dstCityId') == that.model.get('srcCityId')) {
            $('#dstCityId + .help-block').text('Should be different');
            return;
        }

        this.$modal.modal("hide");
        this.trigger("submitted", this.model);
    },

    render: function () {

        $("body").prepend(this.$el);

        // TODO: easyAutocomplete is not works until we call prepend (attach to DOM), but initialization here is not right place

        autocomplete("#srcCityName", "#srcCityId", this.$el);
        autocomplete("#dstCityName", "#dstCityId", this.$el);

        this.$modal.modal("show");
        return this;
    }
});
