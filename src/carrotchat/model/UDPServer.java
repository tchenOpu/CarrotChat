/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


    /**
     * UDPServer
     * Listens to UDP datagrams as a chat server.
     */
public class UDPServer {
    int port;
    
    public UDPServer() {
        System.out.println("Port par défaut : 8080");
        port = 8080;
    }
    
    public UDPServer(int port){
            this.port = port;
    }

    @Override
    public String toString() {
        return "UDPServer{" + "port=" + port + '}';
    }
    
    public void launch() {
        byte[] dataBuffer;
        int dataLength = 100;
        boolean stop = false;
        List<InetAddress> listeClients = new ArrayList<>();
        while (!stop) {
            try{
                dataBuffer = new byte[256];
                //reception
                DatagramSocket socket = new DatagramSocket(port);
                DatagramPacket packet = new DatagramPacket(dataBuffer, dataLength);
                socket.receive(packet);
                String texte = new String(dataBuffer);
                InetAddress address = packet.getAddress();
                if (!listeClients.contains(address))
                {
                    listeClients.add(address);
                }
                texte = address.toString() + " : " + texte;
                int textLen = texte.indexOf(0);
                System.out.println(texte.substring(0, textLen));

                //réponse
                int clientPort = packet.getPort();
                dataBuffer = texte.getBytes();
                for (InetAddress clientAddress : listeClients) {
                    packet = new DatagramPacket(dataBuffer, dataBuffer.length, clientAddress, clientPort);   
                }
                socket.send(packet);

                socket.close();
            }
            catch(IOException eInOut){
                System.out.println("receive marchepa : " + eInOut);
            }
        }
    }
}
