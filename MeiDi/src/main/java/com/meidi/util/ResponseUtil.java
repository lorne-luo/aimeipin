package com.meidi.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

public class ResponseUtil {
    public static void responseJson(Object o, HttpServletResponse response) throws IOException {
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String information = gson.toJson(o);
        out.print(information);
        out.flush();
        out.close();
    }


}
