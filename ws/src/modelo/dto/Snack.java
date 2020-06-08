/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

/**
 *
 * @author Val
 */
public class Snack {
    private int idSnack;
    private String nombreSnack;
    private float precio;

    public Snack() {
    }

    public int getIdSnack() {
        return idSnack;
    }

    public void setIdSnack(int idSnack) {
        this.idSnack = idSnack;
    }

    public String getNombreSnack() {
        return nombreSnack;
    }

    public void setNombreSnack(String nombreSnack) {
        this.nombreSnack = nombreSnack;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
}
