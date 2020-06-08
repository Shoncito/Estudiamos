package modelo.dto;

import java.util.LinkedList;

public class GrupoEstudio {
	private String idGrupo;
	private LinkedList<String> usuarios;
	private String nombreGrupo;
	private String tema;
	private String fecha;
	private int hora;
	private String lugar;
	private String idMateria;

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
		GrupoEstudio other = (GrupoEstudio) obj;
		if (idGrupo != other.idGrupo)
			return false;
		return true;
	}
	/**
	 * @return the idGrupo
	 */
	public String getIdGrupo() {
		return idGrupo;
	}
	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
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
	/**
	 * @return the nombreGrupo
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	/**
	 * @param nombreGrupo the nombreGrupo to set
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	/**
	 * @return the tema
	 */
	public String getTema() {
		return tema;
	}
	/**
	 * @param tema the tema to set
	 */
	public void setTema(String tema) {
		this.tema = tema;
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
	 * @return the idMateria
	 */
	public String getIdMateria() {
		return idMateria;
	}
	/**
	 * @param idMateria the idMateria to set
	 */
	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	public String toJSON() {
		return "{\"idGrupo\":\"" + idGrupo + "\", \" usuarios\":\"" + usuarios + "\", \"nombreGrupo\":\"" + nombreGrupo
				+ "\",\" tema\":\"" + tema + "\", \"fecha\":\"" + fecha + "\", \"hora\":\"" + hora + "\", \"lugar\":\"" + lugar + "\", \"idMateria\":\""
				+ idMateria + "\"}";
	}
	
	
	
}
