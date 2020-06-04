package threads;

import java.util.Calendar;
import java.util.LinkedList;

import modelo.Tutoria;
import modelo.Usuario;
import websocketsapp.Server;

public class Observador implements Runnable {

	Server server;

	@Override
	public void run() {
		LinkedList<Usuario> usuarios = server.usuarios;

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
			meses = "" + mes;
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

			}

		}

	}

	private void enviarMensaje(Usuario usuario, Tutoria tutoria) {
		String mensaje ="{ tipo: notificar,"
				+ "subtipo: tutoria,"
				+ "mensaje:"+tutoria.toString()
				+ "}";
		usuario.getWebSocket().send(mensaje);
	}

	/**
	 * @param server
	 */
	public Observador(Server server) {
		super();
		this.server = server;
	}

}
