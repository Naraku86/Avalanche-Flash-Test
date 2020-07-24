/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing;

import com.ed.criptos.avalanche.testing.utils.PasswordGenerator;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * @author Edgar
 */
public class Main {

    // one instance, reuse
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws Exception {

        System.out.println("Iniciando pruebas");
        if(!System.getProperties().contains("url")){
            System.setProperty("url", "http://192.168.100.67:9650");
        }
        
        String usuario_base= PasswordGenerator.GenerateRandomString(5, 5, 1, 1, 1, 1);
        
        for (int i = 0; i < 1000; i++) {
            
            Keystore creaUsuario= new Keystore(String.format("%s%010d", usuario_base,i), PasswordGenerator.GenerateRandomString(15, 15, 1, 1, 1, 1));
            creaUsuario.create();
            creaUsuario.exportUser();
            creaUsuario.delete();
            creaUsuario.import_user();
            creaUsuario.delete();
            System.out.println(i+"  "+creaUsuario.toString());

        }

       // Main obj = new Main();
        //System.out.println("Testing 1 - Send Http GET request");
        //obj.sendGet();

        //System.out.println("Testing 2 - Send Http POST request");
        //obj.sendPost();

    }

    private void sendGet() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/get"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private void sendPost() throws Exception {

        // form parameters
      
        String json = "{\n"
                + "    \"jsonrpc\":\"2.0\",\n"
                + "    \"id\"     :4,\n"
                + "    \"method\" :\"avm.getBalance\",\n"
                + "    \"params\" :{\n"
                + "        \"address\":\"X-DgFwFVXgPEySynMFwwJmc1BVX5Uk5zYbU\",\n"
                + "        \"assetID\"  :\"AVA\"\n"
                + "    }\n"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                //.uri(URI.create("http://ed.ava.node:9650/ext/bc/X"))
                .uri(URI.create("http://192.168.100.67:9650/ext/bc/X"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

}
