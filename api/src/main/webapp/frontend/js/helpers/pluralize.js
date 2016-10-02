/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

export default function(number, singular, plural) {
    return number === 1 ? singular : (typeof plural === 'string' ? plural : singular + 's');
}