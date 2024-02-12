package com.connectfour;

import java.util.Arrays;
import java.util.HashMap;

public class Puntos {
    private HashMap<Integer,String> Mapa;

    public Puntos(){};

    public Puntos( HashMap<Integer,String> Mapa){
        this.Mapa=Mapa;
    }

    public HashMap<Integer, String> getMapa() {
        return Mapa;
    }

    public void setMapa(HashMap<Integer, String> mapa) {
        Mapa = mapa;
    }

    /**
     * 
     * @param casilla que se evalua
     * @param casillaSiguiente casilla a la que se mueve
     * @param Mapa tablero al finalizar la partida
     * @return es true si las dos son del mismo color
     */
    public boolean casillaSeguida(int casilla, int casillaSiguiente){

        return this.Mapa.get(casilla).equals(this.Mapa.get(casillaSiguiente));
    }

    /**
     * depende de la posicion de la casilla calcula hacia donde podria conectar con 4
     * @param casilla inicial
     * @return 1 la siguiente casilla es a la derecha
     * 6: la siguiente casilla va arriba
     * 7: diagonal derecha
     * 5: diagonal izquierda
     */
    public int[] buscarCamino(int casilla) {
        int fila = casilla / 6;
        int columna = casilla % 6;
        int[] camino = new int[3];
        
        if (columna < 3) {
            camino[0] = 1;
            camino[1] = (fila < 3) ? 6 : -1;
            camino[2] = (fila < 3) ? 7 : -1;
        } else {
            camino[0] = -1;
            camino[1] = (fila < 3) ? 6 : -1;
            camino[2] = (fila < 3) ? 5 : -1;
        }
        
        return camino;
    }

    /**
     * cuenta los puntos en todo el mapa
     * @param Mapa
     * @return puntos [][]
     * puntos [0] son los puntos del jugador 1
     * puntos[1] son los ppuntos del jugador 2
     * puntos [][0] puntos en horizontal
     * puntos [][1] puntos en vertical
     * puntos [][2] puntos en diagonal
     */
    public int [][] contadorPuntos(){
        System.out.println("estamos contando los puntos");
        int[][] puntos = new int[2][3];

        for (int casilla =0; casilla<36; casilla++){
            System.out.println("---------------------------casilla :"+casilla+"-------------------------------");
            int caminos[] = buscarCamino(casilla);
            for( int camino : caminos){
                System.out.println("---------------------------Camino: "+camino+"-------------------------------");
                int seguidas=1;
                while(seguidas<4 && contadorPuntos(camino, casilla+camino*(seguidas-1))){
                    seguidas++;
                    System.out.println("este camino lleva "+ seguidas+" fichas seguidas");
                };  
                if (seguidas==4){ 
                    System.out.println("En la casilla "+ casilla+" HAY PUNTOOO" );
                    int jugador= this.Mapa.get(casilla).equals("red") ? 1 : 0;
                    int direccion= camino==1?0:camino==6?1:2;
                    puntos[jugador][direccion]++;
                    // rojos += this.Mapa.get(casilla).equals("red") ? 1 : 0;
                    // azules += this.Mapa.get(casilla).equals("blue") ? 1 : 0;
                }    
 
                
                System.out.println();                               
                 
            }
            System.out.println("---------------------------Nueva casilla-------------------------------");
            System.out.println();        
            

        }
        
        System.out.println("Jugador 1 tiene "+ Arrays.stream(puntos[0]).sum()+" puntos");
        System.out.println("Jugador 2 tiene "+ Arrays.stream(puntos[1]).sum()+" puntos");
        return puntos;
    }

    /**
     * cuenta las casillas seguidas dependiendo de la direccion que se compara
     * @param Mapa
     * @param camino la direccion de la siguiente casilla
     * @param casilla 
     * @return
     */
    public boolean contadorPuntos(int camino,int casilla){
        System.out.println("casilla: "+casilla+ " la comparamos con la casilla: " +(casilla+camino));
        if (camino<0) return false;
        return casillaSeguida(casilla, casilla+camino);


    }
    
}
