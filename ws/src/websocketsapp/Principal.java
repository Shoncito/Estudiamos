package websocketsapp;

import java.io.IOException;

import modelo.dao.CategoriaSnackDao;
import modelo.dao.EscuelasDao;
import modelo.dao.GruposEstudioDao;
import modelo.dao.IDao;
import modelo.dao.MateriaDao;
import modelo.dao.ProfesorDao;
import modelo.dao.PublicacionDao;
import modelo.dao.SnackDao;
import modelo.dao.TutoriasDao;
import modelo.dao.UsuariosDao;
import threads.Observador;

public class Principal {
	public static void main(String[] args) {
		cargarDaos();
		Server server = new Server();
		server.start();
	}

	private static void cargarDaos() {
		try {
			IDao daos[]= {UsuariosDao.getInstancia(),
					TutoriasDao.getInstancia(),
					SnackDao.getInstancia(),
					PublicacionDao.getInstancia(),
					ProfesorDao.getInstancia(),
					MateriaDao.getInstancia(),
					GruposEstudioDao.getInstancia(),
					EscuelasDao.getInstancia(),
					CategoriaSnackDao.getInstancia()};
			for (int i = 0; i < daos.length; i++) {
				daos[i].leerArchivo();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
