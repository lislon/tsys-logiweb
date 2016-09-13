"use strict"
var App = App || {};
$(function () {
    App.Utils = {
        cityAutocomplete: function (selector, selectorId, $el) {
            if ($el == null) {
                $el = $;
            }

            $el(selector).easyAutocomplete({
                url: function(phrase) {
                    return CONTEXT_PATH + "/api/city/autocomplete.do?q=" + phrase + "&format=json";
                },
                getValue: "name",
                adjustWidth: false,
                list: {
                    match: {
                        enabled: true
                    },
                    onSelectItemEvent: function() {
                        var cityId = $el(selector).getSelectedItemData().id;

                        $el(selectorId).val(cityId);
                    },
                }
            });
        },
        parseUrl: function () {
            return _.object(_.compact(_.map(location.search.slice(1).split('&'),
                function (item) {
                    if (item) return item.split('=');
                })));
        },
    }
});