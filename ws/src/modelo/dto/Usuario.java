package modelo.dto;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.LinkedList;

import org.java_websocket.WebSocket;

public class Usuario implements Serializable{
	
	private WebSocket webSocket;
	private String token;
	private InetSocketAddress direccion;
	private String correo;
	private String usuario;
	private String contrase�a;
	private String motivoDesconexion;
	
	
	
	
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
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the contrase�a
	 */
	public String getContrase�a() {
		return contrase�a;
	}
	/**
	 * @param contrase�a the contrase�a to set
	 */
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	public String toJSON() {
		return "{\"correo\":\"" + correo + "\", \"usuario\":\"" + usuario + "\", \"contrase�a\": \"No tendr�s la contrase�a :v \",\"token\":\""+token+"\"}";
	}
	/**
	 * @return the motivoDesconexion
	 */
	public String getMotivoDesconexion() {
		return motivoDesconexion;
	}
	/**
	 * @param motivoDesconexion the motivoDesconexion to set
	 */
	public void setMotivoDesconexion(String motivoDesconexion) {
		this.motivoDesconexion = motivoDesconexion;
	}

	
}
