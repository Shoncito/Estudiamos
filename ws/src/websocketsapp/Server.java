package websocketsapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.print.attribute.Size2DSyntax;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.Messaging.SyncScopeHelper;

import funciones.Buscador;
import funciones.Comparador;
import modelo.dao.UsuariosDao;
import modelo.dto.GrupoEstudio;
import modelo.dto.Profesor;
import modelo.dto.Tutoria;
import modelo.dto.Usuario;

public class Server extends WebSocketServer {
//	private static Map<Integer,Set<WebSocket>> Rooms = new HashMap<>();
	public LinkedList<Usuario> usuarios;

	public Server() {
		super(new InetSocketAddress(8080));
		this.usuarios = new LinkedList<Usuario>();
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash "
				+ conn.getRemoteSocketAddress().hashCode());
		Usuario usuario = Comparador.comparar(usuarios, conn.getRemoteSocketAddress());
		if (usuario != null) {
			usuario.setWebSocket(conn);
		}

		// System.out.println(clients.size());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println(message);
		JSONObject js = new JSONObject(message);
		System.out.println(js.getString("tipo"));
		if (js.getString("tipo").equals("ping")) {
			conn.send("pong");
		} else if (js.getString("tipo").equals("login")) {
			loginUsuario(conn, js);
		} else if (js.getString("tipo").equals("registro")) {
			registroUsuario(conn, js);
		} else if (js.getString("tipo").equals("crear materia")) {
			crearMateria(conn, js);
		} else if (js.getString("tipo").equals("crear grupo")) {
			crearGrupo(conn, js);
		} else if (js.getString("tipo").equals("crear profesor")) {
			crearProfesor(conn, js);
		} else if (js.getString("tipo").equals("asociar materia a profesor")) {
			asociarMateria(conn, js);
		} else if (js.getString("tipo").equals("pedir tutoria")) {
			pedirTutoria(conn, js);
		} else if (js.getString("tipo").equals("crear categoria snack")) {
			crearCategoriaSnack(conn, js);
		} else if (js.getString("tipo").equals("crear snack")) {
			crearSnack(conn, js);
		} else if (js.getString("tipo").equals("crear escuela")) {
			crearEscuela(conn, js);
		} else if (js.getString("tipo").equals("ingresar a grupo")) {
			ingresarAGrupo(conn, js);
		} else if (js.getString("tipo").equals("crear publicación")) {
			crearPublicacion(conn, js);
		} else if (js.getString("tipo").equals("agregar horario disponible")) {
			agregarHorarioProfesor(conn, js);
		} else if (js.getString("tipo").equals("consultar tutorias")) {
			consultarTutorias(conn, js);
		} else if (js.getString("tipo").equals("consultar publicaciones")) {
			consultarPublicaciones(conn, js);
		} else if (js.getString("tipo").equals("consultar profesores")) {
			consultarProfesores(conn, js);
		} else if (js.getString("tipo").equals("consultar profesor")) {
			consultarProfesor(conn, js);
		} else if (js.getString("tipo").equals("consultar grupos")) {
			consultarGrupos(conn, js);
		} else if (js.getString("tipo").equals("consultar snacks")) {
			consultarSnacks(conn, js);
		} else if (js.getString("tipo").equals("editar grupo")) {
			crearGrupo(conn, js);
		} else if (js.getString("tipo").equals("editar publicación")) {
			editarPublicacion(conn, js);
		} else if (js.getString("tipo").equals("eliminar materia")) {
			eliminarMateria(conn, js);
		} else if (js.getString("tipo").equals("eliminar grupo")) {
			eliminarGrupo(conn, js);
		} else if (js.getString("tipo").equals("eliminar profesor")) {
			eliminarProfesor(conn, js);
		} else if (js.getString("tipo").equals("eliminar publicación")) {
			eliminarPublicacion(conn, js);
		} else if (js.getString("tipo").equals("cancelar tutoria")) {
			cancelarTutoria(conn, js);
		} else if (js.getString("tipo").equals("eliminar tutoria")) {
			eliminarTutoria(conn, js);
		} else if (js.getString("tipo").equals("eliminar categoria")) {
			eliminarCategoria(conn, js);
		} else if (js.getString("tipo").equals("eliminar snack")) {
			eliminarSnack(conn, js);
		}

	}

	private void eliminarSnack(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarCategoria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarTutoria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void cancelarTutoria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarPublicacion(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarGrupo(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void eliminarMateria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void editarPublicacion(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarSnacks(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarGrupos(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarProfesores(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarPublicaciones(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarTutorias(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void agregarHorarioProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearPublicacion(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void ingresarAGrupo(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearEscuela(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearSnack(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearCategoriaSnack(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void pedirTutoria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void asociarMateria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearGrupo(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void crearMateria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void registroUsuario(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void loginUsuario(WebSocket conn, JSONObject js) {
		UsuariosDao usuarioDao =UsuariosDao.getInstancia();
		try {
			usuarioDao.leerArchivo();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario usuario = usuarioDao.consultar(js.getString("usuario"));
		String mensaje = "";
		if (usuario == null) {
			mensaje = "{" + "	tipo: error," + "	mensaje: usuario o contraseña inválidos" + "}";
		} else {
			if (usuario.getContraseña().equals(js.getString("contraseña"))) {
				mensaje = "{" + "	tipo: token," + "	mensaje: " + usuario.getToken() + "}";
				usuario.setWebSocket(conn);
			} else {
				mensaje = "{" + "	tipo: error," + "	mensaje: usuario o contraseña inválidos" + "}";
			}
		}
		conn.send(mensaje);
		try {
			usuarioDao.escribirArchivo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		if (!reason.equals("cambio")) {
			Usuario usuario = Buscador.buscarUsuario(conn, usuarios);
			this.usuarios.remove(usuario);
			usuario.setWebSocket(null);
		}
		System.out.println("Client disconnected: " + reason);
	}

	@Override
	public void onError(WebSocket conn, Exception exc) {
		System.out.println("Error happened: " + exc);
	}

	public void sendToAll(WebSocket conn, String message) {

		for (int i = 0; i < usuarios.size(); i++) {
			WebSocket c = (WebSocket) usuarios.get(i).getWebSocket();
			if (c != conn)
				c.send(message);
		}
	}

}
