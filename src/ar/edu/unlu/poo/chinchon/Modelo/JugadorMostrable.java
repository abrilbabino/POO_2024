package ar.edu.unlu.poo.chinchon.Modelo;

import java.io.Serializable;

public interface JugadorMostrable extends Serializable {
    public int getPuntos();
    public String getNombre();
    public EstadoJugador getEstado();
}
