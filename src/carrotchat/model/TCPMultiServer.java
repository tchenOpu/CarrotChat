/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

    /**
     * TCPMultiServer
     * Listens to TCP connexions as a chat server.
     */

public class TCPMultiServer extends UDPServer{
    static List<Socket> listeClients = new ArrayList<>();
    
    public TCPMultiServer() {
        super();
    }
    
    public TCPMultiServer(int port) {
        super(port);
        System.out.println("Port utilisé : " + port);
    }
    
    @Override
    public void launch() {
        try {
            ServerSocket server = new ServerSocket(port);
            boolean running = true;
            while(running) {
                Socket client = server.accept();
                listeClients.add(client);
                System.out.println("Client connecté");
                new connectionThread(client).start();
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                output.println("Welcome to CarrotChat");
            }
        }
        catch(IOException e)
        {
            System.out.println("exception InOut : " + e.toString());
        }
    }
}
