/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

import java.io.Console;
import java.io.PrintWriter;

    /**
     * clientWriteThread
     * Client thread that reads messages written in the console and
     * send it to the server through the PrintWriter in argument.
     */

public class clientWriteThread extends Thread {
    PrintWriter outToServer;
    Console userInput;
    String lineInput = "";
    boolean stop = false;
    
    public clientWriteThread(PrintWriter outToServer, Console userInput) {
        this.userInput = userInput;
        this.outToServer = outToServer;
    }
    
    @Override
    public void run() {
        while (!stop) {
            lineInput = userInput.readLine(">");
            outToServer.println(lineInput);
            if (lineInput.contains("byebye")) {
                stop = true;
            }
        }
    }
}
