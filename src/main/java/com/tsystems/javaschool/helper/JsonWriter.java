package com.tsystems.javaschool.helper;

import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class JsonWriter {

    public static void writeJson(HttpServletResponse response, JSONArray jsonArray) throws IOException {
        response.setContentType("application/json");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(response.getOutputStream());
        jsonArray.writeJSONString(outputStreamWriter);
        outputStreamWriter.flush();
    }
}
