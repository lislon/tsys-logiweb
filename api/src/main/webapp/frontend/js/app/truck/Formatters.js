/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import apiUrls from "app/common/apiUrls";

export default class Formatters {

    static actionFormatter(value, row) {
         return [
            '<a class="update" href="' + apiUrls.urlTruckEditPage(row.id) + '" title="Edit truck"><i class="glyphicon glyphicon-edit"></i></a>',
            '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
        ].join(' ');
    }
}
