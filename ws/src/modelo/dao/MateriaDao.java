/*
 * Clase MateriaDao
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

import modelo.dto.Materia;


public class MateriaDao extends Dao implements IMateriaDao {

	public MateriaDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Materia dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public Materia consultar(String id) {
		Materia materia = null;
        for (int i = 0; i < this.data.size(); i++) {
        	int idMateria=Integer.parseInt(id);
            if(((Materia)this.data.get(i)).getIdMateria()==idMateria){
            	materia = (Materia)this.data.get(i);
                break;
            }
        }
        return materia;
	}

	@Override
	public boolean actualizar(Materia dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getIdMateria()==((Materia)this.data.get(i)).getIdMateria()){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Materia dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Materia)this.data.get(i)).getIdMateria()==dto.getIdMateria()){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Materia> listarTodos() {
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
