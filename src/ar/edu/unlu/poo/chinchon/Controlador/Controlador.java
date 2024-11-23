package ar.edu.unlu.poo.chinchon.Controlador;

import  ar.edu.unlu.poo.chinchon.Modelo.*;
import ar.edu.unlu.poo.chinchon.Vista.*;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    private IModelo modelo;
    private IVista vista;

    public void setVista(IVista vista){
        this.vista=vista;
    }

    public ArrayList<CartaMostrable> obtenerCartasMano(){
        ArrayList<CartaMostrable> mano=new ArrayList<>();
        try {
            mano=new ArrayList<CartaMostrable>(modelo.getJugadorActual().getMano().getCartas());
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return mano;
    }


    public CartaMostrable obtenerCartaTope(){
        CartaMostrable cartaTope=null;
        try {
            cartaTope=modelo.getPilaDescarte().getTope();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cartaTope;
    }

    public CartaMostrable obtenerCartaExtra(){
        CartaMostrable cartaExtra=null;
        try {
            cartaExtra=modelo.getJugadorActual().getMano().getCartaExtraTurno();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return cartaExtra;
    }

    public  ArrayList<CartaMostrable> obtenerMazo(){
        ArrayList<CartaMostrable> mazo=new ArrayList<>();
        try {
            mazo=new ArrayList<CartaMostrable>(modelo.getMazo().getPilaDeCartas());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return mazo;
    }

    public int faltanJugadores(){
        int valor=-1;
        try {
            int cantidadJ = modelo.getJugadores().size();
            if (cantidadJ == 0) {
                valor=2;
            } else if (cantidadJ == 1) {
                valor=1;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return valor;
    }

    public String jugadorActual(){
        String nombre=null;
        try {
            Jugador jugadorAct = modelo.getJugadorActual();
            nombre=jugadorAct.getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    public void agregarJugador(String nombre){
        try {
            Jugador j = new Jugador(nombre);
            modelo.agregarJugador(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mostrarJugadores(){
        try {
            ArrayList<Jugador> jugadores = modelo.getJugadores();
            if (jugadores.size() == 0) {
                vista.mostrarMensaje("No hay jugadores");
            } else {
                for (Jugador jugador : jugadores) {
                    vista.mostrarMensaje(jugador.getNombre());
                }
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public String getGanador(){
        String nombreGanador=null;
        try {
            nombreGanador=modelo.getGanador().getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return nombreGanador;
    }

    public boolean iniciarJuego(){
        boolean inicia=false;
        try {
            inicia=modelo.iniciar();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return inicia;
    }

    public void cambiarTurno(){
        try {
            modelo.cambiarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
            
        }
    }

    public void agarrarCarta(int opcion){
        try {
            modelo.agarrarCarta(opcion);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void tirarCarta(int posicionCarta){
        try {
            modelo.tirarCarta(posicionCarta);
            cambiarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void moverCartas(int pos1,int pos2){
        try {
            vista.mostrarMensaje("CARTA TOPE");
            vista.mostrarCarta(obtenerCartaTope());
            modelo.moverCartas(pos1, pos2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reiniciaRonda(){
        try {
            modelo.otraRonda();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> obtenerPerdedores(){
        ArrayList<String> perdedores=new ArrayList<>();
        for(Jugador j: obtenerJugadores()){
            if(j.getEstado().equals(EstadoJugador.PERDEDOR)){
                perdedores.add(j.getNombre());
            }
        }
        return perdedores;
    }
    public void cortar(int opcion){
        try {
            if(!modelo.cortar(opcion)) {
                vista.mostrarMensaje("TODAVIA NO PODES CORTAR");
                vista.opcionesCartasTirar();
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public Jugador obtenerJugadorActual(){
        Jugador jugadorActual=null;
        try{
            jugadorActual=modelo.getJugadorActual();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return jugadorActual;
    }
    
    public ArrayList<Jugador> obtenerJugadores(){
        ArrayList<Jugador> jugadores=new ArrayList<>();
        try{
            jugadores=new ArrayList<>(modelo.getJugadores());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return jugadores;
    }
    public int obtenerCantidadDeRondas(){
        int cantRondas=-1;
        try{
            cantRondas=modelo.getCantidadDeRondas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cantRondas;
    }
    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.modelo = (IModelo) modeloRemoto;
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object evento) throws RemoteException {
        ArrayList<CartaMostrable> mano;
        switch(evento) {
            case Eventos.FALTAN_JUGADORES:
                if (faltanJugadores() != -1) {
                    vista.mostrarMensaje("Faltan " + faltanJugadores() + " jugadores para iniciar la partida");
                }
                break;
            case Eventos.JUEGO_INICIADO:
                vista.limpiarPantalla();
                vista.mostrarMensaje("RONDA Nº "+(obtenerCantidadDeRondas()+1));
                vista.mostrarMensaje("PUNTOS DE LOS JUGADORES");
                for (Jugador jugador : obtenerJugadores()) {
                    vista.mostrarMensaje("PUNTOS DEL JUGADOR " + jugador.getNombre() + ": " + jugador.getPuntos());
                }
                Timer timer = new Timer(8000, new ActionListener()
                { @Override public void actionPerformed(ActionEvent e) {
                    actualizarVistaTurno(); } });
                timer.setRepeats(false);
                timer.start();
                break;
            case Eventos.CAMBIA_TURNO:
                actualizarVistaTurno();
                break;
            case Eventos.CAMBIA_CARTA_TOPE:
                if(vista.isTurno()) {
                    vista.mostrarMensaje("CARTA TOPE PILA DESCARTE");
                    if (obtenerCartaTope() != null) {
                        vista.mostrarCarta(obtenerCartaTope());
                    }
                }
                break;

            case Eventos.CAMBIA_MAZO:
                vista.mostrarMensaje("MAZO");
                if (!obtenerMazo().isEmpty()) {
                    vista.mostrarMazo(obtenerMazo());
                }
                break;

            case Eventos.CAMBIA_MANO:
                if(vista.isTurno()) {
                    if (jugadorActual() != null && obtenerJugadorActual() != null && !obtenerCartasMano().isEmpty()) {
                        vista.mostrarMensaje("MANO JUGADOR: " + jugadorActual());
                        vista.mostrarMano(obtenerCartasMano());
                        if(obtenerCartaExtra()!=null) {
                            vista.mostrarMensaje("CARTA LEVANTADA");
                            vista.mostrarCarta(obtenerCartaExtra());
                        }
                    }
                }
                break;

            case Eventos.FIN_DEL_JUEGO:
                if(getGanador()!=null) {
                    vista.mostrarMensaje("GANADOR: JUGADOR " + getGanador());
                    vista.mostrarMensaje("JUEGO TERMINADO");
                }
                break;

            case Eventos.NUEVA_RONDA:
                vista.verificarPerdedores();
                reiniciaRonda();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + evento);
        }
    }

    private void actualizarVistaTurno() {
        if(jugadorActual()!=null){
           vista.verificarTurno();
        }
    }
}
