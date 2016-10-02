/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

import $ from "jquery";
import initCityAutocomplete from "app/common/cityAutoComplete";

export default class EditModule {
    static initialize() {
        $("#name")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-pattern", "[A-Z]{2}\\d{5}");

        $("#maxDrivers")
            .attr("data-parsley-required", "true");

        $("#capacityKg")
            .attr("data-parsley-required", "true")
            .attr("data-parsley-type", "integer")
            .attr("data-parsley-range", "[1000,40000]");



        $("#cityName")
            .attr("data-parsley-required", "true");

        $("#primary-form").parsley();

        $.listen('parsley:field:error', function(ParsleyField) {
            ParsleyField.$element.closest('div').addClass('has-error');
        });
        $.listen('parsley:field:success', function(ParsleyField) {
            ParsleyField.$element.closest('div').removeClass('has-error');
        });

        initCityAutocomplete("#cityName", "#cityId");
    }
}
