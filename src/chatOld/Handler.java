/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatOld;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author snbl
 */
public class Handler implements Runnable {
    
    private Socket client;
    
    public Handler(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
        // Streams
            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            
            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //--------------
            
            String s = null;
            
            while ((s = reader.readLine()) != null) {
                writer.write(s + "\n");
                writer.flush();
                System.out.println("Empfangen vom Client: " + s);
            }
            
            writer.close();
            reader.close();
            
            client.close();
            
        } catch (Exception e) {e.printStackTrace();}
        
    
    }    
}
