/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import $ from "jquery";
import apiUrls from "app/common/apiUrls";
import showAlert from "app/common/showAlert";
import formatters from "./Formatters";
import "bootstrap-table";

// import './driver.scss';

export default class ListModule {
    static initialize() {
        $('#table').bootstrapTable({
            url: apiUrls.urlDriverListApi(),
            onLoadError: function (status, res) {
                showAlert('Error loading data from table!', 'danger');
            }
        });

        // bootstrap table way to bind events
        window.actionEvents = {
            'click .remove': ListModule.deleteItemClick
        };

        // TODO: Finish migrate from confirm to modal window someday.
        // // Bind click to OK button within popup
        // $('#confirm-delete').on('click', '.btn-ok', function(e) {
        //
        //     var $modalDiv = $(e.delegateTarget);
        //     var id = $(this).data('recordId');
        //
        //     $modalDiv.addClass('loading');
        //     $.post('/api/record/' + id).then(function() {
        //         $modalDiv.modal('hide').removeClass('loading');
        //     });
        // });
        //
        // // Bind to modal opening to set necessary data properties to be used to make request
        // $('#confirm-delete').on('show.bs.modal', function(e) {
        //     var data = $(e.relatedTarget).data();
        //     $('.title', this).text(data.recordTitle);
        //     $('.btn-ok', this).data('recordId', data.recordId);
        // });
    }

    static deleteItemClick(event, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: apiUrls.urlDriverDeleteApi(row.id),
                type: 'delete',
                success: function () {
                    $('#table').bootstrapTable('refresh');
                    showAlert('Deleted \"' + formatters.driverName(value, row) + '\"', 'success');
                },
                error: function (error, val) {
                    showAlert('Delete item error! ' + error.responseJSON.errorMessage, 'danger');
                }
            });
        }
    }
}
