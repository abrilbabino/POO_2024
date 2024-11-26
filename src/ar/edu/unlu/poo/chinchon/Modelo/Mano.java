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
    private Carta cartaExtraTurno;

    public CortaCon getCortaCon(){
        return this.cortaCon;
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

    public void recibirCarta(Carta c) {
        mano.add(c);
    }


    public void moverCartas(int p1, int p2) {
        Carta temp=mano.get(p1-1);
        mano.set(p1-1,mano.get(p2-1));
        mano.set(p2-1,temp);
    }

    public void vaciarMano(){
        mano.clear();
    }

    public Carta tirarCarta(Carta c){
        mano.remove(c);
        return  c;
    }

    public Carta tirarCartaExtra(){
        Carta c=this.getCartaExtraTurno();
        setCartaExtraTurno(null);
        return tirarCarta(c);
    }

    public Carta cambiarCartaExtraPorCartaMano(int posicionCarta){
        Carta c=mano.get(posicionCarta);
        mano.set(posicionCarta,cartaExtraTurno);
        setCartaExtraTurno(null);
        return tirarCarta(c);
    }
    public void sacarCartasJuego(){
        if(tieneEscalera()){
            if(!tieneNumerosIguales()){
                tieneEscalera();
            }
        }
        else{
            if(tieneNumerosIguales()){
                if(!tieneEscalera()){
                    tieneNumerosIguales();
                }
            }
        }
    }
    public int calcularPuntos(){
        if(mano.size()>1) {
            sacarCartasJuego();
        }
        int suma=0;
        for(Carta c: mano){
            suma+=c.getNumero();
        }
        System.out.println("SUMA TOTAL "+ suma);
        return suma;
    }

    private void ordenarCartasPorValor() {
        Collections.sort(mano, new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getNumero() - o2.getNumero();
            }
        });
    }
    private void ordenarCartasPorPalo(){
        Collections.sort(mano, new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getPalo().compareTo(o2.getPalo());
            }
        });
    }
    public boolean puedeCortar() {

        if (tieneChinChon()) {
            cortaCon = CortaCon.CHINCHON;
        }
        else{
           if(tieneEscalera()){
               if(!tieneNumerosIguales()) {
                   tieneEscalera();
               }
           }
           else if(tieneNumerosIguales()){
               if(!tieneEscalera()) {
                   tieneNumerosIguales();
               }
           }
           else{
               return false;
           }
           if (primerJuego.size() == 3 && segundoJuego.size() == 4 || primerJuego.size() == 4 && segundoJuego.size() == 3) {
               cortaCon = CortaCon.MENOS_DIEZ;
           } else if(primerJuego.size()==3&& segundoJuego.size()==3 && mano.get(0).getNumero()<=7||primerJuego.size()==6 && mano.get(0).getNumero()<=7 ||segundoJuego.size()==6&& mano.get(0).getNumero()<=7){
               cortaCon = CortaCon.SOBRA_CARTA;
           }
           else{
               mano.addAll(primerJuego);
               mano.addAll(segundoJuego);
               primerJuego.clear();
               segundoJuego.clear();
               return false;
           }
        }
        System.out.println("MANO DEL QUE CORTA");
        for (Carta c: mano){
            System.out.println("Numero: "+ c.getNumero()+ " Palo: "+c.getPalo().toString());
        }
        System.out.println("JUEGO 1");
        for(Carta c: primerJuego){
            System.out.println("Numero: "+ c.getNumero()+ " Palo: "+c.getPalo().toString());
        }
        System.out.println("JUEGO 2");
        for(Carta c: segundoJuego){
            System.out.println("Numero: "+ c.getNumero()+ " Palo: "+c.getPalo().toString());
        }
        return true;
    }

    private boolean tieneChinChon() {
        this.ordenarCartasPorValor();
        Carta c = mano.get(0);
        for (int i = 1; i < mano.size(); i++) {
            if (mano.get(i).getPalo() != c.getPalo() || mano.get(i).getNumero() != c.getNumero() + 1) {
                return false;
            }
            c = mano.get(i);
        }
        mano.clear();
        return true;
    }

    private boolean tieneEscalera() {
        this.ordenarCartasPorValor();
        this.ordenarCartasPorPalo();

        ArrayList<Carta> probando = new ArrayList<>();
        boolean primero = primerJuego.isEmpty();

        for (int i = 0; i < mano.size(); i++) {
            probando.clear();
            probando.add(mano.get(i));

            for (int j = i + 1; j < mano.size(); j++) {
                if (mano.get(j).getNumero() == mano.get(j - 1).getNumero() + 1 && mano.get(j).getPalo() == mano.get(j - 1).getPalo()) {
                    probando.add(mano.get(j));
                } else {
                    break;
                }
            }

            if (primero) {
                if (primerJuego.size() < probando.size()) {
                    primerJuego.clear();
                    primerJuego.addAll(probando);
                }
            } else {
                if (segundoJuego.size() < probando.size()) {
                    segundoJuego.clear();
                    segundoJuego.addAll(probando);
                }
            }
        }

        if (primerJuego.size() < 3 && segundoJuego.size() < 3) {
            primerJuego.clear();
            segundoJuego.clear();
            return false;
        } else if (primero) {
            if (primerJuego.size() < 3) {
                primerJuego.clear();
                return false;
            }
        } else {
            if (segundoJuego.size() < 3) {
                segundoJuego.clear();
                return false;
            }
        }

        if (primero) {
            mano.removeAll(primerJuego);
        } else {
            mano.removeAll(segundoJuego);
        }

        return true;
    }


    private boolean tieneNumerosIguales() {
        boolean primero = false;
        if(primerJuego.size()==0){
            primero=true;
        }
        ArrayList<Carta> probando = new ArrayList<>();
        for (Carta carta : mano) {
            probando.clear();
            for (Carta c : mano) {
                if (carta.getNumero() == c.getNumero()) {
                    probando.add(c);
                }
            }
            if (primero) {
                if (primerJuego.size() < probando.size()) {
                    primerJuego.removeAll(primerJuego);
                    primerJuego.addAll(probando);
                }
            }else {
                if (segundoJuego.size() < probando.size()) {
                    segundoJuego.removeAll(segundoJuego);
                    segundoJuego.addAll(probando);
                }
            }
        }
        if (primerJuego.size() < 3 && segundoJuego.size() < 3) {
            primerJuego.clear();
            segundoJuego.clear();
            return false;
        } else if (primero) {
            if (primerJuego.size() < 3) {
                primerJuego.clear();
                return false;
            }
        } else if(!primero){
            if (segundoJuego.size() < 3) {
                segundoJuego.clear();
                return false;
            }
        }
        if(primero) {
            mano.removeAll(primerJuego);
        }
        else{
            mano.removeAll(segundoJuego);
        }
        return true;
    }


}
