/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import apiUrls from "app/common/apiUrls";

export default class Formatters {
    static driverName(value, row) {
        return row.firstName + ' ' + row.lastName;
    }

    static actionFormatter(value, row) {
         return [
            '<a class="update" href="' + apiUrls.urlDriverEditPage(row.id) + '" title="Edit driver"><i class="glyphicon glyphicon-edit"></i></a>',
            '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
        ].join(' ');
    }

    static statusFormatter(value) {
        switch (value) {
            case 'REST':
                return 'Rest';
            case 'DUTY_DRIVE':
                return 'On Duty - Driving';
            case 'DUTY_REST':
                return 'On Duty - Resting';
            default:
                return value;
        }
    }

    static cellStyle(value, row, index) {
        if (value == 'REST') {
            return {
                classes: 'success'
            };
        }
        return {
            classes: 'warning'
        };
    }
}
