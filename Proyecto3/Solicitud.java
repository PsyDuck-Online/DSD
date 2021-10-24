import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Solicitud {
    private DatagramSocket aSocket;
    private DatagramPacket request;
    private DatagramPacket reply;
    ArrayList<String> argumentosRespuesta;

    private final int limite_intentos = 10;

    public Solicitud() {
        try {
            aSocket = new DatagramSocket();
            argumentosRespuesta = new ArrayList<>();
            aSocket.setSoTimeout((int) 1000L);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
    }

    public ArrayList<String> doOperation(RemoteRef rr, int operationId, ArrayList<String> arguments) {

        int intento = 0;
        boolean continue_sending = true;
        byte[] buffer = new byte[1000];

        argumentosRespuesta.clear();

        reply = new DatagramPacket(buffer, buffer.length);

        while (continue_sending && intento < limite_intentos) {
            try {
                // Creacion del mensaje
                Mensaje mensaje_env = new Mensaje(0, intento, rr, operationId, arguments);

                // Convertimos Mensaje a byte[]
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bytes);
                os.writeObject(mensaje_env);
                os.close();

                // Creamos la peticion(request) y la enviamos
                InetAddress ip_destino = InetAddress.getByName(rr.getIp());
                int puerto_destino = rr.getPuerto();

                request = new DatagramPacket(bytes.toByteArray(), bytes.size(), ip_destino, puerto_destino);

                System.out.println(String.format("Intento %d/%d", intento, limite_intentos));
                aSocket.send(request);
                intento += 1;

                // Recibimos la respuesta del servidor y evitamos que continue el ciclo
                aSocket.receive(reply);
                continue_sending = false;

                // Convertimos el byte[] a Mensaje y extraemos el ArrayList
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reply.getData());
                ObjectInputStream is = new ObjectInputStream(byteArray);
                Mensaje mensaje_recv = (Mensaje) is.readObject();
                is.close();

                argumentosRespuesta = mensaje_recv.getArguments();

            } catch (SocketTimeoutException e) {
                //System.out.println("Socket Timeout:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Class Not Found: " + e.getMessage());
            }

            if (intento >= limite_intentos) {
                argumentosRespuesta.add("./CONN_LOST");
            }
        }

        return argumentosRespuesta;
    }

}
