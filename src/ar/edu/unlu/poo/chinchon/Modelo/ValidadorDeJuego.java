package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ValidadorDeJuego implements Serializable{

    private ArrayList<Carta> juegoValido=new ArrayList<>();

    //EVALUA SI EL JUGADOR TIENE LOS JUEGOS Y LAS CONDICIONES NECESARIAS PARA CORTAR EN EL CASO DE QUE PUEDA
    //DEVUELVE LA CONDICION EN LA CUAL EL JUGADOR CORTA PARA ESTABLECER SI GANA O SUMAR SUS RESPECTIVOS PUNTOS
    public boolean puedeCortar(Mano mano) {
        if (tieneChinChon(mano)) {
            mano.setCortaCon(CortaCon.CHINCHON);
        } else {
            if (prueba1(mano)) {
                System.out.println("MANO");
                for(Carta c: mano.getCartas()){
                    System.out.println(c.getNumero()+ " de "+ c.getPalo());
                }
                System.out.println("PRIMER JUEGO");
                for(Carta c: mano.getPrimerJuego()){
                    System.out.println(c.getNumero()+ " de "+ c.getPalo());
                }
                System.out.println("SEGUNDO JUEGO");
                for(Carta c: mano.getSegundoJuego()){
                    System.out.println(c.getNumero()+ " de "+ c.getPalo());
                }
                if(mano.getCartas().size()>1) {
                    System.out.println("NO PUEDE CORTAR PORQUE LE SOBRA MAS DE UNA ");
                    limpiarJuegosMano(mano);
                    return false;
                }
                if ((mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 4) || (mano.getPrimerJuego().size() == 4 && mano.getSegundoJuego().size() == 3)) {
                    mano.setCortaCon(CortaCon.MENOS_DIEZ);
                } else if ((mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 3 &&  mano.getCartas().get(0).getNumero() <= 7) || (mano.getPrimerJuego().size() == 6  && mano.getCartas().get(0).getNumero() <= 7) || (mano.getSegundoJuego().size() == 6 &&  mano.getCartas().get(0).getNumero() <= 7)) {
                    mano.setCortaCon(CortaCon.SOBRA_CARTA);
                }
                else{
                    limpiarJuegosMano(mano);
                    return false;
                }
            }
            else{
                limpiarJuegosMano(mano);
                if(prueba2(mano)){
                    if(mano.getCartas().size()>1) {
                        limpiarJuegosMano(mano);
                        return false;
                    }
                    else if ((mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 4) || (mano.getPrimerJuego().size() == 4 && mano.getSegundoJuego().size() == 3)) {
                        mano.setCortaCon(CortaCon.MENOS_DIEZ);
                    } else if ((mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 3 &&  mano.getCartas().get(0).getNumero() <= 7) || (mano.getPrimerJuego().size() == 6  && mano.getCartas().get(0).getNumero() <= 7) || (mano.getSegundoJuego().size() == 6 &&  mano.getCartas().get(0).getNumero() <= 7)) {
                        mano.setCortaCon(CortaCon.SOBRA_CARTA);
                    }
                    else{
                        limpiarJuegosMano(mano);
                        return false;
                    }
                }
                else{
                    limpiarJuegosMano(mano);
                    return false;
                }
            }
        }
        return true;
    }

    //CALCULA LA CANTIDAD DE PUNTOS A SUMAR DEL JUGADOR, DEJA DE LADO LAS CARTAS QUE FORMAN JUEGOS
    //YA QUE ESAS NO SE SUMAN
    public int calcularPuntos(Mano mano){
        if(mano.getCartas().size()>1) {
            sacarCartasJuego(mano);
        }
        int suma=0;
        for(Carta c: mano.getCartas()){
            suma+=c.getNumero();
        }
        return suma;
    }

    //PERMITE ORDENAR LAS CARTAS DE LA MANO DE UN JUGADOR POR NUMERO
    private void ordenarCartasPorValor(Mano mano) {
        Collections.sort(mano.getCartas(), new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getNumero() - o2.getNumero();
            }
        });
    }

    //PERMITE ORDENAR LAS CARTAS DE LA MANO DE UN JUGADOR POR PALO
    private void ordenarCartasPorPalo(Mano mano){
        Collections.sort(mano.getCartas(), new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getPalo().compareTo(o2.getPalo());
            }
        });
    }

    //PRIMER PRUEBA PARA ARMAR LOS JUEGOS DE UNA MANO
    private boolean prueba1(Mano mano) {
        if (tieneEscalera(mano)) {
            if (!tieneNumerosIguales(mano)) {
                if (tieneEscalera(mano)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        if(mano.getPrimerJuego().size()==6||mano.getSegundoJuego().size()==6){
            return true;
        }
        return false;
    }

    //SEGUNDA PRUEBA PARA ARMAR LOS JUEGOS DE UNA MANO, SIRVE EN CASOS ESPECIALES DONDE UNA CARTA
    //PUEDE FORMAR PARTE DE DOS JUEGOS
    private boolean prueba2(Mano mano){
        if (tieneNumerosIguales(mano)) {
            if (!tieneEscalera(mano)) {
                if (tieneNumerosIguales(mano)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    //EN EL CASO DE QUE EL JUGADOR NO PUEDA CORTAR VACIA LOS JUEGOS Y LOS DEVUELVE A LA MANO
    private void limpiarJuegosMano(Mano mano){
        mano.recibirCartas(mano.getPrimerJuego());
        mano.recibirCartas(mano.getSegundoJuego());
        mano.vaciarPrimerJuego();
        mano.vaciarSegundoJuego();
        juegoValido.clear();
    }

    //EVALUA SI UN JUGADOR TIENE CHINCHON
    private boolean tieneChinChon(Mano mano) {
        this.ordenarCartasPorValor(mano);
        Carta c = mano.getCartas().get(0);
        for (int i = 1; i < mano.getCartas().size(); i++) {
            if (mano.getCartas().get(i).getPalo() != c.getPalo() || mano.getCartas().get(i).getNumero() != c.getNumero() + 1) {
                return false;
            }
            c = mano.getCartas().get(i);
        }
        mano.vaciarMano();
        return true;
    }

    //EVALUA SI UN JUGADOR TIENE UN JUEGO DE ESCALERA
    private boolean tieneEscalera(Mano mano) {
        this.ordenarCartasPorValor(mano);
        this.ordenarCartasPorPalo(mano);
        System.out.println("MANO");
        for(Carta c: mano.getCartas()){
            System.out.println(c.getNumero()+ " de "+ c.getPalo());
        }
        System.out.println("PRIMER JUEGO");
        for(Carta c: mano.getPrimerJuego()){
            System.out.println(c.getNumero()+ " de "+ c.getPalo());
        }
        System.out.println("SEGUNDO JUEGO");
        for(Carta c: mano.getSegundoJuego()){
            System.out.println(c.getNumero()+ " de "+ c.getPalo());
        }
        boolean primero = mano.getPrimerJuego().isEmpty();

        for (int i = 0; i < mano.getCartas().size(); i++) {
            juegoValido.clear();
            juegoValido.add(mano.getCartas().get(i));

            for (int j = i + 1; j < mano.getCartas().size(); j++) {
                if (mano.getCartas().get(j).getNumero() == mano.getCartas().get(j - 1).getNumero() + 1 && mano.getCartas().get(j).getPalo() == mano.getCartas().get(j - 1).getPalo()) {
                    juegoValido.add(mano.getCartas().get(j));
                } else {
                    break;
                }
            }

            if (primero) {
                if (mano.getPrimerJuego().size() < juegoValido.size()) {
                    mano.vaciarPrimerJuego();
                    mano.agregarPrimerJuego(new ArrayList<>(juegoValido));
                }
            } else {
                if (mano.getSegundoJuego().size() < juegoValido.size()) {
                    mano.vaciarSegundoJuego();
                    mano.agregarSegundoJuego(new ArrayList<>(juegoValido));
                }
            }
        }
        if (primero) {
            System.out.println("MANO PRIMERO");
            for(Carta c: mano.getCartas()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            System.out.println("PRIMER JUEGO");
            for(Carta c: mano.getPrimerJuego()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            System.out.println("SEGUNDO JUEGO");
            for(Carta c: mano.getSegundoJuego()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            if (mano.getPrimerJuego().size() < 3) {
                mano.vaciarPrimerJuego();
                return false;
            }
            else{
                mano.getCartas().removeAll(mano.getPrimerJuego());
            }
        } else {
            System.out.println("MANO ELSE");
            for(Carta c: mano.getCartas()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            System.out.println("PRIMER JUEGO");
            for(Carta c: mano.getPrimerJuego()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            System.out.println("SEGUNDO JUEGO");
            for(Carta c: mano.getSegundoJuego()){
                System.out.println(c.getNumero()+ " de "+ c.getPalo());
            }
            if (mano.getSegundoJuego().size() < 3) {
                mano.vaciarSegundoJuego();
                return false;
            }
            else{
                mano.getCartas().removeAll(mano.getSegundoJuego());
            }
        }
        return true;
    }

    //EVALUA SI UN JUGADOR TIENE UN JUEGO DE NUMEROS IGUALES
    private boolean tieneNumerosIguales(Mano mano) {
        boolean primero = false;
        if(mano.getPrimerJuego().size()==0){
            primero=true;
        }

        for (Carta carta : mano.getCartas()) {
            juegoValido.clear();
            for (Carta c : mano.getCartas()) {
                if (carta.getNumero() == c.getNumero()) {
                    juegoValido.add(c);
                }
            }
            if (primero) {
                if (mano.getPrimerJuego().size() < juegoValido.size()) {
                    mano.vaciarPrimerJuego();
                    mano.agregarPrimerJuego(new ArrayList<>(juegoValido));
                }
            }else {
                if (mano.getSegundoJuego().size() < juegoValido.size()) {
                    mano.vaciarSegundoJuego();
                    mano.agregarSegundoJuego(new ArrayList<>(juegoValido));
                }
            }
        }
        if (primero) {
            if (mano.getPrimerJuego().size() < 3) {
                mano.vaciarPrimerJuego();
                return false;
            }
            else{
                mano.getCartas().removeAll(mano.getPrimerJuego());
            }
        } else if(!primero){
            if (mano.getSegundoJuego().size() < 3) {
                mano.vaciarSegundoJuego();
                return false;
            }
            else{
                mano.getCartas().removeAll(mano.getSegundoJuego());
            }
        }
        return true;
    }

    //SIRVE PARA QUE AL CALCULAR LOS PUNTOS NO SE SUMEN LAS CARTAS QUE FORMAN JUEGOS
    private void sacarCartasJuego(Mano mano){
        if(mano.getCortaCon()==null) {
            if (tieneEscalera(mano)) {
                if (!tieneNumerosIguales(mano)) {
                    tieneEscalera(mano);
                }
                mano.quitarCartas(mano.getPrimerJuego());
                mano.quitarCartas(mano.getSegundoJuego());
            } else {
                if (tieneNumerosIguales(mano)) {
                    if (!tieneEscalera(mano)) {
                        tieneNumerosIguales(mano);
                    }
                }
                mano.quitarCartas(mano.getPrimerJuego());
                mano.quitarCartas(mano.getSegundoJuego());
            }
        }
        else{
            mano.quitarCartas(mano.getPrimerJuego());
            mano.quitarCartas(mano.getSegundoJuego());
        }
    }
}
