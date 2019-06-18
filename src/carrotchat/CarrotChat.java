/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */
package carrotchat;

import carrotchat.graphic.CarrotWindow;
import carrotchat.model.TCPClient;
import carrotchat.model.TCPMultiServer;
import carrotchat.model.UDPClient;
import carrotchat.model.UDPServer;
import carrotchat.model.TCPServer;

public class CarrotChat {

    /**
     * @param args the command line arguments
     * [mst port] [cu adress port]
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Donner le mode :");
            System.out.println("c : mode client TCP");
            System.out.println("g : mode graphique");
            System.out.println("m : mode serveur TCP multi-client");
            System.out.println("s : mode serveur UDP");
            System.out.println("t : mode serveur TCP client unique (haha)");
            System.out.println("u : mode client UDP");
        }
        else if (args.length >= 1) {
            if (args[0].substring(0,1).equals("g")) {
                System.out.println("Lancement du mode graphique.");
                CarrotWindow win = new CarrotWindow("CarrotChat");
            }
            if (args[0].substring(0,1).equals("m")) {
                System.out.println("Mode serveur TCP multi");
                TCPMultiServer carrote = new TCPMultiServer();
                carrote.launch();
            }
            if (args[0].substring(0,1).equals("t")) {
                System.out.println("Mode serveur TCP");
                TCPServer carrote = new TCPServer();
                carrote.launch();
            }
            if (args[0].substring(0,1).equals("s")) {
                UDPServer carrote = null;
                if (args.length == 1) {
                    carrote = new UDPServer();
                    carrote.launch();
                }
                else if (args.length >= 2) {
                    carrote = new UDPServer(Integer.parseInt(args[1]));
                    carrote.launch();
                }
            }
            else if (args[0].substring(0, 1).equals("c")) {
                System.out.println("Mode client TCP");
                TCPClient lapin = null;
                if (args.length == 1) {
                    System.out.println("Pas de serveur entré en argument");
                }
                if (args.length == 2) {
                    System.out.println("Port par défaut : 8080");
                    lapin = new TCPClient(args[1]);
                    lapin.launch();
                }
                else if (args.length >= 2) {
                    lapin = new TCPClient(args[1], Integer.parseInt(args[2]));
                    lapin.launch();
                }
            }
            else if (args[0].substring(0, 1).equals("u")) {
                System.out.println("Mode client UDP");
                UDPClient lapin = null;
                if (args.length == 1) {
                    System.out.println("Pas de serveur entré en argument");
                }
                if (args.length == 2) {
                    lapin = new UDPClient(args[1]);
                }
                else if (args.length >= 2) {
                    lapin = new UDPClient(args[1], Integer.parseInt(args[2]));
                }
            }
        }
    }
    
}
