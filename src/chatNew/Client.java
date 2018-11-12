// Code by  TutCubeDE
// https://www.youtube.com/user/TuTCubeDE

package chatNew;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author snbl
 */
public class Client {
    JFrame clientFrame;
    JPanel clientPanel;
    JTextArea textArea_Messages;
    JTextField textField_ClientMessage;
    JButton button_SendMessage;
    JTextField textField_Username;
    
    Socket client;
    PrintWriter writer;
    BufferedReader reader;
    
    public static void main(String[] args) {
        Client c = new Client();
        c.createGUI();
    }
    
    public void createGUI() {
        clientFrame = new JFrame("Chat");
        clientFrame.setSize(800, 600);
        
        // Panel erzeugen, das alle anderen Inhalte enthält
        clientPanel = new JPanel();
        
        // Serverkonsole erstellen und anzeigen
        textArea_Messages = new JTextArea();
        textArea_Messages.setEditable(false);
        
        textField_ClientMessage = new JTextField(38);
        textField_ClientMessage.addKeyListener(new SendPressEnterListener());
        
        button_SendMessage = new JButton("Senden");
        button_SendMessage.addActionListener(new SendButtonListener());
        
        textField_Username = new JTextField(10);
        
        // Serverkonsole einen Scrollbalken hinzufügen
        JScrollPane scrollPane_Messages = new JScrollPane(textArea_Messages);
        scrollPane_Messages.setPreferredSize(new Dimension(700, 500));
        scrollPane_Messages.setMinimumSize(new Dimension(700, 500));
        scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        if (!connectToServer()) {
            // Connect-Label anzeigen ob verbunden oder nicht
        }
        
        Thread t = new Thread(new MessagesFromServerListener());
        t.start();
        
        clientPanel.add(scrollPane_Messages);
        clientPanel.add(textField_Username);
        clientPanel.add(textField_ClientMessage);
        clientPanel.add(button_SendMessage);
        
        // Panel zum ContentPane (Inhaltsbereich) hinzufügen
        clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);
        
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(true);   
    }
    
    public boolean connectToServer() {
        try {
            client = new Socket("127.0.0.1", 5555);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream());
            appendTextMessages("Netzwerkverbindung hergestellt");
            
            return true;
        } catch (IOException e) {
            appendTextMessages("Netzwerkverbindung fehlgeschlagen");
            return false;
        }  
    }
    
    public void sendMessageToServer() {
        writer.println(textField_Username.getText() + ": " + textField_ClientMessage.getText());
        writer.flush();
        
        textField_ClientMessage.setText("");
        textField_ClientMessage.requestFocus();   
    }
    
    public void appendTextMessages(String message) {
        textArea_Messages.append(message + "\n");
    }
    
    // Listener
    
    public class SendPressEnterListener implements KeyListener {
        
        @Override
        public void keyPressed(KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                sendMessageToServer();
            }            
        }
        
        @Override
        public void keyReleased(KeyEvent arg0) {}
        
        @Override
        public void keyTyped(KeyEvent arg0) {}
        
    }
    
    public class SendButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessageToServer();
        }
    }
    
    public class MessagesFromServerListener implements Runnable {
        
        @Override
        public void run() {
            String message;
            
            try {
                while ((message = reader.readLine()) != null) {
                    appendTextMessages(message);
                }            
            } catch (IOException e) {
                appendTextMessages("Nachricht konnte nicht empfangen werden!");
            }
        }
    }
}
