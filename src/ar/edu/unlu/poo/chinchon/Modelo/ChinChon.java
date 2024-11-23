package ar.edu.unlu.poo.chinchon.Modelo;

import java.time.temporal.JulianFields;
import java.util.ArrayList;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class ChinChon extends ObservableRemoto implements  IModelo{
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private PilaDescarte pilaDescarte;
    private int cantidadDeRondas;
    private Jugador ganador;
    private Jugador jugadorActual;

    public ChinChon() {
        jugadores = new ArrayList<>();
        mazo = new Mazo();
        pilaDescarte = new PilaDescarte();
        cantidadDeRondas=0;
    }

    @Override
    public PilaDescarte getPilaDescarte() throws RemoteException {
        return this.pilaDescarte;
    }

    @Override
    public Mazo getMazo() throws  RemoteException{
        return this.mazo;
    }

    @Override
    public ArrayList<Jugador> getJugadores() throws RemoteException{
        return this.jugadores;
    }

    @Override
    public Jugador getJugadorActual() throws RemoteException{
        return this.jugadorActual;
    }

    @Override
    public int getCantidadDeRondas() throws RemoteException{
        return this.cantidadDeRondas;
    }

    @Override
    public Jugador getGanador() throws RemoteException {
        return ganador;
    }

    @Override
    public void setGanador(Jugador j) throws RemoteException{
        this.ganador=j;
    }

    @Override
    public void agregarJugador(Jugador j) throws RemoteException{
        jugadores.add(j);
    }

    //SETEA EL TURNO DE LOS JUGADORES Y NOTIFICA CAMBIO DE TURNO
    @Override
    public void cambiarTurno() throws RemoteException {
        int jugador=jugadores.indexOf(jugadorActual);
        jugadorActual.setTurno(false);
        int siguienteJugador=(jugador+1) % jugadores.size();
        jugadorActual=jugadores.get(siguienteJugador);
        jugadorActual.setTurno(true);
        notificarObservadores(Eventos.CAMBIA_TURNO);
    }

    //REPARTE CARTAS DEL MAZO A LOS JUGADORES Y PONE UNA CARTA EN LA PILA DESCARTE
    @Override
    public void repartir() throws RemoteException {
        for(Jugador j: jugadores){
            Mano m=j.getMano();
            for (int i = 0; i < 7; i++) {
                m.recibirCarta(mazo.darCarta());
            }
        }
        pilaDescarte.agregarCarta(mazo.darCarta());
    }
    //SI HAY POR LO MENOS 2 JUGADORES INICIA EL JUEGO SINO NOTIFICA FALTAN JUGADORES
    @Override
    public boolean iniciar() throws RemoteException {
        if (this.jugadores.size() >= 2) {
            for (Jugador j : jugadores) {
                j.setEstado(EstadoJugador.JUGANDO);
            }
            jugadorActual=jugadores.get(0);
            jugadorActual.setTurno(true);
            repartir();
            notificarObservadores(Eventos.JUEGO_INICIADO);
        } else {
            notificarObservadores(Eventos.FALTAN_JUGADORES);
            return false;
        }
        return true;
    }

    //CUANDO EL MAZO QUEDA VACIO,BARAJA LA PILA DE DESCARTE MENOS LA CARTA TOPE QUE SE MANTIENE
    // Y LA PONE COMO MAZO
    @Override
    public void cambiarMazoPorPilaDescarte() throws RemoteException {
        notificarObservadores(Eventos.CAMBIA_MAZO);
        pilaDescarte.barajar();
        mazo.setPilaDeCartas(pilaDescarte.getPilaDeCartas());
        notificarObservadores(Eventos.CAMBIA_MAZO);
        pilaDescarte.vaciarPila();
    }

    //SI LA OPCION ES 1 AGARRA UNA CARTA DEL MAZO, SI LA OPCION ES 2 AGARRA LA CARTA TOPE DE LA PILA DESCARTE
    @Override
    public void agarrarCarta(int opcion) throws RemoteException {
        if (opcion == 1) {
            jugadorActual.sacarCartaDelMazo(mazo);
            notificarObservadores(Eventos.CAMBIA_MAZO);
            notificarObservadores(Eventos.CAMBIA_MANO);
            if (mazo.isVacio()) {
                cambiarMazoPorPilaDescarte();
                notificarObservadores(Eventos.CAMBIA_MAZO);
            }
        } else {
            jugadorActual.sacarCartaDeLaPiLaDescarte(pilaDescarte);
            notificarObservadores(Eventos.CAMBIA_CARTA_TOPE);
            notificarObservadores(Eventos.CAMBIA_MANO);
        }
    }

    //SI OPCION ESTA ENTRE 1 Y 7 SE TIRA UNA CARTA DE LA MANO SI OPCION ES 8 SE TIRA LA CARTA LEVANTADA
    //EN EL TURNO
    @Override
    public void tirarCarta(int opcion) throws RemoteException {
        Carta nuevoTope;
        if(opcion<8) {
            nuevoTope = jugadorActual.getMano().cambiarCartaExtraPorCartaMano(opcion-1);
            pilaDescarte.agregarCarta(nuevoTope);
            notificarObservadores(Eventos.CAMBIA_CARTA_TOPE);
            notificarObservadores(Eventos.CAMBIA_MANO);
        }
        else{
            nuevoTope= jugadorActual.getMano().tirarCartaExtra();
            pilaDescarte.agregarCarta(nuevoTope);
            notificarObservadores(Eventos.CAMBIA_CARTA_TOPE);
            notificarObservadores(Eventos.CAMBIA_MANO);
        }
    }

    //MUEVE LAS CARTAS DE LA MANO PARA IR ACOMODANDO EL JUEGO
    @Override
    public void moverCartas(int carta1,int carta2) throws RemoteException {
        jugadorActual.getMano().moverCartas(carta1,carta2);
        notificarObservadores(Eventos.CAMBIA_MANO);
    }

    //ALGUIEN CORTO PERO NO HAY GANADOR TODAVIA SE VACIAN LAS MANOS DE LOS JUGADORES Y LA PILA DESCARTE
    // Y SE VUELVE A REPARTIR
    @Override
    public void otraRonda() throws RemoteException {
        pilaDescarte.vaciarPila();
        for (Jugador jugador : jugadores) {
            jugador.getMano().vaciarMano();
        }
        mazo=new Mazo();
        if(cantidadDeRondas<jugadores.size()) {
            jugadorActual = jugadores.get(cantidadDeRondas);
        }
        else{
            jugadorActual=jugadores.get(0);
        }
        repartir();
        notificarObservadores(Eventos.JUEGO_INICIADO);
    }

    //OBTIENE EL VALOR DE CortaCon SI ES CHINCHON SETEA EL GANADOR Y EL ESTADO DEL JUGADOR QUE GANO,
    // CALCULA LOS PUNTOS Y TERMINA EL JUEGO SI CORTA CON MENOS_DIEZ O SOBRA_CARTA, SI CORTA CON
    // MENOS_DIEZ LE RESTA 10 A LOS PUNTOS DEL JUGADOR QUE CORTO,LUEGO EN AMBOS CASOS INCREMENTA
    // LA CANTIDAD DE RONDAS, CALCULA LOS PUNTOS DE LOS JUGADORES Y SE FIJA QUIENES PUEDEN SEGUIR JUGANDO,
    // POR ULTIMO EMPIEZA UNA NUEVA RONDA
    @Override
    public boolean cortar(int opcion) throws RemoteException {
        boolean corta=false;
        Carta cartaExtra=jugadorActual.getMano().getCartaExtraTurno();
        tirarCarta(opcion);
        if(jugadorActual.getMano().puedeCortar()) {
            if (jugadorActual.getMano().getCortaCon() == CortaCon.CHINCHON) {
                setGanador(jugadorActual);
                jugadorActual.setEstado(EstadoJugador.GANADOR);
                for (Jugador j : jugadores) {
                    j.sumarPuntos(j.getMano().calcularPuntos());
                }
                notificarObservadores(Eventos.FIN_DEL_JUEGO);
            } else {
                if (jugadorActual.getMano().getCortaCon() == CortaCon.MENOS_DIEZ) {
                    jugadorActual.restarDiez();
                }
                cantidadDeRondas++;
                for (Jugador j : jugadores) {
                    j.sumarPuntos(j.getMano().calcularPuntos());
                }
                for (Jugador j : jugadores) {
                    if (!j.sigueJugando()) {
                        j.setEstado(EstadoJugador.PERDEDOR);
                    }
                }
                notificarObservadores(Eventos.NUEVA_RONDA);
            }
            corta=true;
        }
        else{
            if(opcion==8){
                jugadorActual.getMano().setCartaExtraTurno(getPilaDescarte().darCarta());
            }
            else{
                jugadorActual.getMano().getCartas().remove(cartaExtra);
                jugadorActual.getMano().setCartaExtraTurno(cartaExtra);
                jugadorActual.getMano().recibirCarta(getPilaDescarte().darCarta());
            }
            notificarObservadores(Eventos.CAMBIA_CARTA_TOPE);
            notificarObservadores(Eventos.CAMBIA_MANO);
        }
        return corta;
    }

}