/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifma.redes.udp;

import java.net.DatagramSocket;
import java.util.Scanner;

/**
 *
 * @author rbezerra
 */
public class UDPClient {

    private final static int PACKETSIZE = 100;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            Scanner in = new Scanner(System.in);
            String cap = in.nextLine();
            if (ipvalido(cap)) {
                String[] address = cap.split(":");
                String part_host = address[0];
                int part_port = Integer.parseInt(address[1]);

                System.out.println("host: " + part_host + "\nport: " + part_port);
            }else{
                System.out.println("IP INVALIDO");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    static boolean ipvalido(String ip){
        return ip.matches("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
    }
}
