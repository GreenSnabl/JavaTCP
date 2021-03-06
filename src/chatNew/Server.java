// Code by  TutCubeDE
// https://www.youtube.com/user/TuTCubeDE

package chatNew;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
    

/**
 *
 * @author snbl
 */
public class Server {

    ServerSocket server;
    ArrayList<PrintWriter> list_clientWriter;
    
    final int LEVEL_ERROR = 1;
    final int LEVEL_NORMAL = 0;
    
    public static void main(String[] args) {
        Server s = new Server();
        if (s.runServer()) {
            s.listenToClients();
        } else {
            // Do nothing
        }
    }
    public class ClientHandler implements Runnable {
        
        Socket client;
        BufferedReader reader;
        
        public ClientHandler(Socket client) {
            try {
                this.client = client;
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
            } catch (IOException e) {
            }
        }
        @Override
        public void run() {
            String nachricht;
            
            try {
                while ((nachricht = reader.readLine()) != null) {
                    appendTextToConsole("Vom Client: \n" + nachricht, LEVEL_NORMAL);
                    sendToAllClients(nachricht);
                }
            } catch (IOException e) {}
        }
    }
        public void listenToClients() {
            while (true) {
                try {
                    Socket client = server.accept();
                    
                    PrintWriter writer = new PrintWriter(client.getOutputStream());
                    list_clientWriter.add(writer);
                    
                    Thread clientThread = new Thread(new ClientHandler(client));
                    clientThread.start();
                } catch (IOException e) {}
            
            }
        
        }
        
        public boolean runServer() {
            try {
                server = new ServerSocket(5555);
                appendTextToConsole("Server wurde gestartet!", LEVEL_ERROR);
                
                list_clientWriter = new ArrayList<>();
                return true;
            } catch (IOException e) {
                appendTextToConsole("Server konnte nicht gestartet werden!", LEVEL_ERROR);
                return false;
            }
        
        }
        
        public void appendTextToConsole(String message, int level) {
            if (level == LEVEL_ERROR) {
                System.err.println(message + "\n");
            } else {
                System.out.println(message + "\n");
            }
        }
        
        public void sendToAllClients(String message) {
            list_clientWriter.stream().map((PrintWriter writer) -> {
                writer.println(message);
                return writer;
            }).forEachOrdered((writer) -> {
            writer.flush();
        });
        }
    
}
