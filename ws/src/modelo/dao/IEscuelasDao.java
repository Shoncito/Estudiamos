package modelo.dao;

import modelo.dto.Escuela;
/**
 * Interface de objeto de acceso a datos de las escuelas
 * @author Santiago Pérez
 *
 */
public interface IEscuelasDao extends IDao<Escuela>{
	Escuela consultarPorNombre(String nombre);
}
