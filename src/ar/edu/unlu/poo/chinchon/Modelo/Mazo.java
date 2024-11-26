package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Mazo extends PilaDeCartas {

    public Mazo(){
        super();
        crearBaraja();
    }

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

    public boolean isVacio(){
        if(pilaDeCartas.size()==0){
            return true;
        }
        else{
            return false;
        }
    }

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

    @Override
    public void agregarCarta(Carta c) {
        pilaDeCartas.add(c);
    }

}
