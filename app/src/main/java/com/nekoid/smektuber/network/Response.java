package com.nekoid.smektuber.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public final class Response {

    public int statusCode;

    public Object body;

    public Response(HttpURLConnection request) throws IOException {
        setStatusCode(request);
        setBody(request);
    }

    public void setStatusCode(HttpURLConnection request) throws IOException {
        this.statusCode = request.getResponseCode();
    }

    private void setBody(HttpURLConnection request) throws IOException {
        if (request.getResponseCode() >= 200 && request.getResponseCode() <= 299) {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            body = response.toString();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            body = response.toString();
        }
    }
}
