package modelo.dao;

import java.util.LinkedList;

public class Dao {
    protected LinkedList data;
    private final String FILENAME;
/**
 * Método constructor de la clase DAO
 * @param FILENAME que recibe el nombre del archivo de datos
 */
    public Dao(String FILENAME) {
        this.FILENAME = FILENAME;
    }
/**
 * Método que retorna el archivo de datos
 * @return Archivo de datos
 */
    public String getFILENAME() {
        return FILENAME;
    }
}
