package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.Escuela;
/**
 * Clase de objeto de acceso a datos de las escuelas
 * @author Santiago Pérez
 *
 */
public class EscuelasDao extends Dao implements IEscuelasDao {

	private static EscuelasDao instancia;
	private EscuelasDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Escuela dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public Escuela consultar(String id) {
		Escuela escuela = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Escuela)this.data.get(i)).getIdEscuela().equals(id)){
                escuela = (Escuela)this.data.get(i);
                break;
            }
        }
        return escuela;
	}

	@Override
	public boolean actualizar(Escuela dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getIdEscuela() == (((Escuela)this.data.get(i)).getIdEscuela())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Escuela dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Escuela)this.data.get(i)).getIdEscuela().equals(dto.getIdEscuela())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Escuela> listarTodos() {
		// TODO Auto-generated method stub
		return this.data;
	}

	 /**-
     * Método que permite leer el archivo de datos
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void leerArchivo() throws IOException, ClassNotFoundException{
        try{
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(this.getFILENAME()));
            this.data = (LinkedList) fileIn.readObject();
            
        }catch(FileNotFoundException e){
            this.data = new LinkedList();
        }
    }
    /**
     * Método que permite escribir el archivo de datos 
     * @throws IOException 
     */
    @Override
    public void escribirArchivo() throws IOException{
        ObjectOutputStream fileO = new ObjectOutputStream(new FileOutputStream(this.getFILENAME()));
        fileO.writeObject(this.data);
    }

    public static EscuelasDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new EscuelasDao("escuela");
    	}
    	return instancia;
    }

	@Override
	public Escuela consultarPorNombre(String nombre) {
		Escuela escuela = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Escuela)this.data.get(i)).getNombreEscuela().equals(nombre)){
                escuela = (Escuela)this.data.get(i);
                break;
            }
        }
        return escuela;
	}
}
