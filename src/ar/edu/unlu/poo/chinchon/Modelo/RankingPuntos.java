package ar.edu.unlu.poo.chinchon.Modelo;

import java.util.ArrayList;

public class RankingPuntos implements RankingMostrable{
    private static RankingPuntos instancia;
    public ArrayList<Jugador> ranking;
    private static final long serialVersionUID = 1L;

    private RankingPuntos(){
        this.ranking= new ArrayList<Jugador>();
    }

    public static RankingPuntos getInstance() {
        if (instancia == null) {
            instancia = new RankingPuntos();
        }
        return instancia;
    }

    public static void setInstance(RankingPuntos obj) {
        instancia=obj;
    }

    @Override
    public ArrayList<JugadorMostrable> getRanking() {
        return new ArrayList<JugadorMostrable>(ranking);
    }

    //ORDENA EL RANKING DE MENOR A MAYOR, YA QUE UN MENOR PUNTAJE ES MEJOR
    public void ordenarRanking() {
        ranking.sort((a, b) -> Integer.compare(a.getPuntos(), b.getPuntos()));
    }

    //AGREGA UNA ENTRADA AL TOP 5 DEL RANKING Y MANTIENE SIEMPRE ORDENADAS LAS 5 PUNTUACIONES MAS ALTAS,
    //SI VUELVE A GANAR UN JUGADOR QUE ESTA EN EL RANKING MANTIENE EN EL RANKING LA PUNTUACION MAS ALTA
    public void agregarEntrada(Jugador j){
        boolean repetido=false;
        int mayorPuntaje=0;
        Jugador jugadorMayorPuntaje=null;
        Jugador jugadorRepetido=null;

        for(Jugador jug: ranking){
            if(j.getNombre().equals(jug.getNombre())){
                repetido=true;
                jugadorRepetido=jug;
            }
            if(jugadorMayorPuntaje==null|| jug.getPuntos() > mayorPuntaje){
                mayorPuntaje=jug.getPuntos();
                jugadorMayorPuntaje=jug;
            }
        }
        if(ranking.size()<5) {
            if (!repetido) {
                ranking.add(j);
                ordenarRanking();
            } else {
                if (jugadorRepetido.getPuntos() > j.getPuntos()) {
                    ranking.remove(jugadorRepetido);
                    ranking.add(j);
                    ordenarRanking();
                }
            }
        }
        else{
            if(j.getPuntos()<jugadorMayorPuntaje.getPuntos() && !repetido){
                ranking.remove(jugadorMayorPuntaje);
                ranking.add(j);
                ordenarRanking();
            }
            else if(repetido && j.getPuntos()<jugadorRepetido.getPuntos()){
                ranking.remove(jugadorRepetido);
                ranking.add(j);
                ordenarRanking();
            }
        }

    }
}
