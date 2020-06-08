/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

import java.io.Serializable;

/**
 *
 * @author Val
 */
public class Materia implements Serializable{
    private String idMateria;
    private String nombreMateria;
    private String idEscuela;

    public Materia() {
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    

	/**
	 * @return the idEscuela
	 */
	public String getIdEscuela() {
		return idEscuela;
	}

	/**
	 * @param idEscuela the idEscuela to set
	 */
	public void setIdEscuela(String idEscuela) {
		this.idEscuela = idEscuela;
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
    
    
}
