/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */
package carrotchat.graphic;

import carrotchat.model.TCPClient;
import carrotchat.model.TCPMultiServer;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

    /**
     * CarrotWindow
     * Creates the graphic window for the chat application.
     */
public class CarrotWindow extends JFrame implements ActionListener
{
    private final JTextArea consoleTextArea = new JTextArea();
    private final TextAreaOutputStream taOutputStream = new TextAreaOutputStream(consoleTextArea, "Console");
    private JTextArea serverAdressText;
    private JTextArea serverPortText;
    private static boolean serverStarted = false;

    public CarrotWindow(String titre)
    {
        super(titre);
        setSize(800,600);
        
        setLayout(new GridLayout(1,2));

        //Control Pannel
        JPanel controlPan = new JPanel();
        SpringLayout layoutControl = new SpringLayout();
        controlPan.setLayout(layoutControl);
        controlPan.setBackground(Color.GRAY);
        add(controlPan);

        JLabel labelServer = new JLabel("On se connectera au serveur suivant :"); 
        controlPan.add(labelServer);
        serverAdressText = new JTextArea("");
        controlPan.add(serverAdressText);
        serverPortText = new JTextArea("8080");
        controlPan.add(serverPortText);
        Button buttonConnect = new Button("Connexion");
        buttonConnect.addActionListener(this);
        controlPan.add(buttonConnect);
        Button buttonServer = new Button("Lancer le serveur");
        buttonServer.addActionListener(this);
        controlPan.add(buttonServer);

        layoutControl.putConstraint(SpringLayout.NORTH, labelServer, 5, SpringLayout.NORTH, controlPan);
        layoutControl.putConstraint(SpringLayout.NORTH, serverAdressText, 15, SpringLayout.SOUTH, labelServer);
        layoutControl.putConstraint(SpringLayout.NORTH, serverPortText, 15, SpringLayout.SOUTH, serverAdressText);
        layoutControl.putConstraint(SpringLayout.NORTH, buttonConnect, 15, SpringLayout.SOUTH, serverPortText);
        layoutControl.putConstraint(SpringLayout.NORTH, buttonServer, 15, SpringLayout.SOUTH, buttonConnect);
        layoutControl.putConstraint(SpringLayout.WEST, serverAdressText, 5, SpringLayout.WEST, controlPan);
        layoutControl.putConstraint(SpringLayout.EAST, serverAdressText, -5, SpringLayout.EAST, controlPan);
        layoutControl.putConstraint(SpringLayout.WEST, serverPortText, 5, SpringLayout.WEST, controlPan);
        layoutControl.putConstraint(SpringLayout.EAST, serverPortText, -5, SpringLayout.EAST, controlPan);
        layoutControl.putConstraint(SpringLayout.WEST, labelServer, 5, SpringLayout.WEST, controlPan);
        layoutControl.putConstraint(SpringLayout.EAST, buttonConnect, -5, SpringLayout.EAST, controlPan);
        layoutControl.putConstraint(SpringLayout.EAST, buttonServer, -5, SpringLayout.EAST, controlPan);

        //Text Panel
        JPanel textPan = new JPanel();
        SpringLayout layoutText = new SpringLayout();
        textPan.setLayout(layoutText);
        consoleTextArea.setLineWrap(true);
        JScrollPane textHistory = new JScrollPane(consoleTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textPan.add(textHistory);
        System.setOut(new PrintStream(taOutputStream));
        JScrollPane textWrite = new JScrollPane(new JTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textPan.add(textWrite);
        add(textPan);
        
        layoutText.putConstraint(SpringLayout.NORTH, textHistory, 0, SpringLayout.NORTH, textPan);
        layoutText.putConstraint(SpringLayout.SOUTH, textHistory, -90, SpringLayout.SOUTH, textPan);
        layoutText.putConstraint(SpringLayout.NORTH, textWrite, -90, SpringLayout.SOUTH, textPan);
        layoutText.putConstraint(SpringLayout.SOUTH, textWrite, 0, SpringLayout.SOUTH, textPan);
        layoutText.putConstraint(SpringLayout.WEST, textHistory, 0, SpringLayout.WEST, textPan);
        layoutText.putConstraint(SpringLayout.EAST, textHistory, 0, SpringLayout.EAST, textPan);
        layoutText.putConstraint(SpringLayout.WEST, textWrite, 0, SpringLayout.WEST, textPan);
        layoutText.putConstraint(SpringLayout.EAST, textWrite, 0, SpringLayout.EAST, textPan);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String input = e.getActionCommand();
        switch(input)
        {
        case "Connexion" :
            System.out.println("Mode client TCP");
            TCPClient lapin = new TCPClient(serverAdressText.getText(), Integer.parseInt(serverPortText.getText()));
            System.out.println("Il faut écrire le texte depuis la console.");
            lapin.launch();
            break;
        case "Lancer le serveur" :
            if (!serverStarted) {
                serverStarted = true;
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println("Mode serveur TCP multi-clients");
                        TCPMultiServer carrote = new TCPMultiServer(Integer.parseInt(serverPortText.getText()));
                        carrote.launch();
                    }
                }.start();
            }
        }
    }
}
