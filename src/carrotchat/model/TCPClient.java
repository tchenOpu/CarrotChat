/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

    /**
     * TCPClient.
     * <p>
     * Creates a TCP connection to connect to a chat server as a client.
     * </p>
     */

public class TCPClient {
    int port;
    String addressString = "localhost";
    
    /**
     * Testing javadoc.
     * There are some more comments to make
     * @param address is probably the address used
     */
    public TCPClient(String address) {
        this(address, 8080);
    }
    
    /**
     *
     * @param addressString
     * @param port
     */
    public TCPClient(String addressString, int port) {
        this.addressString = addressString;
        this.port = port;
    }
    
    /**
     * void method
     */
    public void launch() {
        String lineInput = "";
        InetAddress address;
        boolean stop = false;
        try {
        
        Console userInput = System.console();
        if (userInput == null) {
            System.out.println("Pas de console");
        }
        else {
            System.out.println("Console détectée");
            address = InetAddress.getByName(addressString);
            Socket clientSocket = new Socket(address, port);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            
            clientRecieveThread reveiveThread = new clientRecieveThread(clientSocket);
            reveiveThread.start();
            clientWriteThread writeThread = new clientWriteThread(outToServer, userInput);
            writeThread.start();
        }
        } catch (UnknownHostException eUnkHost) {
            System.out.println("Unknown host exception : " + eUnkHost);
        } catch (IOException eInOut) {
            System.out.println("IOException : " + eInOut);
        }
    }
}
