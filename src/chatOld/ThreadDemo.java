/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatOld;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author snbl
 */
public class ThreadDemo {

    static class Zaehlen implements Runnable {

        Zaehlen(String s) {
            name = s;
        }
        private String name;

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                System.out.println(name + ": Zählen: " + i);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("START");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Thread t1 = new Thread(new Zaehlen("A"));
        Thread t2 = new Thread(new Zaehlen("B"));
        Thread t3 = new Thread(new Zaehlen("C"));
        Thread t4 = new Thread(new Zaehlen("D"));

        //t1.start();
        //t2.start();
        //t3.start();
        //t4.start();
        
        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
        executor.execute(t4);
        executor.shutdown();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.println("Zählen läuft noch!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ENDE");
    }
}
