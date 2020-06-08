package modelo.dao;

import java.io.IOException;
import java.util.LinkedList;
/**
 * Interface de objeto de acceso a dato
 * @author Santiago Pérez
 *
 * @param <DTO> que es el objeto genérico
 */
public interface IDao <DTO>{
	boolean crear(DTO dto);
    DTO consultar(String id);
    boolean actualizar (DTO dto);
    boolean eliminar(DTO dto);
    LinkedList<DTO> listarTodos();
    void leerArchivo()throws IOException, ClassNotFoundException;
    void escribirArchivo()throws IOException;
}
