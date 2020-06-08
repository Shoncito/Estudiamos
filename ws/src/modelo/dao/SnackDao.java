/*
 * Clase SnackDao
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

import modelo.dto.Snack;


public class SnackDao extends Dao implements ISnackDao {

	private static SnackDao instancia;
	public SnackDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Snack dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public Snack consultar(String id) {
		Snack snack = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Snack)this.data.get(i)).getIdSnack().equals(id)){
                snack = (Snack)this.data.get(i);
                break;
            }
        }
        return snack;
	}

	@Override
	public boolean actualizar(Snack dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getIdSnack()==((Snack)this.data.get(i)).getIdSnack()){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	
	@Override
	public boolean eliminar(Snack dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Snack)this.data.get(i)).getIdSnack().equals(dto.getIdSnack())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	
	@Override
	public LinkedList<Snack> listarTodos() {
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

	public static SnackDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new SnackDao("snack");
    	}
    	return instancia;
    }
}
