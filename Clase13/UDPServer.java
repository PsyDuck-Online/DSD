import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                String data = new String(request.getData(), StandardCharsets.UTF_8);
                String res = calculo(data);
                DatagramPacket reply = new DatagramPacket(res.getBytes(), res.getBytes().length, request.getAddress(),
                        request.getPort());
                aSocket.send(reply);
            }
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

    public static String calculo(String data) {
        String resultado = "";

        String[] aux_data = data.split(",");

        double incremento = Double.parseDouble(aux_data[0]);
        double ciclos = Double.parseDouble(aux_data[1]);

        double i = 0;
        double total = 0;

        for (int j = 0; j < ciclos; j++) {
            total += Math.sqrt(Math.pow(i, 3) + Math.pow(i, 4) + Math.pow(i, 5) + Math.pow(i, 6) + Math.pow(i, 7));
            i += incremento;
        }


        resultado += String.valueOf(total);
        
        return resultado;
    }
}
