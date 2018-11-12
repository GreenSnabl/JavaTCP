
package chatOld;

import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author TutCubeDE
 */
public class Client implements Runnable {
    
    private static int a;
    
    public static void main(String[] args) {
        a = 1;
        new Thread(new Client()).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();
    }
    
    @Override
    public void run() {
        
        try {
            //Scanner eingabe = new Scanner(System.in);
        
            
            Socket client = new Socket("localhost", 5555);
            System.out.println("Client gestartet");
            
            // Streams
            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            
            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //--------------
            
            //System.out.print("> ");
            //String anServer = eingabe.nextLine();
                    
            writer.write("Hallo vom " + a + ". Client!\n");
            writer.flush();
            a++;
            
            String s = null;
            
            while ((s = reader.readLine()) != null) {                
                System.out.println("Empfangen vom Server: " + s);
            }      
            
            reader.close();
            writer.close();
            
        } catch (IOException e) {}
    }
}
