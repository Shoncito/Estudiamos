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
public class CategoriaSnack implements Serializable {
    private String idCategoria;
    private String nombreCategoria;

    public CategoriaSnack() {
    }
    

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }



	public String toJSON() {
		return "{\"idCategoria\":\"" + idCategoria + "\", \"nombreCategoria\":\"" + nombreCategoria + "\"}";
	}
    
}
