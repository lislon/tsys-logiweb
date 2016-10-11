/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

$(function () {
    $('#city').easyAutocomplete({
         url: function(phrase) {
             return CONTEXT_PATH + "/api/city/autocomplete?q=" + phrase + "&format=json";
         },
        getValue: "name",
        list: {
            match: {
                enabled: true
            },
            onSelectItemEvent: function() {
                var cityId = $("#city").getSelectedItemData().id;

                $("#city_id").val(cityId);
            },
        },
        theme: "bootstrap"
    });
});

