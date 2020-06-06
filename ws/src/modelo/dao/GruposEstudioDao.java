package modelo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import modelo.dto.CategoriaSnack;
import modelo.dto.GrupoEstudio;

public class GruposEstudioDao extends Dao implements IGruposEstudioDao {

	public GruposEstudioDao(String FILENAME) {
		super(FILENAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(GrupoEstudio dto) {
		// TODO Auto-generated method stub
		return this.data.add(dto);
	}

	@Override
	public GrupoEstudio consultar(String id) {
		GrupoEstudio grupo = null;
        for (int i = 0; i < this.data.size(); i++) {
            if(((GrupoEstudio)this.data.get(i)).getIdGrupo()==Integer.parseInt(id)){
                grupo = (GrupoEstudio)this.data.get(i);
                break;
            }
        }
        return grupo;
	}

	@Override
	public boolean actualizar(GrupoEstudio dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(dto.getIdGrupo() == (((GrupoEstudio)this.data.get(i)).getIdGrupo())){
                this.data.set(i, dto);
                return true;
            }
        }
        return false;
	}

	@Override
	public boolean eliminar(GrupoEstudio dto) {
		for (int i = 0; i < this.data.size(); i++) {
            if(((GrupoEstudio)this.data.get(i)).getIdGrupo()==(dto.getIdGrupo())){
                this.data.remove(i);
                return true;
            }
        }
        return false;
	}

	@Override
	public LinkedList<GrupoEstudio> listarTodos() {
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


}
