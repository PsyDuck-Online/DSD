
// Ejercicio 8
import java.util.ArrayList;

public class PoligonoIrreg {
    public ArrayList<Coordenada> vertices;

    public PoligonoIrreg() {
        vertices = new ArrayList<Coordenada>();
    }

    public void anadeVertice(Coordenada c) {
        vertices.add(c);
    }

    @Override
    public String toString() {
        String resultado = "";
        for (Coordenada vertice : vertices) {
            resultado += vertice.toString();
        }
        return resultado;
    }

}
