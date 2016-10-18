/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

import csrfSetup from 'app/common/csrfSetup';

'use strict';
export function load(name, cb) {
    require.ensure([], function (require) {
        cb(require.context("./app", true, /^.+$/)('./' + name).default);
    });
}

$(csrfSetup);