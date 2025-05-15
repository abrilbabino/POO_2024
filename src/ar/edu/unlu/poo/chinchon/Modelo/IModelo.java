package ar.edu.unlu.poo.chinchon.Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelo extends IObservableRemoto {
    public ArrayList<Jugador> getJugadores() throws RemoteException;

    public Mazo getMazo() throws RemoteException;

    public PilaDescarte getPilaDescarte() throws RemoteException;

    public int getCantidadDeRondas()throws RemoteException;

    public Jugador getGanador() throws RemoteException;

    public Jugador getJugadorActual() throws RemoteException;

    public void setGanador(Jugador j) throws RemoteException;

    public RankingPuntos getRanking () throws RemoteException;

    public void agregarJugador(Jugador j) throws RemoteException;

    public void cambiarTurno() throws RemoteException;

    public void repartir()throws RemoteException;

    public boolean iniciar() throws RemoteException;

    public void cambiarMazoPorPilaDescarte() throws RemoteException;

    public void agarrarCartaDelMazo() throws RemoteException;

    public void agarrarCartaDeLaPiLaDescarte() throws RemoteException;

    public void tirarCartaExtra() throws RemoteException;

    public void tirarCartaMano(int posCarta )throws RemoteException;

    public void moverCartas(int posCartaUno,int posCartaDos) throws RemoteException;

    public void otraRonda() throws RemoteException;

    public boolean cortar(int opcion) throws RemoteException;

    public Jugador obtenerJugador(String nombre) throws RemoteException;

    public void sacarPerdedoresDelJuego() throws RemoteException;

}
