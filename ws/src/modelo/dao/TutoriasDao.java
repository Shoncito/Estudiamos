package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.Tutoria;
/**
 * Clase de objeto de acceso a datos de las tutorías
 * @author Santiago Pérez
 *
 */
public class TutoriasDao extends Dao implements ITutoriasDao {

	private static TutoriasDao instancia;
	private TutoriasDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Tutoria dto) {
		
		return this.data.add(dto);
	}

	@Override
	public Tutoria consultar(String id) {
		Tutoria tutoria = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Tutoria)this.data.get(i)).getIdTutoria()==id){
                tutoria = (Tutoria)this.data.get(i);
                break;
            }
        }
        return tutoria;
	}

	@Override
	public boolean actualizar(Tutoria dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getIdTutoria() == (((Tutoria)this.data.get(i)).getIdTutoria())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Tutoria dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Tutoria)this.data.get(i)).getIdTutoria()==(dto.getIdTutoria())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Tutoria> listarTodos() {
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
    
    public static TutoriasDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new TutoriasDao("tutorias");
    	}
    	return instancia;
    }
}
