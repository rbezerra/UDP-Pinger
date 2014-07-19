/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ifma.redes.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author rbezerra
 */
public class UDPServer {
    
    private static final int PORT = 9192;
    private static final int SIZE = 100;

    public static void main(String [] args) {

        try {

            DatagramSocket socket = new DatagramSocket(PORT);

            System.out.println("Server Up!");

            for (; ;) {

                DatagramPacket packet = new DatagramPacket(new byte[SIZE], SIZE);

                socket.receive(packet);

                System.out.println(packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()));

                socket.send(packet);
            }
            
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }
}
