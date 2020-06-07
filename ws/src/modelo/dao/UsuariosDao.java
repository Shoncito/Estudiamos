package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.Usuario;

/**
 * Clase de objeto de acceso a datos del usuario
 * @author Santiago Pérez
 *
 */

public class UsuariosDao extends Dao implements IUsuariosDao {

	private static UsuariosDao instancia;
	
	private UsuariosDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Usuario dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public Usuario consultar(String id) {
		Usuario usuario = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((Usuario)this.data.get(i)).getUsuario().equals(id)){
                usuario = (Usuario)this.data.get(i);
                break;
            }
        }
        return usuario;
	}

	@Override
	public boolean actualizar(Usuario dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getUsuario().equals(((Usuario)this.data.get(i)).getUsuario())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(Usuario dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((Usuario)this.data.get(i)).getUsuario().equals(dto.getUsuario())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<Usuario> listarTodos() {
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

    public static UsuariosDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new UsuariosDao("usuarios");
    	}
    	return instancia;
    }
}
