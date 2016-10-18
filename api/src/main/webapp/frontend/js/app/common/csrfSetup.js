'use strict';

/**
 * Setup csrf token header for all ajax requests
 */
export default function () {
    $.ajaxSetup({
        headers: {
            _csrf: $('meta[name="csrf-token"]').attr('content')
        }
    });
}