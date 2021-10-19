import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;

/**
 *
 * @proyecto 2
 * @autor David Baltazar Real
 * @grupo 4CM11
 * 
 */

public class Servidor {

    private static final int puerto_inicial = 6789;

    public static void main(String[] args) {

        System.out.println("Servidor iniciado!!!");

        byte[] bytes;

        int puerto_escucha = Integer.parseInt(args[0]);

        int puerto_envio = Integer.parseInt(args[1]);

        DatagramSocket aSocket = null;
        DatagramPacket enviarToken = null;
        DatagramPacket recibirtToken = null;

        Token token = null;

        

        try {
            
            if (puerto_escucha == puerto_inicial) {
                System.out.println("Creando token");
                token = new Token("localhost", LocalTime.now(), puerto_envio, puerto_escucha, 0);
                System.out.println(token);
                Thread.sleep(3000);
            }

            aSocket = new DatagramSocket(puerto_escucha);
        
            while (true) {
                if (token != null) {
                    // serializable -> bytes
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(bs);
                    os.writeObject(token);
                    os.close();
                    bytes = bs.toByteArray();
                    enviarToken = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(token.getIp()),
                            token.getPuerto_envio());
                    aSocket.send(enviarToken);
                    System.out.println("\nEnviado a '" + token.getPuerto_envio() + "'\n");
                    token = null;
                } else {
                    recibirtToken = new DatagramPacket(new byte[1000], 1000);
                    aSocket.receive(recibirtToken);

                    ByteArrayInputStream byteArray = new ByteArrayInputStream(recibirtToken.getData());
                    ObjectInputStream is = new ObjectInputStream(byteArray);
                    token = (Token) is.readObject();
                    is.close();

                    System.out.println("Token recibido:\n " + token);

                    token.setPuerto_escucha(puerto_escucha);
                    token.setPuerto_envio(puerto_envio);
                    token.setUltima_hora_funcionamiento(LocalTime.now());

                    Thread.sleep(3000);
                }
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}
