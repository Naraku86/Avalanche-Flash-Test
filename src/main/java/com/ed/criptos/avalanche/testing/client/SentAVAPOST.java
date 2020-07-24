/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 *
 * @author Edgar
 */
public class SentAVAPOST {

    private final String URL;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public SentAVAPOST(String URL) {
        this.URL = URL;
    }
    
    

    public JSONObject getPOSResponce(String request_path, JSONObject jsono) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsono.toString()))
                //.uri(URI.create("http://ed.ava.node:9650/ext/bc/X"))
                .uri(URI.create(URL + request_path))
                .header("Content-Type", "application/json")
                .build();
        return new JSONObject(httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body());
    }

}
