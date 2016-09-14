/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

var LIST_URL = CONTEXT_PATH + '/api/truck/list.do';
var EDIT_URL = CONTEXT_PATH + '/truck/edit.do?id=';
var DELETE_URL = CONTEXT_PATH + '/api/truck/delete.do?id=';


var $table = $('#table').bootstrapTable({
    url: LIST_URL,
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    }
});
var $modal = $('#modal').modal({show: false});
var $alert = $('#table-alert').hide();
$(function () {


    // create event
    // $('.create').click(function () {
    //     showModal($(this).text());
    // });
    $modal.find('.submit').click(function () {
        var row = {};
        $modal.find('input[name]').each(function () {
            row[$(this).attr('name')] = $(this).val();
        });
        $.ajax({
            url: LIST_URL + ($modal.data('id') || ''),
            type: $modal.data('id') ? 'put' : 'post',
            contentType: 'application/json',
            data: JSON.stringify(row),
            success: function () {
                $modal.modal('hide');
                $table.bootstrapTable('refresh');
                showAlert(($modal.data('id') ? 'Update' : 'Create') + ' item successful!', 'success');
            },
            error: function () {
                $modal.modal('hide');
                showAlert(($modal.data('id') ? 'Update' : 'Create') + ' item error!', 'danger');
            }
        });
    });
});

function actionFormatter(value, row) {
    return [
        '<a class="update" href="' + EDIT_URL + row.id + '" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
        '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
    ].join(' ');
}
// update and delete events
window.actionEvents = {
    // 'click .update': function (e, value, row) {
    //     showModal($(this).attr('title'), row);
    // },
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
            });
        }
    }
};
