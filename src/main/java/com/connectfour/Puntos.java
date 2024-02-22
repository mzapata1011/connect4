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
     * @return puntos [][]
     * puntos [0]: player 1 points      
     *  puntos[1]: player 2 points 
     * puntos [][0]: horizontal points 
     * puntos [][1] vertical points
     * puntos [][2] diagonal points
     */
    public int [][] contadorPuntos(){
        System.out.println("estamos contando los puntos");
        int[][] puntos = new int[2][3];

        for (int casilla =0; casilla<36; casilla++){
            //System.out.println("---------------------------casilla :"+casilla+"-------------------------------");
            int caminos[] = buscarCamino(casilla);
            for( int camino : caminos){
                //System.out.println("---------------------------Camino: "+camino+"-------------------------------");
                int seguidas=1;
                while(seguidas<4 &&(contadorPuntos(camino, casilla+camino*(seguidas-1)))){
                
                    seguidas++;

                };  
                if (seguidas==4){ 
                    //System.out.println("En la casilla "+ casilla+" HAY PUNTOOO" );
                    int jugador= this.Mapa.get(casilla).equals("red") ? 1 : 0;
                    int direccion= camino==1?0:camino==6?1:2;
                    puntos[jugador][direccion]++;
                }    
 
                
                //System.out.println();                               
                 
            }
            //System.out.println("---------------------------Nueva casilla-------------------------------");
            //System.out.println();        
            

        }
        
        System.out.println("Jugador 1 tiene "+ Arrays.stream(puntos[1]).sum()+" puntos");
        System.out.println("Jugador 2 tiene "+ Arrays.stream(puntos[0]).sum()+" puntos");
        return puntos;
    }

    /**
     * cuenta las casillas seguidas dependiendo de la direccion que se compara
     * @param camino la direccion de la siguiente casilla
     * @param casilla inicial
     * @return true si son iguales false si son diferentes
     */
    public boolean contadorPuntos(int camino,int casilla){
        System.out.println("casilla: "+casilla+ " la comparamos con la casilla: " +(casilla+camino));
        if (camino<0) return false;
        return casillaSeguida(casilla, casilla+camino);


    }
    
}
