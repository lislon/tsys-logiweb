/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

export default function (text, type = 'success') {
    let html;
    if (type == 'success') {
        html = require("templates/alert-success")({text: text});
    } else {
        html = require("templates/alert-danger")({text: text});
    }
    let $el = $(".content").prepend(html);
    $el.fadeIn('slow');
};
