package websocketsapp;


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.print.attribute.Size2DSyntax;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.Messaging.SyncScopeHelper;

import funciones.Comparador;
import modelo.GrupoEstudio;
import modelo.Tutoria;
import modelo.Usuario;
import modelo.Profesor;

public class Server extends WebSocketServer{
//	private static Map<Integer,Set<WebSocket>> Rooms = new HashMap<>();
	public LinkedList<Usuario> usuarios;

	public Server() {
        super(new InetSocketAddress(8080));
        this.usuarios=new LinkedList<Usuario>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash " + conn.getRemoteSocketAddress().hashCode());
        Usuario usuario = Comparador.comparar(usuarios, conn.getRemoteSocketAddress());
        if(usuario!=null) {
        	usuario.setWebSocket(conn);
        }else {
        	usuario = new Usuario();
        	usuario.setWebSocket(conn);
        	usuario.setDireccion(conn.getRemoteSocketAddress());
        }
        
       // System.out.println(clients.size());
    }
    
    @Override
    public void onMessage(WebSocket conn, String message) {
    JSONObject js =new JSONObject(message);
   if(js.get("tipo").equals("recibir")){
   LinkedList<Tutoria> tutorias=new LinkedList();
   LinkedList<GrupoEstudio> grupoestudio=new LinkedList();
   	JSONArray tuto=js.getJSONArray("tutorias");
   	JSONArray gruestu=js.getJSONArray("gruposEstudio");
   	Usuario usuario=Comparador.comparar(usuarios, conn.getRemoteSocketAddress());
   	for(int i=0;i<tuto.length();i++){
   		Tutoria tuto2=new Tutoria();
   		Profesor profe=new Profesor();
   		profe.setNombre(tuto.getJSONObject(i).getString("nombreProfesor"));
   	    tuto2.setIdTutoria(tuto.getJSONObject(i).getInt("id"));
        tuto2.setHora(tuto.getJSONObject(i).getInt("hora"));
        tuto2.setLugar(tuto.getJSONObject(i).getString("lugar"));
        tuto2.setProfesor(profe);
        tuto2.setUsuario(usuario); 
   	}
    usuario.setTutorias(tutorias);
   	for(int i=0;i<gruestu.length();i++){
   		GrupoEstudio grup1=new GrupoEstudio();
   		grup1.setFecha(gruestu.getJSONObject(i).getString("fecha"));
   		grup1.setHora(gruestu.getJSONObject(i).getInt("hora"));
   		grup1.setIdGrupo(gruestu.getJSONObject(i).getInt("idGrupo"));
   		grup1.setNombreGrupo(gruestu.getJSONObject(i).getString("nombreGrupo"));
   		grup1.setTema(gruestu.getJSONObject(i).getString("tema"));
        
   		
   		
   		
   	}
   	
   	
   	
   
   	
    
   	
   }
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
 
    	for(int i =0;i<usuarios.size();i++) {
    		WebSocket c = (WebSocket)usuarios.get(i).getWebSocket();
            if (c != conn) c.send(message);
    	}
    }
    

    


	

}
