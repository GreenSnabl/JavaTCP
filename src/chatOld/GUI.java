package chatOld;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class GUI {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GUI window = new GUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
            }
        });
    }

    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 12));
        frame.setFont(new Font("Calibri", Font.PLAIN, 12));
        frame.getContentPane().setBackground(new Color(255, 248, 220));
        frame.setBounds(100, 100, 800, 601);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        JScrollPane scrollPane_IncomingMessages = new JScrollPane();
        scrollPane_IncomingMessages.setBorder(new LineBorder(new Color(128, 0, 0)));
        scrollPane_IncomingMessages.setBounds(6, 6, 627, 497);
        frame.getContentPane().add(scrollPane_IncomingMessages);
        JTextArea txtrEingehendeNachrichten = new JTextArea();
        txtrEingehendeNachrichten.setFont(new Font("Calibri", Font.PLAIN, 12));
        txtrEingehendeNachrichten.setText("Eingehende Nachrichten");
        txtrEingehendeNachrichten.setBorder(null);
        txtrEingehendeNachrichten.setLineWrap(true);
        scrollPane_IncomingMessages.setViewportView(txtrEingehendeNachrichten);
        JList listUser = new JList();
        listUser.setFont(new Font("Calibri", Font.PLAIN, 12));
        listUser.setToolTipText("Userliste");
        listUser.setBorder(new LineBorder(new Color(139, 0, 0)));
        listUser.setBounds(647, 6, 131, 497);
        frame.getContentPane().add(listUser);
        JButton btnAbschicken = new JButton("Abschicken");
        btnAbschicken.setBorder(new LineBorder(new Color(128, 0, 0)));
        btnAbschicken.setToolTipText("Nachricht absenden");
        btnAbschicken.setBounds(647, 515, 131, 42);
        frame.getContentPane().add(btnAbschicken);
        JScrollPane scrollPane_Messages = new JScrollPane();
        scrollPane_Messages.setBorder(new LineBorder(new Color(128, 0, 0)));
        scrollPane_Messages.setBounds(6, 517, 627, 40);
        frame.getContentPane().add(scrollPane_Messages);
        JTextArea txtrDeineNachricht = new JTextArea();
        txtrDeineNachricht.setFont(new Font("Calibri", Font.PLAIN, 12));
        txtrDeineNachricht.setText("Deine Nachricht");
        txtrDeineNachricht.setBorder(null);
        txtrDeineNachricht.setToolTipText("Geben Sie hier Ihre Nachricht ein...");
        txtrDeineNachricht.setLineWrap(true);
        scrollPane_Messages.setViewportView(txtrDeineNachricht);
    }
}
