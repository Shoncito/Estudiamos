package modelo.dao;

import java.util.LinkedList;
/**
 * Clase de objeto de acceso a datos
 * @author Santiago Pérez
 *
 */
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
