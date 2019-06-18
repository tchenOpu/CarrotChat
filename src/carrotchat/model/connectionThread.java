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
import java.net.Socket;
import java.net.SocketException;

    /**
     * connectionThread
     * Server thread that allows TCP connections from clients.
     */

public class connectionThread extends Thread {
    Socket client;
    
    public connectionThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            boolean connection = true;
            byte[] dataBuffer = new byte[256];
            System.out.println("Client connecté");
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            InetAddress adresse = client.getInetAddress();
            while(connection){
                BufferedInputStream input = new BufferedInputStream(client.getInputStream());
                int stream = input.read(dataBuffer);
                if (stream == -1) {
                    connection = false;
                    System.out.println("Client " + adresse + " déconnecté.");
                }
                else {
                    String texteInput = new String(dataBuffer, 0, stream-1);
                    System.out.println(adresse + " : " + texteInput);
                    for (Socket envoiClient : TCPMultiServer.listeClients) {
                        PrintWriter envoiOutput = new PrintWriter(envoiClient.getOutputStream(), true);
                        envoiOutput.println(client.getInetAddress() + " : " + texteInput);
                    }
                }
            }
            client.close();
            TCPMultiServer.listeClients.remove(client);
            
        } catch (SocketException eDisconnect) {
            System.out.println("Le client " + client.getInetAddress() + " s'est déconnecté.");
        } catch (IOException eInOut) {
            System.out.println("IOException : " + eInOut);
        }
    }
}
