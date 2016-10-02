"use strict";

export default class ApiUrls {
    static urlCityAutocompleteApi(q) {
        return `${CONTEXT_PATH}/cities/api/autocomplete?q=${q}&format=json`;
    }

    // driver

    static urlDriverListApi() {
        return `${CONTEXT_PATH}/drivers/api`;
    }

    static urlDriverDeleteApi(id) {
        return `${CONTEXT_PATH}/drivers/api/${id}`;
    }

    static urlDriverEditPage(id) {
        return `${CONTEXT_PATH}/drivers/${id}`;
    }

    static urlDriverListAvailableApi(orderId = null) {
        return `${CONTEXT_PATH}/drivers/api/available` + (orderId ? `?orderId=${orderId}` : ``);
    }

    // truck

    static urlTruckListApi() {
        return `${CONTEXT_PATH}/trucks/api`;
    }

    static urlTruckDeleteApi(id) {
        return `${CONTEXT_PATH}/trucks/api/${id}`;
    }

    static urlTruckEditPage(id) {
        return `${CONTEXT_PATH}/trucks/${id}`;
    }

    // order

    static urlOrderListApi() {
        return `${CONTEXT_PATH}/orders/api`;
    }

    static urlOrderDeleteApi(id) {
        return `${CONTEXT_PATH}/orders/api/${id}`;
    }

    static urlOrderCreateApi() {
        return `${CONTEXT_PATH}/orders/api/new`;
    }
    static urlOrderUpdateApi(id) {
        return `${CONTEXT_PATH}/orders/api/${id}`;
    }
    static urlOrderRouteMetaApi(orderId = null) {
        return `${CONTEXT_PATH}/orders/api/routeMeta` + (orderId ? `?orderId=${orderId}` : ``);
    }

    static urlOrderEditPage(id) {
        return `${CONTEXT_PATH}/orders/${id}`;
    }

    static urlOrderListPage() {
        return `${CONTEXT_PATH}/orders`;
    }

}