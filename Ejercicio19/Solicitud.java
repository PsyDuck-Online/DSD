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

    private final int limite_intentos = 5;

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
        try {
            int counter = 0;
            boolean continueSendig = true;
            byte[] buffer = new byte[1000];

            // reiniciamos argumentosRespuesta
            argumentosRespuesta.clear();

            reply = new DatagramPacket(buffer, buffer.length);

            while (continueSendig && counter < limite_intentos) {
                // creamos el mensaje
                Mensaje msg_send = new Mensaje(0, counter, rr, operationId, arguments);

                // ArrrayList arguments -> bite[]
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bytes);
                os.writeObject(msg_send);
                os.close();

                request = new DatagramPacket(bytes.toByteArray(), bytes.size(), InetAddress.getByName(rr.getIP()),
                        rr.getPuerto());

                // enviamos la peticion
                aSocket.send(request);
                counter += 1;

                try {

                    // tratamos de recibir una respuesta, si es asi cerramos el ciclo modificando
                    // continueSending
                    System.out.println("Intento no: " + counter);
                    aSocket.receive(reply);
                    continueSendig = false;

                    // byte[] -> ArrayList
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reply.getData());
                    ObjectInputStream is = new ObjectInputStream(byteArray);
                    // ArrayList<String> aux_array = (ArrayList<String>) is.readObject();
                    Mensaje msg_recv = (Mensaje) is.readObject();

                    is.close();

                    argumentosRespuesta = msg_recv.getArguments();

                } catch (SocketTimeoutException e) {
                    // sin respuesta del servidor por 1 seg... volviendo a enviar
                }
            }

            // comprobamos que no se haya superado el limite de intentos
            // si se supero enviamos la opcion para el cierre del cliente
            if (counter >= limite_intentos) {
                argumentosRespuesta.add("./CONN_LOSED");
            }

        } catch (IOException ex) {
            System.out.println("IO: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Not Found: " + ex.getMessage());
        }
        return argumentosRespuesta;
    }
}
