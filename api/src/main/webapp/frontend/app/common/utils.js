/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

"use strict"

$(function () {
    var App = window.App = window.App || {};

    App.Utils = {
        parseUrl: function () {
            return _.object(_.compact(_.map(location.search.slice(1).split('&'),
                function (item) {
                    if (item) return item.split('=');
                })));
        },
        cityValidator: function ($el) {
            var matchValue = $el.data("equals") // foo
            if ($el.val() !== matchValue) {
                return "Hey, that's not valid! It's gotta be " + matchValue
            }
        }
    }
});