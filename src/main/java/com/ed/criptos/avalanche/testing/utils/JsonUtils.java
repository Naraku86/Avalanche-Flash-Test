/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing.utils;

import org.json.JSONObject;

/**
 *
 * @author Edgar
 */
public class JsonUtils {

    private static int id = 0;

    public static synchronized JSONObject getBase() {
        id++;
        return new JSONObject().put("jsonrpc", "2.0")
                .put("id", id);
    }
    
    public static synchronized JSONObject getJSONAVA(String method) {
        return getBase().put("method", method);
    }
    
    
    public static synchronized JSONObject getJSONAVA(String method, JSONObject params) {
        return getJSONAVA(method).put("params", params);
    }
    

}
