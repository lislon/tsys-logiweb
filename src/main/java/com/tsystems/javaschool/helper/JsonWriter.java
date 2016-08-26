package com.tsystems.javaschool.helper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class JsonWriter {

    public static void writeJson(HttpServletResponse response, JSONArray jsonArray) throws IOException {
        response.setContentType("application/json");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(response.getOutputStream());

        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("list", jsonArray);
        jsonArray.writeJSONString(outputStreamWriter);
        outputStreamWriter.flush();
    }
}