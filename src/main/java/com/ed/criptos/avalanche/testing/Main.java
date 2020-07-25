/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing;



import java.net.http.HttpClient;

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

        System.out.println("          ___              __                 __            ________           __  \n"
                + "         /   |_   ______ _/ /___ _____  _____/ /_  ___     / ____/ /___ ______/ /_ \n"
                + "        / /| | | / / __ `/ / __ `/ __ \\/ ___/ __ \\/ _ \\   / /_  / / __ `/ ___/ __ \\\n"
                + "       / ___ | |/ / /_/ / / /_/ / / / / /__/ / / /  __/  / __/ / / /_/ (__  ) / / /\n"
                + "      /_/  |_|___/\\__,_/_/\\__,_/_/ /_/\\___/_/ /_/\\___/  /_/   /_/\\__,_/____/_/ /_/ \n"
                + "                                                                                   \n"
                + "                                    ______          __ \n"
                + "                                   /_  __/__  _____/ /_\n"
                + "                                    / / / _ \\/ ___/ __/\n"
                + "                                   / / /  __(__  ) /_  \n"
                + "                                  /_/  \\___/____/\\__/  \n"
                + "                                                       ");

        if (!System.getProperties().containsKey("ava.node.url")) {
            System.setProperty("ava.node.url", "http://127.0.0.1:9650");
        }

        TestingKeystore testingKeystore = new TestingKeystore();
        testingKeystore.init();

    }

}
