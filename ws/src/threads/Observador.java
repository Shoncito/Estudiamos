package threads;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import modelo.dao.GruposEstudioDao;
import modelo.dao.TutoriasDao;
import modelo.dao.UsuariosDao;
import modelo.dto.GrupoEstudio;
import modelo.dto.Tutoria;
import modelo.dto.Usuario;
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
			try {
				Calendar calendario = Calendar.getInstance();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minutos = calendario.get(Calendar.MINUTE);
				int segundos = calendario.get(Calendar.SECOND);
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
					TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
					GruposEstudioDao gruposEstudioDao = GruposEstudioDao.getInstancia();
					LinkedList<GrupoEstudio> gruposEstudio = gruposEstudioDao.listarTodos();
					LinkedList<Tutoria> tutorias = tutoriasDao.listarTodos();
					UsuariosDao usuariosDao = UsuariosDao.getInstancia();
					
					//Se consultan las tutorias actuales que están activas
					for(Tutoria tutoria: tutorias) {
						if(tutoria.getFecha().equals(fecha)) {
							if(tutoria.getHora()==hora) {
								LinkedList<String>nombreUsuarios=tutoria.getUsuarios();
								for(String nombreUsuario: nombreUsuarios) {
									Usuario usuario = usuariosDao.consultar(nombreUsuario);
									if(usuario.getWebSocket()==null) {
										System.out.println("El usuario "+usuario.getUsuario()+" no está conectado");
									}else {
										this.enviarMensaje(usuario, tutoria);
									}
								}
								tutorias.remove(tutoria);
							}
						}
					}
					for(GrupoEstudio grupoEstudio: gruposEstudio) {
						if(grupoEstudio.getFecha().equals(fecha)) {
							if(grupoEstudio.getHora()==hora) {
								this.iniciarPomodoro(grupoEstudio);
							}
						}
					}
					Thread.sleep(3600000 - (minutos*60*1000+segundos*1000));
				}
				
			}catch(InterruptedException ex) {
				System.out.println("Error: "+ex.getMessage());
			}
			
			
		

	}


	private void iniciarPomodoro(GrupoEstudio grupoEstudio) {
		this.gruposActivos.add(grupoEstudio);
		Pomodoro pomodoro = new Pomodoro(grupoEstudio,this.gruposActivos);
		Thread hiloPomodoro = new Thread(pomodoro);
		hiloPomodoro.start();
	}

	private void enviarMensaje(Usuario usuario, Tutoria tutoria) {
		String mensaje = "{ \"tipo\": \"notificar\"," + "\"subtipo: \"tutoria\"," + "\"mensaje\":\"" + tutoria.toString() + "\"}";
		usuario.getWebSocket().send(mensaje);
	}

	

}
