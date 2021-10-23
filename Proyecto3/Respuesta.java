import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Respuesta {
    private DatagramSocket aSocket;
    private DatagramPacket request;
    private DatagramPacket reply;
    private RemoteRef rr;
    private ArrayList<String> argumentosRespuesta;

    public Respuesta(int puerto) {
        argumentosRespuesta = new ArrayList<>();
        try {
            aSocket = new DatagramSocket(puerto);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
    }

    public ArrayList<String> getRequest() {
        byte[] buffer = new byte[1000];
        request = new DatagramPacket(buffer, buffer.length);
        argumentosRespuesta.clear();
        try {
            aSocket.receive(request);
            ByteArrayInputStream byteArray = new ByteArrayInputStream(request.getData());
            ObjectInputStream is = new ObjectInputStream(byteArray);
            Mensaje mensaje = (Mensaje) is.readObject();
            is.close();
            if (mensaje.getRequestId() != 0) {
                argumentosRespuesta.add(0, ".PACKET_LOST");
            }
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());
        }
        return argumentosRespuesta;
    }

    public void sendReply(ArrayList<String> arguments) {
        Mensaje mensaje = new Mensaje(1, 0, rr, 0, arguments);

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(mensaje);
            os.close();

            reply = new DatagramPacket(bytes.toByteArray(), bytes.size(), request.getAddress(), request.getPort());
            aSocket.send(reply);
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
