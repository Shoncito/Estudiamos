package threads;

import java.util.LinkedList;

import org.java_websocket.WebSocket;

import modelo.dao.UsuariosDao;
import modelo.dto.GrupoEstudio;
import modelo.dto.Usuario;

public class Pomodoro implements Runnable {

	GrupoEstudio grupoEstudio;
	
	/**
	 * @param grupoEstudio
	 */
	public Pomodoro(GrupoEstudio grupoEstudio) {
		this.grupoEstudio = grupoEstudio;
	}

	@Override
	public void run() {
		try {
			for(int j=0;j<4;j++) {
				for(int i=0;i<this.grupoEstudio.getUsuarios().size();i++) {
					String mensaje = "{" + 
							"	tipo: notificacion," + 
							"	subtipo: grupo," + 
							"	mensaje: ¡A estudiar!. Estudia 20 minutos" + 
							"}";
					enviarATodosEnElGrupo(mensaje);
				}
				Thread.sleep(1200000);
				int descanso=5;
				if(j==3) {
					descanso=20;
				}
				for(int i=0;i<this.grupoEstudio.getUsuarios().size();i++) {
					String mensaje = "{" + 
							"	tipo: notificacion," + 
							"	subtipo: grupo," + 
							"	mensaje: descansa "+descanso+" minutos " + 
							"}";
					enviarATodosEnElGrupo(mensaje);
				}
				Thread.sleep(descanso*60*1000);
				String mensaje = "{" + 
						"	tipo: notificacion," + 
						"	subtipo: grupo," + 
						"	mensaje: Grupo estudio finalizado" + 
						"}";
			}
			
		}catch(InterruptedException ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		

	}

	private void enviarATodosEnElGrupo(String mensaje) {
		LinkedList<String> usuarios = grupoEstudio.getUsuarios();
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		for(String nombreUsuario : usuarios) {
			Usuario usuario =usuariosDao.consultar(nombreUsuario);
			WebSocket w = usuario.getWebSocket();
			if(w!=null) {
				w.send(mensaje);
			}else {
				System.out.println(usuario.getUsuario()+" no está conectado");
			}
		}
	}

}
