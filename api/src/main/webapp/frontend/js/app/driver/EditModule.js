/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import $ from "jquery";
import autocomplete from "app/common/cityAutoComplete";
import Utils from "app/common/Utils";

export default class EditModule {
    static initialize() {

        $("#email")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-type", "email");

        if (Utils.getIdFromUrl() == null) {
            $("#password")
                .attr("data-parsley-required", "true")
                .attr("data-parsley-minlength", "3");
        }

        $("#firstName, #lastName")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-maxlength", "255")
            .attr("data-parsley-type", "alphanum");

        $("#personalCode")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-maxlength", "10")

        $("#cityName")
            .attr("data-parsley-required", "true");

        $("#primary-form").parsley({
            errorsContainer: function(ParsleyField) {
                var formgroup = ParsleyField.$element.closest('.form-group');

                if (formgroup.length > 0) {
                    return formgroup;
                }

                return parsleyField;
            }
        });


        window.Parsley.on('field:error', function(ParsleyField) {
            ParsleyField.$element.closest('.form-group').addClass('has-error');
        });
        window.Parsley.on('field:success', function(ParsleyField) {
            ParsleyField.$element.closest('.form-group').removeClass('has-error');
        });

        $("#gen-pass").click(function () {
            let generator = require('generate-password');
            let password = generator.generate({
                length: 10,
                numbers: true
            });
            $("#password").val(password);
            $("#password").attr("type", "text");
        });


        autocomplete("#cityName", "#cityId");
    }
}
