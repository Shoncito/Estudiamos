/*
 * Interface IProfesorDao
 * @author Sebastian Miranda
 */
package modelo.dao;

import modelo.dto.Profesor;


public interface IProfesorDao extends IDao <Profesor>{
	boolean consultarMateria(String idMateria, Profesor profesor);
}
