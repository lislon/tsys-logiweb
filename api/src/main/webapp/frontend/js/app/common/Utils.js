/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

"use strict";

export default class Utils {
    /**
     * Retrieve last slice of url address if it's a number.
     * For example if url is "/logiweb/orders/93" the method returns 93
     *
     * @returns id or null
     */
    static getIdFromUrl() {
        let maybeId = window.location.pathname.split("/").slice(-1)[0]
        return isNaN(maybeId) ? null : +maybeId;
    }
}