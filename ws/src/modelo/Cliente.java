package modelo;

import java.util.LinkedList;

import org.java_websocket.WebSocket;

public class Cliente {
	private WebSocket webSocket;
	private String token;
	private LinkedList<Notificacion> notificaciones;
	
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
	public LinkedList<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	public void setNotificaciones(LinkedList<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
}
