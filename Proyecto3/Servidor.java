/* 
* Clase: Desarrollo de Sistemas Distribuidos.
* Proyecto: 3.
* Alumno: Baltazar Real David.
* Grupo: 4CM11.
*/

import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("java Servidor <puerto_escucha> <puerto_destino> <token: 1 o 0>");
            System.exit(0);
        }

        // Variables

        String ip = "localhost";
        int puertoEscucha = Integer.parseInt(args[0]);
        int puertoDestino = Integer.parseInt(args[1]);
        Token token = null;

        RemoteRef rr = new RemoteRef(ip, puertoDestino);
        Respuesta respuesta = new Respuesta(puertoEscucha);
        Solicitud solicitud = new Solicitud();
        ArrayList<Object> argsEnviar = new ArrayList<>();

        try {
            if (args[2].equals("1")) {
                token = new Token(ip, puertoDestino, puertoEscucha, 0);
                token.agregarPuerto(puertoEscucha);
                System.out.println("Token creado.");
                Thread.sleep(3000);
            }

            System.out.println("Servidor Iniciado.\n");
            while (true) {
                argsEnviar.clear();

                if (token != null) {
                    // Se tiene el token

                    // Se retiene el token
                    Thread.sleep(3000);

                    // Actualizamos el token
                    token.agregarPuerto(puertoDestino);
                    token.setPuertoEscucha(puertoEscucha);
                    token.setPuertoDestino(puertoDestino);

                    // Direccionamos el mensaje
                    rr.setPuerto(token.getPuertoDestino());

                    // Creamos la lista de los argumentos que vamos a enviar
                    System.out.println("Enviando el token a: " + puertoDestino);
                    argsEnviar.add(token);

                    // Enviamos el token y esperamos una respuesta
                    ArrayList<Object> argsRecibidos = solicitud.doOperation(rr, 0, argsEnviar);

                    // Comprobamos que no se haya perdido la conexion
                    // Manejamos la perdida de conexion y si se completo el envio borramos el token
                    String statusOperacion = (String) argsRecibidos.get(0);
                    if (statusOperacion.equals("./CONN_LOST")) {
                        int posicionPuertoActual = token.buscarPuerto(puertoEscucha);

                        // Borramos el puerto al que intentamos conectar
                        token.eliminarPuerto(puertoDestino);

                        // Escogemos un nuevo puerto destino
                        if (token.getListaPuertos().size() <= 1) {
                            System.out.println("No hay mas puertos en la lista");
                            System.exit(0);
                        } else if (posicionPuertoActual < token.getListaPuertos().size() - 1) {
                            puertoDestino = token.getListaPuertos().get(posicionPuertoActual + 1);
                        } else {
                            puertoDestino = token.getListaPuertos().get(0);
                        }
                        System.out.println("Error al enviar el token, enviando a otro puerto\n");
                    } else if (statusOperacion.equals("./RECIBIDO")) {
                        System.out.println("Token enviado con exito.\n");
                        token = null;
                    }
                } else {
                    // Se espera recibir el token
                    System.out.println("Esperando el token...");

                    ArrayList<Object> argsRecibidos = respuesta.getRequest();

                    // Separamos los argumentos recibidos
                    // String operacionStatus = (String) argsRecibidos.get(0);
                    token = (Token) argsRecibidos.get(1);

                    // Agregamos el acuse de recibido a la lista que se va enviar
                    argsEnviar.add("./RECIBIDO");

                    // Enviamos la respuesta al servidor previo
                    respuesta.sendReply(argsEnviar);

                    System.out.println("Token Recibido de: " + token.getPuertoEscucha() + "\n");

                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
