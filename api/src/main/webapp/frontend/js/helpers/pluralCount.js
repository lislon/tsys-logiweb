/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

'use strict';

/**
 * Created by ele on 10/14/16.
 */
export default function(number, singular, plural) {
    return number + ' ' + (number === 1 ? singular : (typeof plural === 'string' ? plural : singular + 's'));
};