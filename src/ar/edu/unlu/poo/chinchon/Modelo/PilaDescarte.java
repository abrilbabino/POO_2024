package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;

public class PilaDescarte extends PilaDeCartas{
    private Carta cartaTope;

    public Carta getTope() {
        return this.cartaTope;
    }

    private void setCartaTope() {
        if (pilaDeCartas.size() > 1) {
            this.cartaTope = pilaDeCartas.get(pilaDeCartas.size() - 1);
            pilaDeCartas.remove(cartaTope);
        }
        else{
            this.cartaTope=null;
            pilaDeCartas.remove(cartaTope);
        }
    }

    //PERMITE LEVANTAR LA CARTA DEL TOPE DE LA PILA DESCARTE
    @Override
    public Carta darCarta() {
        Carta carta = cartaTope;
        setCartaTope();
        return carta;
    }

    //PERMITE AGREGAR UNA CARTA A LA PILA DE DESCARTE, DEJANDOLA COMO TOPE
    @Override
    public void agregarCarta(Carta c) {
        pilaDeCartas.add(cartaTope);
        pilaDeCartas.add(c);
        setCartaTope();
    }

}
