/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

    /**
     * TCPServer
     * Listens to TCP connexions as a chat server, only one connexion is 
     * possible in this mode.
     */

public class TCPServer extends UDPServer {
    public TCPServer() {
        super();
    }
    
    public TCPServer(int port) {
        super(port);
    }
    
    @Override
    public void launch() {
        try {
            ServerSocket server = new ServerSocket(port);
            boolean running = true;
            while(running) {
                byte[] dataBuffer = new byte[256];
                Socket client = server.accept();
                boolean connexion = true;
                System.out.println("Client connecté");
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                output.println("Welcome to CarrotChat");
                InetAddress adresse = client.getInetAddress();
                while(connexion)
                {
                    BufferedInputStream input = new BufferedInputStream(client.getInputStream());
                    int stream = input.read(dataBuffer);
                    if (stream == -1) {
                        connexion = false;
                        System.out.println("Client " + adresse + "déconnecté.");
                    }
                    else {
                        String texteInput = new String(dataBuffer, 0, stream-1);
                        //System.out.println(Arrays.toString(texteInput.getBytes()));
                        System.out.println(adresse + " : " + texteInput);
                        output.println(adresse + " : " + texteInput);
                    }
                }
                client.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("exception InOut : " + e.toString());
        }
    }
}
