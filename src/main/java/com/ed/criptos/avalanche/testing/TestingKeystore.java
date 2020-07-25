/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing;

import com.ed.criptos.avalanche.testing.threads.Keystore;
import com.ed.criptos.avalanche.testing.threads.ProccesStatistics;
import com.ed.criptos.avalanche.testing.utils.PasswordGenerator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Edgar
 */
public class TestingKeystore {

    private int numberTest = Integer.parseInt(System.getProperty("ava.test.number", "100"));
    private int threadNumber = Integer.parseInt(System.getProperty("ava.test.thread.number", "100"));
    private boolean verbose = Boolean.valueOf(System.getProperty("ava.verbose", "true"));

    private String testingName;
    private final String CSV_HEADER = "ID,USERNAME,PASSWORD,USER,METHOD_CREATE,METHOD_CREATE,METHOD_EXPORT,METHOD_EXPORT,METHOD_IMPORT,METHOD_IMPORT,METHOD_DELETE,METHOD_DELETE, TOTAL";
    private final ProccesStatistics statistics;

    public TestingKeystore() {
        testingName = "Keystore-" + numberTest + threadNumber;
        statistics = new ProccesStatistics(testingName);

    }

    public void init() {
        statistics.init(CSV_HEADER);
        new Thread(statistics).start();
        System.out.println("Iniciando la Prueba[" + testingName + "]  ");
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        String usuario_base = PasswordGenerator.GenerateRandomString(5, 5, 1, 1, 1, 1);
        String password_base = PasswordGenerator.GenerateRandomString(10, 10, 1, 1, 1, 1);
        long inicio_prueba = System.currentTimeMillis();
        for (int i = 0; i < numberTest; i++) {
            Runnable worker = new Keystore(i, String.format("%s%010d", usuario_base, i), String.format("%s%05d", password_base, i), statistics, verbose);
            executor.submit(worker);
        }
        executor.shutdown();
        int count=0;
        while (!executor.isTerminated()) {
            count++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
            if(!verbose&& count%100==0)System.out.println("Prueba[" + testingName + "] Procesando...");
        }
        long fin = System.currentTimeMillis();

        long latencia1 = fin - inicio_prueba;
       // long latencia2 = fin - inicio_shutdown;
        
        statistics.setFinaliza(true);

        System.out.println("Numero de pruebas [" + numberTest + "] llamadas al API[" + (numberTest * 5) + "] - Tiempo [" + (latencia1 / 1000) + "]Seg | Promedio de Llamadas [" + ((numberTest * 5) / (latencia1 / 1000)) + "]");

    }
}
