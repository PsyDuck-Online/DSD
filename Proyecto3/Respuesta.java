import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Respuesta {
    private DatagramSocket aSocket;
    private DatagramPacket request;
    private DatagramPacket reply;
    private RemoteRef rr;
    private Token token;

    public Respuesta(int puerto) {
        try {
            aSocket = new DatagramSocket(puerto);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public Token getRequest() {
        byte buffer[] = new byte[1000];
        request = new DatagramPacket(buffer, buffer.length);
        try {
            // Recibimos el arreglo en bytes y lo transformamos a Mensaje
            aSocket.receive(request);
            ByteArrayInputStream byteArray = new ByteArrayInputStream(request.getData());
            ObjectInputStream is = new ObjectInputStream(byteArray);
            Mensaje mensaje = (Mensaje) is.readObject();
            is.close();

            token = mensaje.getToken();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return token;
    }

    public void sendReply(Token token) {
        Mensaje mensaje = new Mensaje(1, 0, 0, rr, token);

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(mensaje);
            os.close();

            InetAddress address = request.getAddress();
            int port = request.getPort();
            reply = new DatagramPacket(bytes.toByteArray(), bytes.size(), address, port);
            aSocket.send(reply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
