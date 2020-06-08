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
public class Escuela implements Serializable {
    private String idEscuela;
    private String nombreEscuela;

    public Escuela() {
    }

    public String getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(String idEscuela) {
        this.idEscuela = idEscuela;
    }

    public String getNombreEscuela() {
        return nombreEscuela;
    }

    public void setNombreEscuela(String nombreEscuela) {
        this.nombreEscuela = nombreEscuela;
    }

	public String toJSON() {
		return "{\"idEscuela\":\"" + idEscuela + "\", \"nombreEscuela\":\"" + nombreEscuela + "\"}";
	}

    
    
    
}
