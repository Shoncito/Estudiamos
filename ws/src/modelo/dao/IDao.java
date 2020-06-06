package modelo.dao;

import java.io.IOException;
import java.util.LinkedList;

public interface IDao <DTO>{
	boolean crear(DTO dto);
    DTO consultar(String id);
    boolean actualizar (DTO dto);
    boolean eliminar(DTO dto);
    LinkedList<DTO> listarTodos();
    void leerArchivo()throws IOException, ClassNotFoundException;
    void escribirArchivo()throws IOException;
}
