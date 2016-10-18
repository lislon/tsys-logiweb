package com.tsystems.javaschool.logiweb.service.helper;

import com.tsystems.javaschool.logiweb.dao.entities.*;
import com.tsystems.javaschool.logiweb.service.LogiwebConfig;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class RouteCalculator {

    private CityManager cityManager;

    private LogiwebConfig appConfig;

    @Autowired
    public RouteCalculator(CityManager cityManager, LogiwebConfig appConfig) {
        this.cityManager = cityManager;
        this.appConfig = appConfig;
    }

    /**
     * Return aproximation of trip duration with given number of drivers.
     *
     * @param routeLength Route length in km
     * @param numCoDrivers  Number of drivers in truck
     * @return double number of total hours required to trip, include rest time.
     */
    public double getRouteDuration(int routeLength, int numCoDrivers) {
        double distancePerDay = (Math.min(numCoDrivers * appConfig.getLimitHoursPerDay(), 24) * appConfig.getTruckAvgSpeed());

        double hours = Math.floor(routeLength / distancePerDay) * 24 + (routeLength % distancePerDay) / appConfig.getTruckAvgSpeed();

        return hours;
    }

    /**
     * Return a route length in KM
     *
     * @param waypointCollection Waypoints
     * @return route length in KM
     */
    public Integer getRouteDistance(Collection<OrderWaypoint> waypointCollection) {

        if (waypointCollection.size() <= 1) {
            return 0;
        }
        Iterator<OrderWaypoint> iterator = waypointCollection.iterator();
        City lastCity = iterator.next().getCity();
        int totalDistance = 0;

        while (iterator.hasNext()) {
            City thisCity = iterator.next().getCity();
            if (!thisCity.equals(lastCity)) {
                totalDistance += cityManager.getDistance( lastCity, thisCity );
            }
            lastCity = thisCity;
        }
        return totalDistance;
    }


    /**
     * Returns maximum summary carried weight for segment along the route.
     *
     * @param waypointCollection List of waypoints for given route
     * @return Maximum weight carried for single segment (in kg)
     */
    public Integer getMaxPayload(Collection<OrderWaypoint> waypointCollection) {
        int maxPayload = 0;
        Set<Cargo> cargoOnBoard = new HashSet<>();
        for (OrderWaypoint p : waypointCollection) {
            if (p.getOperation() == OrderWaypoint.Operation.LOAD) {
                cargoOnBoard.add(p.getCargo());
            } else if (p.getOperation() == OrderWaypoint.Operation.UNLOAD) {
                cargoOnBoard.remove(p.getCargo());
            }
            maxPayload = Math.max(
                    cargoOnBoard.stream().mapToInt(Cargo::getWeight).sum(),
                    maxPayload);
        }
        return maxPayload;
    }
}
