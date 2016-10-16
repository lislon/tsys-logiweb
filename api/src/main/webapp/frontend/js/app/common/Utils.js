/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

"use strict";

export default class Utils {
    static parseUrl() {
        return _.object(_.compact(_.map(location.search.slice(1).split('&'),
            function (item) {
                if (item) return item.split('=');
            })));
    }
}

// $(function () {
//     var App = window.App = window.App || {};
//
//     App.Utils = {
//         cityValidator: function ($el) {
//             var matchValue = $el.data("equals") // foo
//             if ($el.val() !== matchValue) {
//                 return "Hey, that's not valid! It's gotta be " + matchValue
//             }
//         }
//     }
// });