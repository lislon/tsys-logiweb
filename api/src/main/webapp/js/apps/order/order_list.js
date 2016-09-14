/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

var LIST_URL = CONTEXT_PATH + '/api/order/list.do';
var EDIT_URL = CONTEXT_PATH + '/order/edit.do?id=';
var DELETE_URL = CONTEXT_PATH + '/api/order/delete.do?id=';


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

function dateFormatter(value) {
    var date = new Date(value);
    return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate();
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
                    showAlert('Delete item successful!', 'success');
                },
                error: function () {
                    showAlert('Delete item error!', 'danger');
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