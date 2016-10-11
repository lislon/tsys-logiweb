/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

var LIST_URL = CONTEXT_PATH + '/api/order/list';
var EDIT_URL = CONTEXT_PATH + '/order/edit?id=';
var DELETE_URL = CONTEXT_PATH + '/api/order/delete?id=';


var $table = $('#table').bootstrapTable({
    url: LIST_URL,
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    }
});
var $modal = $('#modal').modal({show: false});
var $alert = $('#table-alert').hide();

function queryParams(params) {
    return {};
}
function actionFormatter(value, row) {
    return [
        '<a class="update" href="' + EDIT_URL + row.id + '" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
        '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
    ].join(' ');
}

function routeLengthFormatter(value) {
    return value + ' km';
}
function payloadFormatter(value) {
    return value + ' kg';
}
function truckFormatter(value, row) {
    if (value != null) {
        return '<a href="' + CONTEXT_PATH + '/truck/edit?id=' + row.truckId + '">' + value + "";
    }
}

function dateFormatter(value) {
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



// update and delete events
window.actionEvents = {
    'click .remove': function (e, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: DELETE_URL + row.id,
                type: 'delete',
                success: function () {
                    $table.bootstrapTable('refresh');
                    showAlert('Order deleted', 'success');
                },
                error: function () {
                    showAlert('Delete order error!', 'danger');
                }
            })
        }
    }
};

function showAlert(title, type) {
    $alert.attr('class', 'alert alert-' + type || 'success')
        .html('<i class="glyphicon glyphicon-check"></i> ' + title).show();
    setTimeout(function () {
        $alert.hide();
    }, 3000);
}