'use strict';
/**
 * Created by ele on 10/16/16.
 */

/**
 * Converts route data from cargo-based to operation-based format
 *
 * Operation based format is:
 *
           cargoes: {
                0: {
                    name: '',
                    title: ''
                }
            },
            waypoints: [
                {
                    cargo_id: 0,
                    city_id: 0,
                    operation: 'LOAD'
                }
            ]

 */
export default class RouteDataAdapter {
    static convert(cargoCollection, citiesSequence) {

        let cargoNames = cargoCollection.pluck('name');

        let waypoints = [];

        _(citiesSequence).each(cityId => {

            // search for cargoes to load in this city and add them as load operation
            let cargoesToLoad = cargoCollection.where({ srcCityId: +cityId });
            cargoesToLoad.forEach(cargo =>
                waypoints.push({
                    cityId: +cityId,
                    cargoNo: _.indexOf(cargoNames, cargo.get('name')),
                    operation: 'LOAD'
                })
            );

            // search for cargoes to unload in this city and add them as unload operation
            let cargoesToUnLoad = cargoCollection.where({ dstCityId: +cityId });
            cargoesToUnLoad.forEach(cargo =>
                waypoints.push({
                    cityId: +cityId,
                    cargoNo: _.indexOf(cargoNames, cargo.get('name')),
                    operation: 'UNLOAD'
                })
            );
        });

        return {
            cargoes: cargoCollection.map(el => { return {
                name: el.get('name'),
                title: el.get('title'),
                weight: el.get('weight')
            }}),
            waypoints: waypoints
        };

    }
}