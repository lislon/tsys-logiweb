"use strict";

export default class ApiUrls {
    static urlCityAutocompleteApi(q) {
        return `${CONTEXT_PATH}/api/city/autocomplete?q=${q}&format=json`;
    }

    static urlDriverListApi() {
        return `${CONTEXT_PATH}/api/drivers`;
    }

    static urlDriverDeleteApi(id) {
        return `${CONTEXT_PATH}/api/drivers/${id}`;
    }

    static urlDriverEditPage(id) {
        return `${CONTEXT_PATH}/drivers/${id}`;
    }

}