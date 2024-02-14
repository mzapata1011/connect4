package com.connectfour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONArray;
import org.json.JSONObject;

@ServerEndpoint("/refresh")
public class RefreshSocket {
    
    private static final Map<String, Session> sessions = new ConcurrentHashMap<String,Session>();
    private static JSONObject juegos =new JSONObject();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened. ID:"+session.getId());
        sessions.put(session.getId(), session); // Add session to the map
        // session.getUserProperties().put("gameId",gameId);
        // juegos.put(gameId, session.getId());
        // System.out.println("Juegos: "+ juegos.toString());


    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket connection number "+session.getId()+"closed");
        sessions.remove(session.getId());
        
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Handle WebSocket error event
        System.err.println("WebSocket error: " + throwable.getMessage());
        throwable.printStackTrace();
        // Close the WebSocket connection gracefully
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "WebSocket error"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @OnMessage
    public void onMessage(String message, Session session) {
        // recibo el mensaje de la partida que esta jugado el usuario y la junto al json de juegos
        try {
            System.out.println("Message received: " + message);
            session.getBasicRemote().sendText("Received your message: " + message);
            juegos.accumulate(session.getId(),message);
            System.out.println("Los juegos con socket abiertos son: "+juegos.toString());
            
        } catch (IOException e) {
            // TODO: handle exception
            onClose(session);

        }


    }

    public static void refreshActiveGame(String game_id)throws IOException{
        System.out.println("Rfrescamos las partidas "+game_id);
        
        for (String jsonKey : juegos.keySet() ){
            if(juegos.getString(jsonKey).equals(game_id)){
                System.out.println("deberiamos refrescar bro keyset:"+jsonKey);
            }
       
        }
    }

    // public void removeFromJson(Session session){

    // }


}
