package websocketsapp;


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.print.attribute.Size2DSyntax;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.Messaging.SyncScopeHelper;

import com.google.gson.Gson;


public class Server extends WebSocketServer{
//	private static Map<Integer,Set<WebSocket>> Rooms = new HashMap<>();
	public static List<WebSocket> clients=new ArrayList<WebSocket>();

	public Server() {
        super(new InetSocketAddress(8080));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash " + conn.getRemoteSocketAddress().hashCode());
        clients.add(conn);
       // System.out.println(clients.size());
    }

    public void onMessage(WebSocket conn, String message) {
  
    }
   
    	
    

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + reason);
    }

    @Override
    public void onError(WebSocket conn, Exception exc) {
        System.out.println("Error happened: " + exc);
    }

    private int generateRoomNumber() {
        return new Random(System.currentTimeMillis()).nextInt();
    }

    public void sendToAll(WebSocket conn, String message) {
 
    	for(int i =0;i<clients.size();i++) {
    		WebSocket c = (WebSocket)clients.get(i);
            if (c != conn) c.send(message);
    	}
    }
    

    public static void main(String[] args) {
    
        Server server = new Server();
        server.start();
       
    }


	

}
