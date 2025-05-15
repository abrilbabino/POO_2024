package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Mazo extends PilaDeCartas {

    public Mazo(){
        super();
        crearBaraja();
    }

    //CREA UNA BARAJA DE 48 CARTAS ESPAÑOLAS Y LAS MEZCLA
    private void crearBaraja() {
        Carta carta;
        for (Palo p : Palo.values()) {
            for (int j = 1; j <= 12; j++) {
                carta = new Carta(p, j);
                agregarCarta(carta);
            }
        }
        super.barajar();
    }

    //PERMITE DETERMINAR SI EL MAZO TIENE CARTAS O NO
    public boolean isVacio(){
        if(pilaDeCartas.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

    //PERMITE LEVANTAR UNA CARTA DEL MAZO, VA A LEVANTAR LA CARTA QUE ESTE MAS ARRIBA
    @Override
    public Carta darCarta() {
        Carta carta = null;
        if (pilaDeCartas.size() > 0) {
            carta = pilaDeCartas.get(pilaDeCartas.size() - 1);
            pilaDeCartas.remove(pilaDeCartas.size() - 1);
            return carta;
        }
        return carta;
    }

    //PERMITE AGREGAR UNA CARTA AL MAZO
    @Override
    public void agregarCarta(Carta c) {
        pilaDeCartas.add(c);
    }

}
