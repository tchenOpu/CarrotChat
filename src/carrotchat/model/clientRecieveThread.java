/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

    /**
     * clientRecieveThread
     * Client thread that displays recieved messages to the console
     * from the socket given in argument.
     */
public class clientRecieveThread extends Thread {
    private final Socket clientSocket;
    private byte[] dataBuffer = new byte[256];
    boolean stop = true;
    public clientRecieveThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            while (stop) {
                BufferedInputStream inFromServer = new BufferedInputStream(clientSocket.getInputStream());
                int stream = inFromServer.read(dataBuffer);
                if (stream == -1) {
                    stop = true;
                    System.out.println("Connexion au serveur perdue.");
                }
                else {
                    String texteInput = new String(dataBuffer, 0, stream-1);
                    System.out.println(texteInput);
                }
            }
        } catch (SocketException eDisconnect) {
            System.out.println("Sucessfully disconnected.");
        } catch (IOException eInOut) {
            System.out.println(eInOut);
        }
    }
}
