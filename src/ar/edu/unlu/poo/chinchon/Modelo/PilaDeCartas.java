package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class PilaDeCartas implements Serializable {
    protected ArrayList<Carta> pilaDeCartas=new ArrayList<Carta>();

    public ArrayList<Carta> getPilaDeCartas(){
        return this.pilaDeCartas;
    }

    public void setPilaDeCartas(ArrayList<Carta> pilaDeCartas){
        this.pilaDeCartas=pilaDeCartas;
    }

    //PERMITE TOMAR UNA CARTA DE LA PILA
    public abstract Carta darCarta();

    //PERMITE AGREGAR UNA CARTA A LA PILA
    public abstract void agregarCarta(Carta c);

    //PERMITE DEJAR LA PILA VACIA (SIN CARTAS)
    public void vaciarPila(){
        this.pilaDeCartas.clear();
    }

    //MEZCLA LAS CARTAS DE LA PILA
    public void barajar() {
        Collections.shuffle(pilaDeCartas);
    }

}
