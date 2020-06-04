package modelo;

import java.net.InetSocketAddress;
import java.util.LinkedList;

import org.java_websocket.WebSocket;

public class Usuario {
	
	private WebSocket webSocket;
	private String token;
	private LinkedList<Tutoria>tutorias ;
	private LinkedList<GrupoEstudio>gruposEstudio;
	private InetSocketAddress direccion;
	
	public WebSocket getWebSocket() {
		return webSocket;
	}
	public void setWebSocket(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LinkedList<Tutoria> getTutorias() {
		return tutorias;
	}
	public void setTutorias(LinkedList<Tutoria> tutorias) {
		this.tutorias = tutorias;
	}
	public LinkedList<GrupoEstudio> getGruposEstudio() {
		return gruposEstudio;
	}
	public void setGruposEstudio(LinkedList<GrupoEstudio> gruposEstudio) {
		this.gruposEstudio = gruposEstudio;
	}
	/**
	 * @return the direccion
	 */
	public InetSocketAddress getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(InetSocketAddress direccion) {
		this.direccion = direccion;
	}

	
}
