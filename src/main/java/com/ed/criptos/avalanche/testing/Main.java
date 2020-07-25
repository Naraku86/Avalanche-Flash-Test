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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

        System.out.println(" _____    _         ___  _   _  ___       _____         _   \n"
                + "|  ___|  | |       / _ \\| | | |/ _ \\     |_   _|       | |  \n"
                + "| |__  __| |______/ /_\\ \\ | | / /_\\ \\______| | ___  ___| |_ \n"
                + "|  __|/ _` |______|  _  | | | |  _  |______| |/ _ \\/ __| __|\n"
                + "| |__| (_| |      | | | \\ \\_/ / | | |      | |  __/\\__ \\ |_ \n"
                + "\\____/\\__,_|      \\_| |_/\\___/\\_| |_/      \\_/\\___||___/\\__|\n"
                + "                                                            \n"
                + "                                                            ");

       

        if (!System.getProperties().containsKey("ava.node.url")) {
            System.setProperty("ava.node.url", "http://127.0.0.1:9650");
        }

        int numberTest = Integer.parseInt(System.getProperty("ava.test.number", "100"));

        boolean verbose = Boolean.valueOf(System.getProperty("ava.verbose", "true"));

        String usuario_base = PasswordGenerator.GenerateRandomString(5, 5, 1, 1, 1, 1);

        List<String> results = new ArrayList<>();
        results.add("USERNAME,PASSWORD,user,METHOD_CREATE,METHOD_CREATE,METHOD_EXPORT,METHOD_EXPORT,METHOD_IMPORT,METHOD_IMPORT,METHOD_DELETE,METHOD_DELETE");

        long inicio=System.currentTimeMillis();
         System.out.println("Iniciando pruebas");
        for (int i = 0; i < numberTest; i++) {

            Keystore creaUsuario = new Keystore(String.format("%s%010d", usuario_base, i), PasswordGenerator.GenerateRandomString(15, 15, 1, 1, 1, 1));
            creaUsuario.create();
            creaUsuario.exportUser();
            creaUsuario.delete();
            creaUsuario.import_user();
            creaUsuario.delete();
            if (verbose) {
                System.out.println(i + "  " + creaUsuario.toString());
            }
            results.add(creaUsuario.toStringCSV());
        }
        inicio=System.currentTimeMillis()-inicio;
        
        System.out.println("Numero de pruebas ["+numberTest+"] llamadas al API["+(numberTest*5)+"] - Tiempo ["+(inicio/1000)+"]Seg | Promedio de Llamadas ["+((numberTest*5)/(inicio/1000)) +"]");

        Files.write(Paths.get("result_test_keystore.csv"), results);

        // Main obj = new Main();
        //System.out.println("Testing 1 - Send Http GET request");
        //obj.sendGet();
        //System.out.println("Testing 2 - Send Http POST request");
        //obj.sendPost();
    }

    

}
