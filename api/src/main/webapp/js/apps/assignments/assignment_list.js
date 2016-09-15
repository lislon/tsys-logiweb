var $modal = $('.modal').modal({show: false});

var LIST_URL = CONTEXT_PATH + "/api/assignment/list.do";
var DRIVER_CODE = '';

var $table = $('#table').bootstrapTable({
    url: LIST_URL,
    onLoadError: function (status, res) {
        showAlert('Error loading data from table!', 'danger');
    },
    formatNoMatches: function () {
        if ($('#personalNumber').val()) {
            return 'No orders found for ' + DRIVER_CODE;
        } else {
            return "Enter drivers code to see data";
        }

    }
});

function driversFormatter(val, row) {
    return row.coDrivers.join(', ');
}

function queryParams() {
    if (DRIVER_CODE == '') {
        return false;
    }
    return { personalNumber: DRIVER_CODE };
}

$(function () {

    if ($('#personalNumber').val()) {
        updateTable();
    }

    $("#search-driver").submit(function (e) {
            e.preventDefault();
            updateTable();
        }
    );
});

function updateTable() {
    DRIVER_CODE = $('#personalNumber').val();
    $table.bootstrapTable('refresh');
}

