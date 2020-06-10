package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.CategoriaSnack;

/**
 * Clase de objeto de acceso a datos de las categorías de snack
 * @author Santiago Pérez
 *
 */
public class CategoriaSnackDao extends Dao implements ICategoriaSnack {

	private static CategoriaSnackDao instancia;
	private CategoriaSnackDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(CategoriaSnack dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public CategoriaSnack consultar(String id) {
		CategoriaSnack categoria = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((CategoriaSnack)this.data.get(i)).getIdCategoria().equals(id)){
                categoria = (CategoriaSnack)this.data.get(i);
                break;
            }
        }
        return categoria;
	}

	@Override
	public boolean actualizar(CategoriaSnack dto) {
		for (int i = 0; i < this.data.size(); i++) {
			if(((CategoriaSnack)this.data.get(i)).getIdCategoria().equals(dto.getIdCategoria())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(CategoriaSnack dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((CategoriaSnack)this.data.get(i)).getIdCategoria().equals(dto.getIdCategoria())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<CategoriaSnack> listarTodos() {
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

    public static CategoriaSnackDao getInstancia() {
    	if(instancia ==null) {
    		instancia = new CategoriaSnackDao("categoria");
    	}
    	return instancia;
    }

	@Override
	public CategoriaSnack consultarPorNombre(String nombre) {
		CategoriaSnack categoria = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((CategoriaSnack)this.data.get(i)).getNombreCategoria().equals(nombre)){
                categoria = (CategoriaSnack)this.data.get(i);
                break;
            }
        }
        return categoria;
	}
}
