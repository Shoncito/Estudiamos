package funciones;

import java.util.LinkedList;

import org.java_websocket.WebSocket;

import modelo.dto.Usuario;

public class Buscador {
	public static Usuario buscarUsuario(WebSocket conn, LinkedList<Usuario> usuarios) {
		for(Usuario usuario: usuarios) {
			if(conn.equals(usuario.getWebSocket())) {
				return usuario;
			}
		}		
		return null;
	}
}
