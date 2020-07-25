/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing;

import com.ed.criptos.avalanche.testing.client.SentAVAPOST;
import com.ed.criptos.avalanche.testing.utils.JsonUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final SentAVAPOST SENDAVA = new SentAVAPOST(System.getProperty("ava.node.url"));

    private final HashMap<String, Boolean> MSUCCES = new HashMap<>();
    private final HashMap<String, Long> MTime = new HashMap<>();
    private final HashMap<String, Date> MTimestamp = new HashMap<>();
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
        MTimestamp.put(METHOD_IMPORT, new Date());
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
        MTimestamp.put(METHOD_EXPORT, new Date());

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
        MTimestamp.put(METHOD_DELETE, new Date());

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
        MTimestamp.put(METHOD_CREATE, new Date());
    }

    public String toStringCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        return String.format("%s," //USERNAME
                + "%s," //PASSWORD
                + "%s," //user
                + "%d," //METHOD_CREATE
                + "%s," //METHOD_CREATE
                + "%d," //METHOD_EXPORT
                + "%s," //METHOD_EXPORT
                + "%d," //METHOD_IMPORT
                + "%s," //METHOD_IMPORT
                + "%d," //METHOD_DELETE
                + "%s,", //METHOD_DELETE
                USERNAME,
                PASSWORD,
                user,
                MTime.get(METHOD_CREATE),
                sdf.format(MTimestamp.get(METHOD_CREATE)),
                MTime.get(METHOD_EXPORT),
                sdf.format(MTimestamp.get(METHOD_EXPORT)),
                MTime.get(METHOD_IMPORT),
                sdf.format(MTimestamp.get(METHOD_IMPORT)),
                MTime.get(METHOD_DELETE),
                sdf.format(MTimestamp.get(METHOD_DELETE))
        );
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
