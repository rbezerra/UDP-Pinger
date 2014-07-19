/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifma.redes.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author rbezerra
 */
public class UDPClient {

    private final static int PACKETSIZE = 100;

    public static void main(String[] args) {
        DatagramPacket packet = null;
        PingRequest ping = null;

        try {
            Scanner in = new Scanner(System.in);
            String cap = in.nextLine();
            if (ipValido(cap)) {                        
                do {
                    ping = dadosPacote(cap); 
                    packet = montaPacote("PING", ping.getEndereco(), ping.getPorta());
                    enviaPacote(packet);
                } while (ping.getParametro().equals("-t"));
            } else {
                System.out.println("IP INVALIDO");
            }

        } catch (Exception e) {
            System.out.println("Erro main: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //valida IP no formato 0.0.0.0:0000 -t
    static boolean ipValido(String command) {
        String ip = command.split(" ")[0];
        return ip.matches("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
    }

    static PingRequest dadosPacote(String comandoDigitado) {
        PingRequest ping = new PingRequest();
        ping.setEndereco(comandoDigitado.split(":")[0]);
        ping.setPorta(Integer.parseInt(comandoDigitado.split(":")[1].split(" ")[0]));

        if (comandoDigitado.split(" ").length > 1) {
            ping.setParametro(comandoDigitado.split(" ")[1]);
        }else{
            ping.setParametro("");
        }
        
        return ping;

    }

    //monta pacote com os dados capturados
    static DatagramPacket montaPacote(String data, String host, int port) {
        byte[] packetData;
        InetAddress packetHost;
        int packetPort;
        DatagramPacket packet;

        packetData = data.getBytes();
        packetPort = port;

        try {
            packetHost = InetAddress.getByName(host);
            packet = new DatagramPacket(packetData, packetData.length, packetHost, packetPort);
            return packet;
        } catch (UnknownHostException e) {
            System.out.println("Erro monta pacote: " + e.getMessage());
            return null;
        }
    }

    //imprime a mensagem dos dados obtidos depois do ping
    static void enviaPacote(DatagramPacket packet) {
        long iniciaContagem, fimContagem, tempoFinal;
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            iniciaContagem = System.currentTimeMillis();
            socket.send(packet);
            socket.setSoTimeout(3000);
            packet.setData(new byte[PACKETSIZE]);
            socket.receive(packet);
            fimContagem = System.currentTimeMillis();
            tempoFinal = fimContagem - iniciaContagem;
            String mensagem = packet.getLength() + " bytes from " + packet.getAddress() + ": time=" + tempoFinal;
            System.out.println(mensagem);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
