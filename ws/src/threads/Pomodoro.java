package threads;

import java.util.LinkedList;

import modelo.GrupoEstudio;
import modelo.Usuario;

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
							"	mensaje: Descansa "+descanso+" minutos" + 
							"}";
					enviarATodosEnElGrupo(mensaje);
				}
				Thread.sleep(descanso*60*1000);
				String mensaje = "{" + 
						"	tipo: notificacion," + 
						"	mensaje: grupo de estudio finalizado" + 
						"}";
			}
			
		}catch(InterruptedException ex) {
			System.out.println("Error: "+ex.getMessage());
		}
		

	}

	private void enviarATodosEnElGrupo(String mensaje) {
		LinkedList<Usuario> usuarios = grupoEstudio.getUsuarios();
		for(Usuario usuario : usuarios) {
			usuario.getWebSocket().send(mensaje);
		}
	}

}
