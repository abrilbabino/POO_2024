package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Mano implements Serializable {
    private ArrayList<Carta> mano = new ArrayList<>();
    private ArrayList<Carta> primerJuego = new ArrayList<>();
    private ArrayList<Carta> segundoJuego = new ArrayList<>();
    private CortaCon cortaCon=null;
    private Carta cartaExtraTurno=null;

    public CortaCon getCortaCon(){
        return this.cortaCon;
    }

    public void setCortaCon(CortaCon cortaCon){
        this.cortaCon=cortaCon;
    }

    public ArrayList<Carta> getCartas(){
        return this.mano;
    }

    public Carta getCartaExtraTurno(){
        return cartaExtraTurno;
    }

    public void setCartaExtraTurno(Carta c){
        cartaExtraTurno=c;
    }

    public ArrayList<Carta> getPrimerJuego(){
        return primerJuego;
    }

    public ArrayList<Carta> getSegundoJuego(){
        return segundoJuego;
    }

    public void recibirCarta(Carta c) {
        mano.add(c);
    }

    public void recibirCartas(ArrayList<Carta> cartas){
        mano.addAll(cartas);
    }

    public void quitarCartas(ArrayList<Carta> cartas){
        mano.removeAll(cartas);
    }

    public void vaciarMano(){
        mano.clear();
    }

    public void agregarPrimerJuego(ArrayList<Carta> primero){
        primerJuego.addAll(primero);
    }

    public void agregarSegundoJuego(ArrayList<Carta> segundo){
        segundoJuego.addAll(segundo);
    }

    public void vaciarPrimerJuego(){
        primerJuego.clear();
    }

    public void vaciarSegundoJuego(){
        segundoJuego.clear();
    }

    //PERMITE MOVER LAS CARTAS DE LA MANO PARA PODER ORDENAR LOS JUEGOS
    public void moverCartas(int p1, int p2) {
        Carta temp=mano.get(p1-1);
        mano.set(p1-1,mano.get(p2-1));
        mano.set(p2-1,temp);
    }

    //PERMITE INTERCAMBIAR LA POSICION DE LA ULTIMA CARTA LEVANTA Y UNA CARTA DE LA MANO
    public void moverCartaExtra(int p1){
        Carta temp=mano.get(p1-1);
        mano.set(p1-1,cartaExtraTurno);
        setCartaExtraTurno(temp);
    }

    //PERMITE TIRAR UNA CARTA DE LA MANO
    public Carta tirarCarta(Carta c){
        mano.remove(c);
        return  c;
    }

    //PERMITE TIRAR LA ULTIMA CARTA LEVANTADA DE LA MANO
    public Carta tirarCartaExtra(){
        Carta c=this.getCartaExtraTurno();
        setCartaExtraTurno(null);
        return tirarCarta(c);
    }

    //SIRVE CUANDO SE TIRA UNA CARTA DE LA MANO QUE NO ES LA ULTIMA LEVANTADA
    public Carta cambiarCartaExtraPorCartaMano(int posicionCarta){
        Carta c=mano.get(posicionCarta);
        mano.set(posicionCarta,cartaExtraTurno);
        setCartaExtraTurno(null);
        return tirarCarta(c);
    }
}