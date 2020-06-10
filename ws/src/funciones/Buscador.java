package funciones;

import java.util.LinkedList;

import org.java_websocket.WebSocket;

import modelo.dto.GrupoEstudio;
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

	public static boolean estaUsuarioEnGrupo(Usuario usuario, GrupoEstudio grupoEstudio) {
		LinkedList<String> nombreUsuarios = grupoEstudio.getUsuarios();
		for(String nombreUsuario: nombreUsuarios) {
			if(nombreUsuario.equals(usuario.getUsuario())) {
				return true;
			}
		}
		return false;
	}
}
