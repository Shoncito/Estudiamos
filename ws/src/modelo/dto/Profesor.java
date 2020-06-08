package modelo.dto;

import java.util.LinkedList;

public class Profesor {
	private String id;
	private String nombre;
	private LinkedList<String> materias;
	private LinkedList<String> horarios;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the materias
	 */
	public LinkedList<String> getMaterias() {
		return materias;
	}

	/**
	 * @param materias the materias to set
	 */
	public void setMaterias(LinkedList<String> materias) {
		this.materias = materias;
	}

	/**
	 * @return the horarios
	 */
	public LinkedList<String> getHorarios() {
		return horarios;
	}

	/**
	 * @param horarios the horarios to set
	 */
	public void setHorarios(LinkedList<String> horarios) {
		this.horarios = horarios;
	}


	public String toJSON() {
		return "{\"id\":\"" + id + "\", \"nombre\":\"" + nombre + "\", \" materias\":\"" + materias + "\", \" horarios\":\"" + horarios + "\"}";
	}

	
}
