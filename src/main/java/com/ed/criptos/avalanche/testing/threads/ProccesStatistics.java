/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ed.criptos.avalanche.testing.threads;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;


/**
 *
 * @author Edgar
 */
public class ProccesStatistics implements Runnable {

    private final LinkedBlockingDeque<String> CSV = new LinkedBlockingDeque<>();
    //private final LinkedBlockingDeque<Long[]> latencias = new LinkedBlockingDeque<>();
    private boolean finaliza = false;
    private final String FILE_NAME;

    public ProccesStatistics(String nameTesting) {
        this.FILE_NAME = nameTesting + ".csv";
    }

    public void init(String csv_header) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            fileWriter.write(csv_header+"\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void addRegistroCSV(String lineCSV) {
        CSV.add(lineCSV);
    }
    
    public synchronized void addStatistics(String lineCSV) {
        CSV.add(lineCSV);
    }
    

    @Override
    public void run() {

        while (!finaliza || !CSV.isEmpty()) {
            if (CSV.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
                continue;
            }
            try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
                fileWriter.write(CSV.remove()+"\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    /**
     * @return the finaliza
     */
    public synchronized boolean isFinaliza() {
        return finaliza;
    }

    /**
     * @param finaliza the finaliza to set
     */
    public synchronized void setFinaliza(boolean finaliza) {
        this.finaliza = finaliza;
    }

}
