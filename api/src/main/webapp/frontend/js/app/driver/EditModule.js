/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import $ from "jquery";
import autocomplete from "app/common/cityAutoComplete";

export default class EditModule {
    static initialize() {
        $("#firstName, #lastName")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-maxlength", "255")
            .attr("data-parsley-type", "alphanum");

        $("#personalCode")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-maxlength", "10")

        $("#cityName")
            .attr("data-parsley-required", "true");

        $("#primary-form").parsley();

        $.listen('parsley:field:error', function(ParsleyField) {
            ParsleyField.$element.closest('div').addClass('has-error');
        });
        $.listen('parsley:field:success', function(ParsleyField) {
            ParsleyField.$element.closest('div').removeClass('has-error');
        });

        autocomplete("#cityName", "#cityId");
    }
}
