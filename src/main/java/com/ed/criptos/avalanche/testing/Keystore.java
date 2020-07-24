/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing;

import com.ed.criptos.avalanche.testing.client.SentAVAPOST;
import com.ed.criptos.avalanche.testing.utils.JsonUtils;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author Edgar
 */
public class Keystore {

    private final String USERNAME;
    private final String PASSWORD;
    private final String METHOD_CREATE = "keystore.createUser";
    private final String METHOD_DELETE = "keystore.deleteUser";
    private final String METHOD_EXPORT = "keystore.exportUser";
    private final String METHOD_IMPORT = "keystore.importUser";
    private final String REQUEST_PATH = "/ext/keystore";
    private final SentAVAPOST SENDAVA = new SentAVAPOST(System.getProperty("url"));

    private final HashMap<String, Boolean> MSUCCES = new HashMap<>();
    private final HashMap<String, Long> MTime = new HashMap<>();
    private String user;

    public Keystore(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }
    
    public void import_user() {
        JSONObject jsono = JsonUtils.getJSONAVA(METHOD_IMPORT, new JSONObject().put("username", USERNAME).put("password", PASSWORD).put("user", user));
        long latencia = System.currentTimeMillis();
        boolean success = true;
        try {
            JSONObject posResponce = SENDAVA.getPOSResponce(REQUEST_PATH, jsono);
            if (!posResponce.has("result")) {
                success = false;
            }
        } catch (IOException | InterruptedException ex) {
            success = false;
        }
        MTime.put(METHOD_IMPORT, System.currentTimeMillis() - latencia);
        MSUCCES.put(METHOD_IMPORT, success);
    }
    

    public void exportUser() {
        JSONObject jsono = JsonUtils.getJSONAVA(METHOD_EXPORT, new JSONObject().put("username", USERNAME).put("password", PASSWORD));
        long latencia = System.currentTimeMillis();
        boolean success = true;

        try {
            JSONObject posResponce = SENDAVA.getPOSResponce(REQUEST_PATH, jsono);

            if (!posResponce.has("result")) {
                success = false;
            } else {
                user = posResponce.getJSONObject("result").getString("user");
            }

        } catch (IOException | InterruptedException ex) {
            success = false;
        }
        MTime.put(METHOD_EXPORT, System.currentTimeMillis() - latencia);
        MSUCCES.put(METHOD_EXPORT, success);

    }

    public void delete() {
        JSONObject jsono = JsonUtils.getJSONAVA(METHOD_DELETE, new JSONObject().put("username", USERNAME).put("password", PASSWORD));
        long latencia = System.currentTimeMillis();
        boolean success = true;

        try {
            JSONObject posResponce = SENDAVA.getPOSResponce(REQUEST_PATH, jsono);

            if (!posResponce.has("result")) {
                success = false;

            }

        } catch (IOException | InterruptedException ex) {
            success = false;
        }
        MTime.put(METHOD_DELETE, System.currentTimeMillis() - latencia);
        MSUCCES.put(METHOD_DELETE, success);

    }

    public void create() {
        JSONObject jsono = JsonUtils.getJSONAVA(METHOD_CREATE, new JSONObject().put("username", USERNAME).put("password", PASSWORD));
        long latencia = System.currentTimeMillis();
        boolean success = true;

        try {
            JSONObject posResponce = SENDAVA.getPOSResponce(REQUEST_PATH, jsono);

            if (!posResponce.has("result")) {
                success = false;

            }

        } catch (IOException | InterruptedException ex) {
            success = false;
        }
        MTime.put(METHOD_CREATE, System.currentTimeMillis() - latencia);
        MSUCCES.put(METHOD_CREATE, success);

    }

    @Override
    public String toString() {
        return String.format("username=%-20s password=%15s |"
                + "create-success=%s create-time=%4d mls |"
                + "export-success=%s export-time=%4d mls |"
                + "import-success=%s import-time=%4d mls |"
                + "delete-success=%s delete-time=%4d mls |"
                + " user=%s",
                USERNAME,
                PASSWORD,
                MSUCCES.get(METHOD_CREATE),
                MTime.get(METHOD_CREATE),
                
                MSUCCES.get(METHOD_EXPORT),
                MTime.get(METHOD_EXPORT),
                
                MSUCCES.get(METHOD_IMPORT),
                MTime.get(METHOD_IMPORT),
                
                MSUCCES.get(METHOD_DELETE),
                MTime.get(METHOD_DELETE),
                user
        );
    }

}
