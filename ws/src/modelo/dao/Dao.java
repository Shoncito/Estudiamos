package modelo.dao;

import java.util.LinkedList;
/**
 * Clase de objeto de acceso a datos
 * @author Santiago P�rez
 *
 */
public class Dao {
    protected LinkedList data;
    private final String FILENAME;
/**
 * M�todo constructor de la clase DAO
 * @param FILENAME que recibe el nombre del archivo de datos
 */
    public Dao(String FILENAME) {
        this.FILENAME = FILENAME;
    }
/**
 * M�todo que retorna el archivo de datos
 * @return Archivo de datos
 */
    public String getFILENAME() {
        return FILENAME;
    }
}
