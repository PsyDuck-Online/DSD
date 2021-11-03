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

public class Solicitud {
    private DatagramSocket aSocket;
    private DatagramPacket request;
    private DatagramPacket reply;

    private final int limite = 10;

    public Solicitud() {
        try {
            aSocket = new DatagramSocket();
            aSocket.setSoTimeout((int) 1000L);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public boolean doOperation(RemoteRef rr, int operationId, Token token) {

        int intento = 0;
        boolean cont_sending = true;
        byte buffer[] = new byte[1000];
        boolean operation_state = false;

        reply = new DatagramPacket(buffer, buffer.length);

        while (cont_sending && intento < limite) {
            try {
                Mensaje mensaje_env = new Mensaje(0, 0, 0, rr, token);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bytes);
                os.writeObject(mensaje_env);
                os.close();

                InetAddress ip_destino = InetAddress.getByName(rr.getIp());
                int puerto_destino = rr.getPuerto();

                request = new DatagramPacket(bytes.toByteArray(), bytes.size(), ip_destino, puerto_destino);

                System.out.println("Intento " + intento + " de " + limite);
                aSocket.send(request);
                intento++;

                aSocket.receive(reply);
                cont_sending = false;

                ByteArrayInputStream byteArray = new ByteArrayInputStream(reply.getData());
                ObjectInputStream is = new ObjectInputStream(byteArray);
                Mensaje mensaje_recv = (Mensaje) is.readObject();
                is.close();

                if (token.equals(mensaje_recv.getToken())) {
                    operation_state = true;
                }

            } catch (SocketTimeoutException e) {
                // No hacemos nada
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return operation_state;
    }
}
