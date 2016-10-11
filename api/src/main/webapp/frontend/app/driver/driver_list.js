/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import './driver.scss';

import apiUrls from 'app/common/apiUrls';
import $ from 'jquery';
// import bindAutoComplete from 'app/common/cityAutoComplete';

const DELETE_URL = CONTEXT_PATH + '/api/drivers/';

let $table = $('#table').bootstrapTable({
    url: apiUrls.urlDriverListApi(),
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    }
});
let $alert = $('#table-alert').hide();

// $(function () {
//     bindAutoComplete("#cityName", "#cityId");
// });


function nameFormatter(value, row) {
    return row.firstName + ' ' + row.lastName;
}

function actionFormatter(value, row) {
    // return [
    //     '<a class="update" href="javascript:" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
    //     '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
    // ].join(' ');
    return [
        '<a class="update" href="' + apiUrls.urlDriverEditPage(row.id) + '" title="Edit driver"><i class="glyphicon glyphicon-edit"></i></a>',
        '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
    ].join(' ');
}

function statusFormatter(value) {
    switch (value) {
        case 'REST': return 'Rest';
        case 'DUTY_DRIVE': return 'On Duty - Driving';
        case 'DUTY_REST': return 'On Duty - Resting';
        default: return value;
    }
}

// update and delete events
window.actionEvents = {
    // 'click .update': function (e, value, row) {
    //     showModal($(this).attr('title'), row);
    // },
    'click .remove': function (e, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: apiUrls.urlDriverDeleteApi(row.id),
                type: 'delete',
                success: function () {
                    $table.bootstrapTable('refresh');
                    showAlert('Delete item successful!', 'success');
                },
                error: function (error, val) {
                    showAlert('Delete item error! ' + error.responseJSON.errorMessage, 'danger');
                }
            });
        }
    }
};


function cellStyle(value, row, index) {
    if (value == 'REST') {
        return {
            classes: 'success'
        };
    }
    return {
        classes: 'warning'
    };
}

export { nameFormatter, actionFormatter, statusFormatter, cellStyle }
