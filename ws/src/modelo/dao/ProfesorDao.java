/*
 * Clase ProfesorDao
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

import modelo.dto.Profesor;


public class ProfesorDao extends Dao implements IProfesorDao {

	public ProfesorDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Profesor dto) {
		return this.data.add(dto);
	}

	@Override
	public Profesor consultar(String id) {
		Profesor profesor = null;
        for (int i = 0; i < this.data.size(); i++) {
        	int idProfesor=Integer.parseInt(id);
            if(((Profesor)this.data.get(i)).getId()==idProfesor){
            	profesor = (Profesor)this.data.get(i);
                break;
            }
        }
        return profesor;
	}

	@Override
	public boolean actualizar(Profesor dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getId()==((Profesor)this.data.get(i)).getId()){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Profesor dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Profesor)this.data.get(i)).getId()==dto.getId()){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Profesor> listarTodos() {
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

}
