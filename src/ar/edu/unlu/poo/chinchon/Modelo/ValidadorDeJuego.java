package ar.edu.unlu.poo.chinchon.Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ValidadorDeJuego {

    private ArrayList<Carta> juegoValido=new ArrayList<>();

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

    private void ordenarCartasPorValor(Mano mano) {
        Collections.sort(mano.getCartas(), new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getNumero() - o2.getNumero();
            }
        });
    }
    private void ordenarCartasPorPalo(Mano mano){
        Collections.sort(mano.getCartas(), new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return o1.getPalo().compareTo(o2.getPalo());
            }
        });
    }

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
        return false;
    }

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

    public boolean puedeCortar(Mano mano) {
        if (tieneChinChon(mano)) {
            mano.setCortaCon(CortaCon.CHINCHON);
        } else {
            boolean primerPrueba = prueba1(mano);
            if (primerPrueba) {
                if (mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 4 || mano.getPrimerJuego().size() == 4 && mano.getSegundoJuego().size() == 3) {
                    mano.setCortaCon(CortaCon.MENOS_DIEZ);
                } else if (mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 3 && mano.getCartas().get(0).getNumero() <= 7 || mano.getPrimerJuego().size() == 6 && mano.getCartas().get(0).getNumero() <= 7 || mano.getSegundoJuego().size() == 6 && mano.getCartas().get(0).getNumero() <= 7) {
                    mano.setCortaCon(CortaCon.SOBRA_CARTA);
                } else {
                    mano.recibirCartas(mano.getPrimerJuego());
                    mano.recibirCartas(mano.getSegundoJuego());
                    mano.vaciarPrimerJuego();
                    mano.vaciarSegundoJuego();
                    juegoValido.clear();
                    return false;
                }
            }
            else{
                mano.recibirCartas(mano.getPrimerJuego());
                mano.recibirCartas(mano.getSegundoJuego());
                mano.vaciarPrimerJuego();
                mano.vaciarSegundoJuego();
                juegoValido.clear();
                if(prueba2(mano)){
                    if (mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 4 || mano.getPrimerJuego().size() == 4 && mano.getSegundoJuego().size() == 3) {
                        mano.setCortaCon(CortaCon.MENOS_DIEZ);
                    } else if (mano.getPrimerJuego().size() == 3 && mano.getSegundoJuego().size() == 3 && mano.getCartas().get(0).getNumero() <= 7 || mano.getPrimerJuego().size() == 6 && mano.getCartas().get(0).getNumero() <= 7 || mano.getSegundoJuego().size() == 6 && mano.getCartas().get(0).getNumero() <= 7) {
                        mano.setCortaCon(CortaCon.SOBRA_CARTA);
                    } else {
                        mano.recibirCartas(mano.getPrimerJuego());
                        mano.recibirCartas(mano.getSegundoJuego());
                        mano.vaciarPrimerJuego();
                        mano.vaciarSegundoJuego();
                        juegoValido.clear();
                        return false;
                    }
                }
            }
        }
        return true;
    }

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

    private boolean tieneEscalera(Mano mano) {
        this.ordenarCartasPorValor(mano);
        this.ordenarCartasPorPalo(mano);

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
            if (mano.getPrimerJuego().size() < 3) {
                mano.vaciarPrimerJuego();
                return false;
            }
            else{
                mano.getCartas().removeAll(mano.getPrimerJuego());
            }
        } else {
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

}
