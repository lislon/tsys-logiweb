/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import apiUrls from "app/common/apiUrls";

export default class Formatters {
    static actionFormatter(value, row) {
        return [
            '<a class="update" href="' + apiUrls.urlOrderEditPage(row.id) + '" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
            '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
        ].join(' ');
    }

    static routeLengthFormatter(value) {
        return value + ' km';
    }
    static payloadFormatter(value) {
        return value + ' kg';
    }
    static truckFormatter(value, row) {
        if (value != null) {
            return '<a href="' + apiUrls.urlTruckEditPage(row.truckId) + '">' + value + "";
        }
    }

    static dateFormatter(value) {
        var date = new Date(value);
        var month = date.getMonth();
        var day = date.getDate();
        var hours = date.getHours();
        var minutes = date.getMinutes();


        if (hours   < 10) {hours   = "0"+hours;}
        if (minutes < 10) {minutes = "0"+minutes;}
        if (month   < 10) {month   = "0"+month;}
        if (day < 10)       {day = "0"+day;}

        return date.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + minutes;
    }

    static queryParams(params) {
        return {};
    }

}
