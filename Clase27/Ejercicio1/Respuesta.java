import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Respuesta {
    private DatagramSocket aSocket;
    private DatagramPacket request;
    private DatagramPacket reply;
    private RemoteRef rr;
    ArrayList<Object> argumentosRespuesta;

    public Respuesta(int puerto) {
        try {
            aSocket = new DatagramSocket(puerto);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getRequest() {
        byte[] buffer = new byte[1000];
        request = new DatagramPacket(buffer, buffer.length);

        try {
            aSocket.receive(request);
            ByteArrayInputStream byteArray = new ByteArrayInputStream(request.getData());
            ObjectInputStream is = new ObjectInputStream(byteArray);
            Mensaje msg = (Mensaje) is.readObject();
            is.close();

            argumentosRespuesta = msg.getArguments();

            if (msg.getRequestId() != 0) {
                argumentosRespuesta.add(0, "./PACKET_LOST");
            } else {
                argumentosRespuesta.add(0, "./REQUEST");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return argumentosRespuesta;
    }

    public void sendReply(ArrayList<Object> arguments) {
        Mensaje msg = new Mensaje(1, 0, rr, 0, arguments);

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(msg);
            os.close();

            InetAddress ip_dest = request.getAddress();
            int puerto = request.getPort();
            reply = new DatagramPacket(bytes.toByteArray(), bytes.size(), ip_dest, puerto);
            aSocket.send(reply);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
