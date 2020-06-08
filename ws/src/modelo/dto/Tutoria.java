package modelo.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class Tutoria implements Serializable{
	private String idTutoria;
	private int hora;
	private Profesor profesor;
	private String lugar;
	private LinkedList<String> usuarios;
	private String fecha;
	
	
	
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Recuerda que tienes una tutoria en la hora:" + hora + ",junto con el profesor:" + profesor + ",en el lugar:" + lugar + ", en el dia:" + fecha;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tutoria other = (Tutoria) obj;
		if (idTutoria != other.idTutoria)
			return false;
		return true;
	}
	
	
	
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
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
	 * @return the idTutoria
	 */
	public String getIdTutoria() {
		return idTutoria;
	}
	/**
	 * @param idTutoria the idTutoria to set
	 */
	public void setIdTutoria(String idTutoria) {
		this.idTutoria = idTutoria;
	}
	/**
	 * @return the usuarios
	 */
	public LinkedList<String> getUsuarios() {
		return usuarios;
	}
	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(LinkedList<String> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
