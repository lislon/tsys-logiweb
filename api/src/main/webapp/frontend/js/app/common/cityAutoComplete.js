"use strict";

import apiUrls from "app/common/apiUrls";
// import apiUrls from 'easy-autocomplete';

/**
 * Binds an autocomplete for element at $selector.
 *
 * @param selector Selector of text-input element
 * @param selectorId hidden-input element selector for selected id of the city
 * @param $el Context jquery for selectors (Default - all page)
 */
export default function (selector, selectorId, $context = null) {

    $(selector, $context).easyAutocomplete({
        url: phrase => apiUrls.urlCityAutocompleteApi(phrase),
        getValue: "name",
        adjustWidth: false,
        list: {
            match: {
                enabled: true
            },
            onSelectItemEvent: function() {
                let cityId = $(selector, $context).getSelectedItemData().id;
                $(selectorId, $context).val(cityId);
            },
        }
    });
}