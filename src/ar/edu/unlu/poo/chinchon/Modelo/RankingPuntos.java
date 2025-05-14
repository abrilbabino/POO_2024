package ar.edu.unlu.poo.chinchon.Modelo;

import java.util.ArrayList;

public class RankingPuntos implements RankingMostrable{
    private static RankingPuntos instancia;
    public ArrayList<Jugador> ranking;

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

    public void agregarEntrada(Jugador j){
        boolean repetido=false;
        int menorPuntaje=100;
        Jugador jugadorMenorPuntaje=null;
        Jugador jugadorRepetido=null;

        for(Jugador jug: ranking){
            if(j.getNombre().equals(jug.getNombre())){
                repetido=true;
                jugadorRepetido=jug;
            }
            if(jug.getPuntos()<menorPuntaje){
                menorPuntaje=jug.getPuntos();
                jugadorMenorPuntaje=jug;
            }
        }
        if(ranking.size()<5) {
            if (!repetido) {
                ranking.add(j);
                ranking.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));
            } else {
                if (jugadorRepetido.getPuntos() < j.getPuntos()) {
                    ranking.remove(jugadorRepetido);
                    ranking.add(j);
                    ranking.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));
                }
            }
        }
        else{
            if(j.getPuntos()>jugadorMenorPuntaje.getPuntos() && !repetido){
                ranking.remove(jugadorMenorPuntaje);
                ranking.add(j);
                ranking.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));
            }
            else if(repetido && j.getPuntos()>jugadorRepetido.getPuntos()){
                ranking.remove(jugadorRepetido);
                ranking.add(j);
                ranking.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));
            }
        }
    }

    public ArrayList<Jugador> getRanking() {
        return ranking;
    }
}
