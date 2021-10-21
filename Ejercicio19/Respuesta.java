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
    private int puertoServidor;
    private RemoteRef rr;
    ArrayList<String> argumentosRespuesta;

    public Respuesta(int puertoServidor) {
        try {

            this.puertoServidor = puertoServidor;
            aSocket = new DatagramSocket(this.puertoServidor);
            argumentosRespuesta = new ArrayList<>();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        }
    }

    public ArrayList<String> getRequest() {
        try {

            // argumentosRespuesta.clear();

            // obtenemos los datos de la peticion en bytes[]
            byte[] buffer = new byte[1000];
            request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);

            // convertimos el byte[] -> ArrayList
            ByteArrayInputStream byteArray = new ByteArrayInputStream(request.getData());
            ObjectInputStream is = new ObjectInputStream(byteArray);

            try {

                Mensaje msg = (Mensaje) is.readObject();
                argumentosRespuesta = msg.getArguments();

                if (msg.getRequestId() != 0) {

                    argumentosRespuesta.add("./PACKET_LOSED");
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Class Not Found:" + e.getMessage());
            }
            is.close();

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }

        return argumentosRespuesta;
    }

    public void sendReply(ArrayList<String> arguments) {
        try {
            Mensaje msg = new Mensaje(1, 1, rr, 0, arguments);

            // arguments(ArrayList) -> byte[]
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(msg);
            os.close();

            // enviamos la informacion pasada por el parametro arguments
            reply = new DatagramPacket(bytes.toByteArray(), bytes.size(), request.getAddress(), request.getPort());

            aSocket.send(reply);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}