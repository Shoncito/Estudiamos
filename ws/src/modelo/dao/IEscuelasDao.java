package modelo.dao;

import modelo.dto.Escuela;
/**
 * Interface de objeto de acceso a datos de las escuelas
 * @author Santiago P�rez
 *
 */
public interface IEscuelasDao extends IDao<Escuela>{
	Escuela consultarPorNombre(String nombre);
}
