/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

    /**
     * UDPClient
     * Sends UDP datagrams to a chat server.
     */
public class UDPClient {
    int port;
    String addressString = "";

    
    public UDPClient(String address) {
        this(address, 8080);
    }
    
    public UDPClient(String addressString, int port) {
        this.addressString = addressString;
        this.port = port;
        String lineInput = "";
        DatagramPacket packet;
        InetAddress address;
        DatagramSocket socket;
        boolean stop = false;
        byte[] dataBuffer = null;
        
        Console userInput = System.console();
        if (userInput == null) {
            System.out.println("Pas de console");
        }
        else {
            System.out.println("Console détectée");
            while (!stop) {
                try {
                    dataBuffer = null;
                    socket = new DatagramSocket();
                    lineInput = userInput.readLine(">");
                    dataBuffer = lineInput.getBytes();
                    address = InetAddress.getByName(addressString);
                    packet = new DatagramPacket(dataBuffer, lineInput.length(), address, port);
                    socket.send(packet);
                    socket.close();
                    if (lineInput.contains("byebye")) {
                        stop = true;
                    }
                } catch (UnknownHostException eUnknownHost) {
                    System.out.println("unknown host : " + eUnknownHost);
                } catch (IOException eInOut) {
                    System.out.println("Socket exception : " + eInOut);
                }
            }
        }
    }
    
}
