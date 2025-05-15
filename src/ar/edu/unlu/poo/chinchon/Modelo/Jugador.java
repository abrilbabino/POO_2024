package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements JugadorMostrable {
    private String nombre;
    private Mano mano;
    private int puntos;
    private boolean turno;
    private EstadoJugador estado;
    private static final long serialVersionUID = 1L;

    public Jugador(String nombre){
        this.nombre=nombre;
        this.puntos=0;
        this.turno=false;
        this.mano=new Mano();
    }

    public String getNombre() {
        return this.nombre;
    }

    public Mano getMano(){
        return this.mano;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno){
        this.turno=turno;
    }

    public EstadoJugador getEstado(){
        return this.estado;
    }

    public void setEstado(EstadoJugador estado){
        this.estado=estado;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void sumarPuntos(int puntosaSumar) {
        this.puntos += puntosaSumar;
    }

    public void restarDiez(){
        this.puntos-=10;
    }

    //LEVANTA UNA CARTA DEL MAZO Y LA AGREGA A LA MANO DEL JUGADOR COMO LA ULTIMA CARTA LEVANTADA
    public void sacarCartaDelMazo(Mazo m){
        this.getMano().setCartaExtraTurno(m.darCarta());
    }

    //LEVANTA LA CARTA TOPE DE LA PILA DESCARTA Y LA AGREGA A LA MANO DEL JUGADOR COMO LA ULTIMA
    // CARTA LEVANTADA
    public void sacarCartaDeLaPiLaDescarte(PilaDescarte p){
        this.getMano().setCartaExtraTurno(p.darCarta());
    }

    //PERMITE EVALUAR SI UN JUGADOR PUEDE SEGUIR JUGANDO O SI EXCEDE LOS 100 PUNTOS Y EN ESE CASO PIERDE
    public boolean sigueJugando(){
        if(this.puntos>100){
            return false;
        }
        else{
            return true;
        }
    }
}
