/**
 * 
 */
package modelo.dao;

import modelo.dto.GrupoEstudio;

/**
 * Interface de objeto de acceso a datos de los grupos de estudio
 * @author Santiago Pérez
 *
 */
public interface IGruposEstudioDao extends IDao<GrupoEstudio> {
	GrupoEstudio consultarPorNombre (String nombre);
}
