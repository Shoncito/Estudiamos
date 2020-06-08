package websocketsapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
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
import modelo.dto.CategoriaSnack;
import modelo.dto.Escuela;
import modelo.dto.GrupoEstudio;
import modelo.dto.Materia;
import modelo.dto.Profesor;
import modelo.dto.Snack;
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
		} else if (js.getString("tipo").equals("asociar materia")) {
			asociarMateria(conn, js);
		} else if (js.getString("tipo").equals("pedir tutoria")) {
			pedirTutoria(conn, js);
		}else if (js.getString("tipo").equals("pedir tutoria")) {
			crearTutoria(conn, js);
		}
		else if (js.getString("tipo").equals("crear categoria snack")) {
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
		}else if(js.getString("tipo").equals("apagar")) {
			apagarServidor(conn,js);
		}else if(js.getString("tipo").equals("consultar categorias")) {
			consultarCategorias(conn,js);
		}

	}

	private void consultarCategorias(WebSocket conn, JSONObject js) {
		CategoriaSnackDao categoriaSnackDao = CategoriaSnackDao.getInstancia();
		LinkedList<CategoriaSnack> categorias = categoriaSnackDao.listarTodos();
		String mensaje ="{" + 
				"	\"tipo\": \"lista categorias\"," + 
				"	\"categorias\": [";
		for (int i = 0; i < categorias.size(); i++) {
			mensaje +=categorias.get(i).toJSON();
			if(i<categorias.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]";
		conn.send(mensaje);
	}

	private void crearTutoria(WebSocket conn, JSONObject js) {
		TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		Profesor profesor = profesorDao.consultar(js.getString("idProfesor"));
		Materia materia = materiaDao.consultar(js.getString("idMateria"));
		String mensaje="";
		if(profesor==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese profesor no existe\"" + 
					"}";
		}else if(materia==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa materia no existe\"" + 
					"}";
		}else {
			Tutoria tutoria = new Tutoria();
			tutoria.setProfesor(profesor);
			tutoria.setHora(js.getInt("hora"));
			tutoria.setLugar(js.getString("lugar"));
			tutoria.setUsuarios(new LinkedList());
			tutoria.setFecha(js.getString("fecha"));
			String idTutoria= ""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
			tutoria.setIdTutoria(idTutoria);
			if(tutoriasDao.crear(tutoria)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Tutoría creada\"" +
						"\"idTutoria\": \""+tutoria.getIdTutoria()+"\""+
						"}";
			}
		}
		conn.send(mensaje);
			
	}

	private void apagarServidor(WebSocket conn, JSONObject js) {
		System.out.println("Guardando repositorios");
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
				daos[i].escribirArchivo();;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Servidor apagado");
		System.exit(0);
		
	}

	private void eliminarSnack(WebSocket conn, JSONObject js) {
		SnackDao snackDao = SnackDao.getInstancia();
		Snack snack = snackDao.consultar(js.getString("idSnack"));
		String mensaje = "";
		if(snack!=null) {
			if(snackDao.eliminar(snack)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Snack eliminado\"" +
						"}";
			}else {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Error al eliminar snack\"" + 
						"}";
			}
				
		}else {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese snack no existe\"" + 
					"}";
		}
		conn.send(mensaje);
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
		EscuelasDao escuelaDao = EscuelasDao.getInstancia();
		Escuela escuela = new Escuela();
		String idEscuela =""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
		escuela.setIdEscuela(idEscuela);
		escuela.setNombreEscuela(js.getString("nombreEscuela"));
		String mensaje = "";
		if(escuelaDao.consultarPorNombre(escuela.getNombreEscuela())!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa escuela ya existe\"" + 
					"}";
		}else {
			if(escuelaDao.crear(escuela)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Escuela creada\"" +
						"\"idEscuela\": \""+escuela.getIdEscuela()+"\""+
						"}";
			}
		}
		conn.send(mensaje);

	}

	private void crearSnack(WebSocket conn, JSONObject js) {
		SnackDao snackDao = SnackDao.getInstancia();
		CategoriaSnackDao categoriaSnackDao = CategoriaSnackDao.getInstancia();
		Snack snack = new Snack();
		snack.setNombreSnack(js.getString("nombreSnack"));
		snack.setPrecio(js.getFloat("precio"));
		snack.setImagen(js.getString("imagen"));
		snack.setIdCategoria(js.getString("idCategoria"));
		String idSnack = ""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
		snack.setIdSnack(idSnack);
		if(categoriaSnackDao.consultar(js.getString("idCategoria"))==null) {
			
		}
	}

	private void crearCategoriaSnack(WebSocket conn, JSONObject js) {
		CategoriaSnackDao categoriaSnackDao = CategoriaSnackDao.getInstancia();
		CategoriaSnack categoria = new CategoriaSnack();
		String idCategoria=""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
		categoria.setIdCategoria(idCategoria);
		categoria.setNombreCategoria(js.getString("nombreCategoria"));
		String mensaje="";
		if(categoriaSnackDao.consultarPorNombre(categoria.getNombreCategoria())!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa escuela ya existe\"" + 
					"}";
		}else {
			if(categoriaSnackDao.crear(categoria)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Categoría creada\"" +
						"\"idCategoria\": \""+categoria.getIdCategoria()+"\""+
						"}";
			}
		}
		conn.send(mensaje);
	}
	/**
	 * 
	 * @param conn
	 * @param js
	 */
	private void pedirTutoria(WebSocket conn, JSONObject js) {
		TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		Tutoria tutoria=tutoriasDao.consultar(js.getString("idTutoria"));
		String mensaje="";
		if(tutoria==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa tutoría no existe\"" + 
					"}";
		}else {
			Usuario usuario = usuariosDao.consultar(js.getString("usuario"));
			if(usuario==null) {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Ese usuario no existe\"" + 
						"}";
			}
		}

	}

	private void asociarMateria(WebSocket conn, JSONObject js) {
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		Profesor profesor = profesorDao.consultar(js.getString("idProfesor"));
		String mensaje="";
		if(profesor==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese profesor no existe\"" + 
					"}";
		}else {
			if(profesorDao.consultarMateria(js.getString("idMateria"), profesor)) {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Ese profesor no existe\"" + 
						"}";
			}else {
				MateriaDao materiaDao = MateriaDao.getInstancia();
				Materia materia=materiaDao.consultar(js.getString("idMateria"));
				if(materia!=null) {
					profesor.getMaterias().add(materia.getIdMateria());
					mensaje="{" + 
							"\"tipo\": \"ok\"" + 
							"\"mensaje\": \"Materia "+materia.getNombreMateria()+" vinculada a "+profesor.getNombre()+"\"" + 
							"}";
				}else {
					mensaje ="{" + 
							"\"tipo\": \"error\"," + 
							"\"mensaje\": \"Esa materia no existe\"" + 
							"}";
				}
			}
		}
		conn.send(mensaje);
	}

	private void crearProfesor(WebSocket conn, JSONObject js) {
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		Profesor profesor = profesorDao.consultar(js.getString("idProfesor"));
		String mensaje = "";
		if(profesor!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese profesor ya existe\"" + 
					"}";
		}else {
			profesor = new Profesor();
			String idProfesor = ""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
			profesor.setId(idProfesor);
			profesor.setNombre(js.getString("nombreProfesor"));
			profesor.setMaterias(new LinkedList());
			if(profesorDao.crear(profesor)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Materia creada\"" +
						"\"idProfesor\": \""+profesor.getId()+"\""+
						"}";
			}
		}
		conn.send(mensaje);
	}

	private void crearGrupo(WebSocket conn, JSONObject js) {
		GruposEstudioDao gruposEstudioDao = GruposEstudioDao.getInstancia();
		String mensaje="";
		GrupoEstudio grupo = new GrupoEstudio();
		grupo.setFecha(js.getString("fecha"));
		grupo.setHora(js.getInt("hora"));
		grupo.setTema(js.getString("tema"));
		grupo.setUsuarios(new LinkedList());
		grupo.getUsuarios().add(js.getString("idUsuario"));
		grupo.setIdMateria(js.getString("idMateria"));
		grupo.setLugar(js.getString("lugar"));
		String idGrupo =""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
		grupo.setIdGrupo(idGrupo);
		if(gruposEstudioDao.consultarPorNombre(grupo.getNombreGrupo())!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese grupo ya existe\"" + 
					"}";
		}else {
			if(gruposEstudioDao.crear(grupo)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Materia creada\"" +
						"\"idGrupo\": \""+idGrupo+"\""+
						"}";
			}
		}
		
		conn.send(mensaje);
		
	}

	private void crearMateria(WebSocket conn, JSONObject js) {
		EscuelasDao escuelaDao = EscuelasDao.getInstancia(); 
		Escuela escuela = escuelaDao.consultar(js.getString("idEscuela"));
		String mensaje = "";
		if(escuela==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa escuela no existe\"" + 
					"}";
		}else {
			MateriaDao materiaDao = MateriaDao.getInstancia();
			Materia materia = new Materia();
			materia.setIdEscuela(escuela.getIdEscuela());
			String idMateria = ""+Calendar.YEAR+(Calendar.MONTH+1)+Calendar.DAY_OF_MONTH+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND;
			materia.setIdMateria(idMateria);
			materia.setNombreMateria(js.getString("nombreMateria"));
			if(materiaDao.consultar(materia.getIdMateria())!=null) {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Esa materia ya existe\"" + 
						"}";		
			}else {
				if(materiaDao.crear(materia)) {
					mensaje = "{" + 
							"\"tipo\": \"ok\"," + 
							"\"mensaje\": \"Materia creada\"" +
							"\"idMateria\": \""+materia.getIdMateria()+"\""+
							"}";
				}
			}
		}
		conn.send(mensaje);
	}

	private void registroUsuario(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		Usuario usuario = new Usuario();
		String mensaje="";
		if(usuariosDao.consultar(js.getString("usuario"))!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese usuario ya existe\"" + 
					"}";
		}
		else {
			usuario.setUsuario(js.getString("usuario"));
			usuario.setCorreo(js.getString("correo"));
			usuario.setContraseña(js.getString("contraseña"));
			if(usuariosDao.crear(usuario)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Usuario creado\"" +
						"\"hash\": \""+usuario.hashCode()+"\""+
						"}";
			}
		}
		conn.send(mensaje);

	}

	private void loginUsuario(WebSocket conn, JSONObject js) {
		UsuariosDao usuarioDao =UsuariosDao.getInstancia();
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
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		if (!reason.equals("cambio")) {
			Usuario usuario = Buscador.buscarUsuario(conn, usuarios);
			if(usuario!=null) {
				this.usuarios.remove(usuario);
				usuario.setWebSocket(null);
			}
		}
		System.out.println("Client disconnected: " + reason);
	}

	@Override
	public void onError(WebSocket conn, Exception exc) {
		System.out.println("Error happened: " + exc.getMessage());
	}

	public void sendToAll(WebSocket conn, String message) {

		for (int i = 0; i < usuarios.size(); i++) {
			WebSocket c = (WebSocket) usuarios.get(i).getWebSocket();
			if (c != conn)
				c.send(message);
		}
	}

}
