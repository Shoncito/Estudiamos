/*
 * Clase PublicacionDao
 * @author Sebastian Miranda
 */
package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.Publicacion;


public class PublicacionDao extends Dao implements IPublicacionDao {

	private static PublicacionDao instancia;
	private PublicacionDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Publicacion dto) {
		return this.data.add(dto);
	}

	@Override
	public Publicacion consultar(String id) {
		Publicacion publicacion = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Publicacion)this.data.get(i)).getIdPublicacion().equals(id)){
            	publicacion = (Publicacion)this.data.get(i);
                break;
            }
        }
        return publicacion;
	}

	@Override
	public boolean actualizar(Publicacion dto) {
		for (int i = 0; i < this.data.size(); i++) {
			if(((Publicacion)this.data.get(i)).getIdPublicacion().equals(dto.getIdPublicacion())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Publicacion dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Publicacion)this.data.get(i)).getIdPublicacion().equals(dto.getIdPublicacion())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Publicacion> listarTodos() {
		return this.data;
	}

	@Override
	public void leerArchivo() throws IOException, ClassNotFoundException {
		  try{
	            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(this.getFILENAME()));
	            this.data = (LinkedList) fileIn.readObject();
	            
	        }catch(FileNotFoundException e){
	            this.data = new LinkedList();
	        }
	}

	@Override
	public void escribirArchivo() throws IOException {
		  ObjectOutputStream fileO = new ObjectOutputStream(new FileOutputStream(this.getFILENAME()));
	      fileO.writeObject(this.data);
	}
	public static PublicacionDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new PublicacionDao("publicacion");
    	}
    	return instancia;
    }
}
