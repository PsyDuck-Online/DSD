import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {

            long inicio = System.currentTimeMillis();
            aSocket = new DatagramSocket();
            byte[] m = args[0].getBytes(); // incrementos,ciclos
            InetAddress aHost = InetAddress.getByName(args[1]);
            int serverPort = 6789;
            // Envio de datos
            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
            aSocket.send(request);
            // Recv de datos
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            String total = new String(reply.getData());
            long fin = System.currentTimeMillis();
            // Impresion de datos
            System.out.println("total: " + total + " || duracion: " + (fin - inicio) + "ms");

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }
}