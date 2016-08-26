package com.tsystems.javaschool.servlet;

import com.tsystems.javaschool.entities.Truck;
import com.tsystems.javaschool.helper.JsonWriter;
import com.tsystems.javaschool.services.TruckManager;
import com.tsystems.javaschool.repos.TruckRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ele on 8/21/16.
 */
public class TruckListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TruckManager manager = new TruckManager(new TruckRepository());

        List<Truck> allTrucks = manager.findAllTrucks();
        JSONArray jsonArray = new JSONArray();
        for (Truck truck : allTrucks) {
            // todo jackson
            JSONObject jsonTruck = new JSONObject();
            jsonTruck.put("id", truck.getId());
            jsonTruck.put("name", truck.getName());
            jsonTruck.put("duty_hours", truck.getMaxDuty());
            jsonTruck.put("capacity", truck.getCapacityKg());
            jsonTruck.put("condition", truck.getCondition().toString());
            jsonTruck.put("city", truck.getCity().getName());
            jsonArray.add(jsonTruck);
        }

        JsonWriter.writeJson(response, jsonArray);
    }
}
