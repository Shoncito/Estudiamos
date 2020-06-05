package threads;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import modelo.GrupoEstudio;
import modelo.Tutoria;
import modelo.Usuario;
import websocketsapp.Server;

public class Observador implements Runnable {

	Server server;

	LinkedList<GrupoEstudio>gruposActivos;
	/**
	 * @param server
	 */
	public Observador(Server server) {
		super();
		this.server = server;
		this.gruposActivos=new LinkedList();
	}
	@Override
	public void run() {
		LinkedList<Usuario> usuarios = server.usuarios;
			try {
				Calendar calendario = Calendar.getInstance();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minutos = calendario.get(Calendar.MINUTE);
				int dia = calendario.get(Calendar.DATE);
				String dias = "";
				if (dia < 10) {
					dias = "0" + dia;
				} else {
					dias = "" + dia;
				}
				String meses = "";
				int mes = calendario.get(Calendar.MONTH);
				if (mes < 10) {
					meses = "0" + mes;
				} else {
					meses = "" + mes;
				}
				int año = calendario.get(Calendar.YEAR);
				String fecha = año + "-" + meses + "-" + dias;
				while (true) {
					for (int i = 0; i < usuarios.size(); i++) {
						LinkedList<Tutoria> tutorias = usuarios.get(i).getTutorias();
						for (int j = 0; j < tutorias.size(); j++) {
							String fecha2 = tutorias.get(j).getFecha();
							if (fecha.equals(fecha2)) {
								int hora2 = tutorias.get(j).getHora();
								if (hora == hora2) {
									enviarMensaje(usuarios.get(i), tutorias.get(j));
								}

							}
						}
						LinkedList<GrupoEstudio> gruposEstudio = usuarios.get(i).getGruposEstudio();
						for (int j = 0; j < gruposEstudio.size(); j++) {
							String fecha2 = gruposEstudio.get(j).getFecha();
							if (fecha.equals(fecha2)) {
								int hora2 = gruposEstudio.get(j).getHora();
								if (hora == hora2) {
									if(!existeGrupo(gruposEstudio.get(j))) {
										iniciarPomodoro(usuarios.get(i),gruposEstudio.get(j));
									}else {
										agregarAGrupo(usuarios.get(i),gruposEstudio.get(j));
									}
									
								}

							}
						}

					}
					Thread.sleep(3600000);
				}
				
			}catch(InterruptedException ex) {
				System.out.println("Error: "+ex.getMessage());
			}
			
			
		

	}

	private void agregarAGrupo(Usuario usuario, GrupoEstudio grupoEstudio) {
		for(GrupoEstudio grupoEstudio2: gruposActivos) {
			if(grupoEstudio2.equals(grupoEstudio)) {
				grupoEstudio2.getUsuarios().add(usuario);
			}
		}
		
	}
	private boolean existeGrupo(GrupoEstudio grupoEstudio) {
		for(GrupoEstudio grupoEstudio2: gruposActivos) {
			if(grupoEstudio2.equals(grupoEstudio)) {
				return true;
			}
		}
		return false;
	}
	private void iniciarPomodoro(Usuario usuario, GrupoEstudio grupoEstudio) {
		grupoEstudio.getUsuarios().add(usuario);
		this.gruposActivos.add(grupoEstudio);
		Pomodoro pomodoro = new Pomodoro(grupoEstudio);
		Thread hiloPomodoro = new Thread(pomodoro);
		hiloPomodoro.start();
	}

	private void enviarMensaje(Usuario usuario, Tutoria tutoria) {
		String mensaje = "{ tipo: notificar," + "subtipo: tutoria," + "mensaje:" + tutoria.toString() + "}";
		usuario.getWebSocket().send(mensaje);
	}

	

}
