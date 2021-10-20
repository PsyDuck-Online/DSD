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
            // limpiamos argumentosRespuesta
            argumentosRespuesta.clear();
            // obtenemos los datos de la peticion en bytes[]
            byte[] buffer = new byte[1000];
            request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            // convertimos el byte[] -> ArrayList
            ByteArrayInputStream byteArray = new ByteArrayInputStream(request.getData());
            ObjectInputStream is = new ObjectInputStream(byteArray);
            ArrayList<String> aux_array = (ArrayList<String>) is.readObject();
            is.close();
            // agregamos los datos de aux_array a argumentosRespuesta
            for (String i : aux_array) {
                argumentosRespuesta.add(i);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class Not Found: " + ex.getMessage());
        }

        return argumentosRespuesta;
    }

    public void sendReply(ArrayList<String> arguments) {
        try {
            // arguments(ArrayList) -> byte[]
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(arguments);
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