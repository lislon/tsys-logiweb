/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

var API_URL = CONTEXT_PATH + '/api/truck/list.do';


var $table = $('#table').bootstrapTable({
    url: API_URL,
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    }
});
var $modal = $('#modal').modal({show: false}),
    $alert = $('.alert').hide();
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
            url: API_URL + ($modal.data('id') || ''),
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
function queryParams(params) {
    return {};
}
function actionFormatter(value, row) {
    return [
        '<a class="update" href="' + CONTEXT_PATH +'/truck/edit?id=' + row.id + '" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
        '<a class="remove" href="javascript:" title="Delete Item"><i class="glyphicon glyphicon-remove-circle"></i></a>',
    ].join('');
}
// update and delete events
window.actionEvents = {
    // 'click .update': function (e, value, row) {
    //     showModal($(this).attr('title'), row);
    // },
    'click .remove': function (e, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: API_URL + row.id,
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
function showModal(title, row) {
    row = row || {
            id: '',
            name: '',
            stargazers_count: 0,
            forks_count: 0,
            description: ''
        }; // default row value
    $modal.data('id', row.id);
    $modal.find('.modal-title').text(title);
    for (var name in row) {
        $modal.find('input[name="' + name + '"]').val(row[name]);
    }
    $modal.modal('show');
}
function showAlert(title, type) {
    $alert.attr('class', 'alert alert-' + type || 'success')
        .html('<i class="glyphicon glyphicon-check"></i> ' + title).show();
    setTimeout(function () {
        $alert.hide();
    }, 3000);
}