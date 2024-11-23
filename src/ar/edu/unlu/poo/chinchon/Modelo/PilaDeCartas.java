package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class PilaDeCartas implements Serializable {
    protected ArrayList<Carta> pilaDeCartas=new ArrayList<>();

    public ArrayList<Carta> getPilaDeCartas(){
        return this.pilaDeCartas;
    }

    public void setPilaDeCartas(ArrayList<Carta> pilaDeCartas){
        this.pilaDeCartas=pilaDeCartas;
    }

    public abstract Carta darCarta();

    public abstract void agregarCarta(Carta c);

    public void vaciarPila(){
        this.pilaDeCartas.clear();
    }

    public void barajar() {
        Collections.shuffle(pilaDeCartas);
    }

}
