package modelo;

public class Tutoria {
	private int idTutoria;
	private int hora;
	private Profesor profesor;
	private String lugar;
	private Usuario usuario;
	
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	/**
	 * @return the idTutoria
	 */
	public int getIdTutoria() {
		return idTutoria;
	}
	/**
	 * @param idTutoria the idTutoria to set
	 */
	public void setIdTutoria(int idTutoria) {
		this.idTutoria = idTutoria;
	}
	/**
	 * @return the hora
	 */
	public int getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(int hora) {
		this.hora = hora;
	}
	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
