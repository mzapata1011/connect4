package com.connectfour;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/refresh")
public class RefreshSocket {
    
    private static final Map<String, Session> sessions = new ConcurrentHashMap<String,Session>();
    private static HashMap<String,String> juegos =new HashMap<String,String>();

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
        juegos.remove(session.getId());        
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
        
            if( message.contains("Partida:")){
                session.getBasicRemote().sendText("Received your message: " + message);
                message=message.substring("Partida:".length());
                juegos.put(session.getId(),message); // mando refresh a los sockets con esta partida abierta
                System.out.println("Los juegos con socket abiertos son: "+juegos.toString());
                messageRival(juegos.get(session.getId()), session.getId(), "llego rival");

            }
            else {
                System.out.println(message);
                messageRival(juegos.get(session.getId()), session.getId(), message);


            }
            
        } catch (IOException e) {
            onClose(session);

        }


    }

    public static void refreshActiveGame(String game_id)throws IOException{
        
        for (Map.Entry<String, String> entry : juegos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the value matches the target value
            if (game_id.equals(value)) {
                System.out.println("Refrescamos el socket "+key+ " conectado a la partida: "+value);
                sendMessageToClient(sessions.get(key), "refresh" );
            }
        }
    }

    /**
     * 
     * @param game_id : id del juego que estas participando
     * @param your_id : id de tu session
     * @param message : mensaje que le envias al rival
     * @throws IOException
     * Le manda un mensaje al socket del rival
     */
    public static void messageRival(String game_id, String your_id,String message) throws IOException{

        for (Map.Entry<String, String> entry : juegos.entrySet()) {
            
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the value matches the target value
            if (game_id.equals(value) && !key.equals(your_id)) {
                System.out.println("le enviamos mensaje al el socket "+key+ " conectado a la partida: "+value);
                sendMessageToClient(sessions.get(key), message );
            }
        }
    }

    private static void sendMessageToClient(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void removeFromJson(Session session){

    // }


}
