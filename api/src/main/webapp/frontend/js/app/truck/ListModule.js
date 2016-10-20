/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */
'use strict';

import $ from "jquery";
import apiUrls from "app/common/apiUrls";
import showAlert from "app/common/showAlert";
import "bootstrap-table";

// import './driver.scss';

export default class ListModule {
    static initialize() {
        $('#table').bootstrapTable({
            url: apiUrls.urlTruckListApi(),
            onLoadError: function (status, res) {
                showAlert('Error loading data from table!', 'danger');
            }
        });

        // bootstrap table way to bind events
        window.actionEvents = {
            'click .remove': ListModule.deleteItemClick
        };
    }

    static deleteItemClick(event, value, row) {
        if (confirm('Are you sure to delete this item?')) {
            $.ajax({
                url: apiUrls.urlTruckDeleteApi(row.id),
                type: 'delete',
                success: function () {
                    $('#table').bootstrapTable('refresh');
                    showAlert('Deleted \"' + row.name + '\"', 'success');
                },
                error: function (error, val) {
                    showAlert('Delete item error! ' + error.responseJSON.errorMessage, 'danger');
                }
            });
        }
    }
}
