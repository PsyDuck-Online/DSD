/* 
* Clase: Desarrollo de Sistemas Distribuidos.
* Proyecto: 3.
* Alumno: Baltazar Real David.
* Grupo: 4CM11.
*/

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
    private ArrayList<Object> argumentosRespuesta;

    private final int limite = 10;

    public Solicitud() {
        try {
            aSocket = new DatagramSocket();
            argumentosRespuesta = new ArrayList<>();
            aSocket.setSoTimeout((int) 1000L);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // ---------------
    // Funciones
    // ---------------

    // Hace una peticion al servidor
    // Devuelve una lista con los objetos enviados desde el servidor
    public ArrayList<Object> doOperation(RemoteRef rr, int operationId, ArrayList<Object> arguments) {
        int intento = 0;
        boolean contSending = true;
        byte[] buffer = new byte[1000];

        reply = new DatagramPacket(buffer, buffer.length);

        argumentosRespuesta.clear();

        while (contSending && intento < limite) {
            try {
                Mensaje msgEnv = new Mensaje(0, intento, operationId, rr, arguments);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bytes);
                os.writeObject(msgEnv);
                os.close();

                InetAddress ip_destino = InetAddress.getByName(rr.getIp());
                int puerto = rr.getPuerto();
                request = new DatagramPacket(bytes.toByteArray(), bytes.size(), ip_destino, puerto);

                intento++;
                System.out.println(String.format("Intento %d / %d", intento, limite));
                aSocket.send(request);

                aSocket.receive(reply);
                contSending = false;

                ByteArrayInputStream byteArray = new ByteArrayInputStream(reply.getData());
                ObjectInputStream is = new ObjectInputStream(byteArray);
                Mensaje msgRecv = (Mensaje) is.readObject();
                is.close();

                argumentosRespuesta = msgRecv.getArguments();

            } catch (SocketTimeoutException e) {
                // No se hace nada
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (intento >= limite) {
                argumentosRespuesta.add(0, "./CONN_LOST");
            }

        }

        return argumentosRespuesta;
    }
}
