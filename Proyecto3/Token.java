/* 
* Clase: Desarrollo de Sistemas Distribuidos.
* Proyecto: 3.
* Alumno: Baltazar Real David.
* Grupo: 4CM11.
*/

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Token implements Serializable {
    private String ip;
    private int puertoDestino;
    private int puertoEscucha;
    private ArrayList<Integer> listaPuertos;
    private int idProceso;
    private final LocalTime horaLevantamiento;
    private LocalTime ultimaHora;

    public Token(String ip, int puertoDestino, int puertoEscucha, int idProceso) {
        this.ip = ip;
        this.puertoDestino = puertoDestino;
        this.puertoEscucha = puertoEscucha;
        this.idProceso = idProceso;
        horaLevantamiento = LocalTime.now();
        ultimaHora = horaLevantamiento;
        listaPuertos = new ArrayList<>();
        listaPuertos.add(puertoEscucha);
    }

    // -------------------
    // Funciones
    // -------------------

    // Busca la posicion de un puerto
    // Devuelve la posicion del puerto
    public int buscarPuerto(int puerto) {
        return listaPuertos.indexOf(puerto);
    }

    // Agrega un puerto a la lista
    // Devuelve True si se pudo a gregar y False si no se agrego
    public boolean agregarPuerto(int puerto) {
        boolean condicion = false;

        boolean estaPuerto = listaPuertos.contains(puerto);
        if (!estaPuerto) {
            listaPuertos.add(puerto);
            condicion = true;
        }

        return condicion;
    }

    // Elimina un puerto de la lista
    // Devuelve true si se pudo agregar y false si no se pudo agregar
    public boolean eliminarPuerto(int puerto) {
        boolean condicion = false;

        int posicion = listaPuertos.indexOf(puerto);
        if (posicion != -1) {
            listaPuertos.remove(posicion);
            condicion = true;
        }

        return condicion;
    }

    // -------------------
    // Getters
    // -------------------

    public String getIp() {
        return ip;
    }

    public int getPuertoDestino() {
        return puertoDestino;
    }

    public int getPuertoEscucha() {
        return puertoEscucha;
    }

    public ArrayList<Integer> getListaPuertos() {
        return listaPuertos;
    }

    public int getIdProceso() {
        return idProceso;
    }

    public LocalTime getHoraLevantamiento() {
        return horaLevantamiento;
    }

    public LocalTime getUltimaHora() {
        return ultimaHora;
    }

    // -------------------
    // Setters
    // -------------------

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public void setPuertoEscucha(int puertoEscucha) {
        this.puertoEscucha = puertoEscucha;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public void setUltimaHora(LocalTime ultimaHora) {
        this.ultimaHora = ultimaHora;
    }

}
