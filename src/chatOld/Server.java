/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatOld;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author snbl
 */
public class Server {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(30);

        ServerSocket server;
        try {
            server = new ServerSocket(5555);
            System.out.print("Server gestartet!");

            while (true) {
            try {

                Socket client = server.accept();                   // Wartet / Clientanfragen werden angenommen

                Thread t = new Thread(new Handler(client));
                t.start();

                executor.execute(new Handler(client));

            } catch (IOException e) {
            }
            }
        } catch (IOException e1) {
        }

    }

}
