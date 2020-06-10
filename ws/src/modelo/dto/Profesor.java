package modelo.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class Profesor implements Serializable{
	private String idProfesor;
	private String nombre;
	private LinkedList<String> materias;
	private LinkedList<String> horarios;

	/**
	 * @return the id
	 */
	public String getId() {
		return idProfesor;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String idProfesor) {
		this.idProfesor = idProfesor;
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
		return "{\"idProfesor\":\"" + idProfesor + "\", \"nombre\":\"" + nombre + "\", \" materias\":\"" + materias + "\", \" horarios\":\"" + horarios + "\"}";
	}

	
}
