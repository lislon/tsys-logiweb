/*
 * Copyright (c) 2016. 
 * Igor Avdeev
 */

var LIST_URL = CONTEXT_PATH + '/api/driver/list.do';
var EDIT_URL = CONTEXT_PATH + '/api/driver/edit.do?id=';
var DELETE_URL = CONTEXT_PATH + '/api/driver/edit.do?id=';

var $modal = $('.modal').modal({show: false});
var $table = $('#table').bootstrapTable({
    url: LIST_URL,
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    }
});
var $alert = $('#table-alert').hide();

$(function () {

    App.Utils.cityAutocomplete("#cityName", "#cityId");

    $modal.on('shown.bs.modal', function () {
        $modal.find('input[name]').first().focus();
    });

    $('.create').click(function () {
        showModal('New driver');
    });
});

onSubmit = function(e) {
    if (e.isDefaultPrevented()) {
        return;
    }
    e.preventDefault();

    var row = {};
    $modal.find('input[name],select[name]').each(function () {
        row[$(this).attr('name')] = $(this).val();
    });
    delete row.cityName;

    $.ajax({
        url: EDIT_URL + ($modal.data('id') || ''),
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(row),
        success: function () {
            $modal.modal('hide');
            $table.bootstrapTable('refresh');
            showAlert(($modal.data('id') ? 'Update' : 'Create') + ' item successful!', 'success');
        },
        error: function (error) {
            $modal.modal('hide');
            showAlert(($modal.data('id') ? 'Update' : 'Create') + ' item error (' + error.responseJSON.errorMessage + ')!', 'danger');
        }
    });
}

function nameFormatter(value, row) {
    return row.firstName + ' ' + row.lastName;
}

function actionFormatter(value, row) {
    return [
        '<a class="update" href="javascript:" title="Update Item"><i class="glyphicon glyphicon-edit"></i></a>',
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
    'click .update': function (e, value, row) {
        showModal($(this).attr('title'), row);
    },
    'click .remove': function (e, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: DELETE_URL + row.id,
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


function showModal(title, row) {
    row = row || {
            id: '',
            firstName: '',
            lastName: '',
            personalCode: '',
            hoursWorked: 0,
            status: 'REST',
            city: {
                id: 0,
                name: ''
            },
        }; // default row value
    $modal.data('id', row.id);
    $modal.find('.modal-title').text(title);
    for (var name in row) {
        $modal.find('input[name="' + name + '"]').val(row[name]);
    }
    $modal.find('input[name=cityId]').val(row.city.id);
    $modal.find('input[name=cityName]').val(row.city.name);
    $modal.find('select[name=status]').selectpicker('val', row.status);
    $modal.find('form').validator("destroy");
    $modal.find('form').validator().on('submit', onSubmit);
    $modal.modal('show');
}


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