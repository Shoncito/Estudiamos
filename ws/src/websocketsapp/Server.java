package websocketsapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.LinkedList;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONObject;
import funciones.Buscador;
import funciones.Comparador;
import funciones.Generador;
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
import modelo.dto.Publicacion;
import modelo.dto.Snack;
import modelo.dto.Tutoria;
import modelo.dto.Usuario;
import threads.Observador;

public class Server extends WebSocketServer {
//	private static Map<Integer,Set<WebSocket>> Rooms = new HashMap<>();
	public LinkedList<Usuario> usuarios;

	public Server() {
		super(new InetSocketAddress(8080));
		this.usuarios = new LinkedList<Usuario>();
		Thread hiloObservador = new Thread(new Observador(this));
		this.start();
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash "
				+ conn.getRemoteSocketAddress().hashCode());
		Usuario usuario = Comparador.comparar(usuarios, conn.getRemoteSocketAddress());
		if (usuario != null) {
			usuario.setWebSocket(conn);
			String mensaje = "{" + "	\"tipo\": \"usuario carga\"," + "	\"mensaje\": " + usuario.toJSON() + "}";
			System.out.println(mensaje);
			conn.send(mensaje);
			if(usuario.getMotivoDesconexion()!=null) {
				if(!usuario.getMotivoDesconexion().equals("")) {
					JSONObject js = new JSONObject(usuario.getMotivoDesconexion());
					if(js.getString("tipo").equals("consultar tutorias")) {
						consultarTutoriasPor(usuario.getWebSocket(),js);
						usuario.setMotivoDesconexion(null);
					}else if(js.getString("tipo").equals("consultar mis grupos")) {
						consultarGruposDeUsuario(usuario);
					}else if(js.getString("tipo").equals("consultar grupos")) {
						consultarGrupos(conn,js);
					}else if (js.getString("tipo").equals("consultar publicaciones")) {
						consultarPublicaciones(conn, js);
					}else if(js.getString("tipo").equals("crear publicaciones")) {
						crearPublicaciones(conn,js);
					}
					
				}
			}
			
		}

		// System.out.println(clients.size());
	}

	private void crearPublicaciones(WebSocket conn, JSONObject js) {
		MateriaDao materiaDao = MateriaDao.getInstancia();
		Materia materia = materiaDao.consultar(js.getString("idMateria"));
		String mensaje = "{\"tipo\": \"materia de publicacion\"," + "	\"materia\": " + materia.toJSON() + "}";
		conn.send(mensaje);
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
		}else if (js.getString("tipo").equals("crear tutoria")) {
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
		}else if(js.getString("tipo").equals("consultar token")) {
			consultarToken(conn,js);
		}else if(js.getString("tipo").equals("consultar escuelas")) {
			consultarEscuelas(conn,js);
		}else if(js.getString("tipo").equals("consultar materias")) {
			consultarMaterias(conn,js);
		}else if(js.getString("tipo").equals("consultar profesor por materia")) {
			consultarProfesorPorMateria(conn,js);
		}
	}


	private void consultarGruposDeUsuario(Usuario usuario) {
		GruposEstudioDao gruposEstudioDao = GruposEstudioDao.getInstancia();
		LinkedList<GrupoEstudio> gruposEstudio = gruposEstudioDao.listarTodos();
		LinkedList<GrupoEstudio> gruposMios = new LinkedList();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		String mensaje ="{\"tipo\": \"mis grupos\" }";
		JSONObject objToSend = new JSONObject(mensaje);
		for(GrupoEstudio grupoEstudio: gruposEstudio) {
			LinkedList<String> nombreUsuarios = grupoEstudio.getUsuarios();
			cicloUsuario:
			for(String nombreUsuario: nombreUsuarios) {
				if(nombreUsuario.equals(usuario.getUsuario())) {
					gruposMios.add(grupoEstudio);
					break cicloUsuario;
				}
			}
		}
		JSONArray array = new JSONArray();
		for(GrupoEstudio grupoEstudio: gruposMios) {
			JSONObject grupo = new JSONObject(grupoEstudio.toJSON());
			Materia materia = materiaDao.consultar(grupoEstudio.getIdMateria());
			String stringMateria = materia.toJSON();
			grupo.put("materia", materia.toJSON());
			array.put(grupo);
		}
		objToSend.put("grupos", array);
		System.out.println(objToSend.toString());
		usuario.getWebSocket().send(objToSend.toString());
	}
	
	private void consultarProfesorPorMateria(WebSocket conn, JSONObject js) {
		String idMateria = js.getString("idMateria");
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		LinkedList<Profesor> profesores = profesorDao.listarTodos();
		LinkedList<Profesor> profesoresFiltrados= new LinkedList();
		for(Profesor profesor: profesores) {
			LinkedList<String> materias = profesor.getMaterias();
			for(String materia: materias) {
				if(materia.equals(idMateria)) {
					profesoresFiltrados.add(profesor);
				}
			}
		}
		
		String mensaje ="{" + 
				"	\"tipo\": \"lista profesores\"," + 
				"	\"profesores\": [";
		for(int i =0 ;i<profesoresFiltrados.size();i++) {
			mensaje+=profesoresFiltrados.get(i).toJSON();
			if(i<profesoresFiltrados.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]}";
		conn.send(mensaje);
	}

	private void consultarTutoriasPor(WebSocket conn, JSONObject js) {
		LinkedList<Tutoria> tutoriasFiltradas = new LinkedList();
		TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		LinkedList<Tutoria> tutorias = tutoriasDao.listarTodos();
		String idProfesor=js.getString("idProfesor");
		String idMateria= js.getString("idMateria");
		if(idProfesor.equals("")&&idMateria.equals("")) {
			this.consultarTutorias(conn, js);
			return;
		}else if(idProfesor.equals("")&&!idMateria.equals("")){
			for(Tutoria tutoria: tutorias) {
				if(tutoria.getIdMateria().equals(idMateria)) {
					tutoriasFiltradas.add(tutoria);
				}
			}
		}else if(!idProfesor.equals("")&&idMateria.equals("")) {
			for(Tutoria tutoria: tutorias) {
				if(tutoria.getProfesor().getId().equals(idProfesor)) {
					tutoriasFiltradas.add(tutoria);
				}
			}
		}else {
			for(Tutoria tutoria: tutorias) {
				if(tutoria.getProfesor().getId().equals(idProfesor) && tutoria.getIdMateria().equals(idMateria)) {
					tutoriasFiltradas.add(tutoria);
				}
			}
		}
		String mensaje ="{\"tipo\": \"lista tutorias\"}";
		JSONObject objToSend = new JSONObject(mensaje);
		JSONArray array = new JSONArray();
		for(Tutoria tutoria: tutoriasFiltradas) {
			JSONObject tutoriaJson = new JSONObject(tutoria.toJSON());
			tutoriaJson.put("materia", materiaDao.consultar(tutoria.getIdMateria()).toJSON());
			tutoriaJson.put("profesor", profesorDao.consultar(tutoria.getProfesor().getId()).toJSON());
			tutoriaJson.put("inscritos", tutoria.getUsuarios().size());
			tutoriaJson.put("unido", Buscador.estaUsuarioEnTutoria(Buscador.buscarUsuario(conn, usuarios), tutoria));
			array.put(tutoriaJson);
		}
		objToSend.put("tutorias", array);
		System.out.println(objToSend.toString());
		conn.send(objToSend.toString());
	}
	private void consultarMaterias(WebSocket conn, JSONObject js) {
		MateriaDao materiaDao = MateriaDao.getInstancia();
		LinkedList<Materia> materias=materiaDao.listarTodos();
		String mensaje ="{" + 
				"	\"tipo\": \"lista materias\"," + 
				"	\"materias\": [";
		for (int i = 0; i < materias.size(); i++) {
			mensaje+=materias.get(i).toJSON();
			if(i<materias.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]}";
		conn.send(mensaje);	
	}

	private void consultarEscuelas(WebSocket conn, JSONObject js) {
		EscuelasDao escuelasDao = EscuelasDao.getInstancia();
		LinkedList<Escuela> escuelas = escuelasDao.listarTodos();
		String mensaje ="{" + 
				"	\"tipo\": \"lista escuelas\"," + 
				"	\"escuelas\": [";
		for (int i = 0; i < escuelas.size(); i++) {
			mensaje+=escuelas.get(i).toJSON();
			if(i<escuelas.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]}";
		conn.send(mensaje);
	}

	private void consultarToken(WebSocket conn, JSONObject js) {
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		Usuario usuario = usuariosDao.consultarPorToken(js.getString("token"));
		String mensaje = "{" + "	\"tipo\": \"usuario carga\"," + "	\"mensaje\": " + usuario.toJSON() + "}";
		conn.send(mensaje);
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
		mensaje+="]}";
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
			tutoria.setIdMateria(js.getString("idMateria"));
			String idTutoria= Generador.generarId();
			tutoria.setIdTutoria(idTutoria);
			if(tutoriasDao.crear(tutoria)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Tutoría creada\", " +
						"\"idTutoria\": \""+tutoria.getIdTutoria()+"\""+
						"}";
			}
		}
		conn.send(mensaje);
			
	}

	private void apagarServidor(WebSocket conn, JSONObject js) {
		LinkedList<Usuario> usuarios = UsuariosDao.getInstancia().listarTodos();
		for(Usuario usuario: usuarios) {
			if(usuario.getWebSocket()!=null) {
				usuario.getWebSocket().close();
				usuario.setWebSocket(null);
			}		
		}
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
	/**
	 * Método que permite eliminar una tutoria
	 * @param conn que es el webSocket del usuario
	 * @param js que es el objeto json entrante
	 */
	private void eliminarTutoria(WebSocket conn, JSONObject js) {
		TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
		Tutoria tutorias = tutoriasDao.consultar(js.getString("idTutoria"));
		String mensaje = "";
		if(tutorias!=null) {
			if(tutoriasDao.eliminar(tutorias)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Tutoria eliminada\"" +
						"}";
			}else {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Error al eliminar tutoria\"" + 
						"}";
			}
				
		}else {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa tutoria no existe\"" + 
					"}";
		}
		conn.send(mensaje);
	}

	private void cancelarTutoria(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}
	/**
	 * Método que permite eliminar una publicación
	 * @param conn que es el websocket del usuario que elimina la publicación
	 * @param js que es el objeto json entrante
	 */
	private void eliminarPublicacion(WebSocket conn, JSONObject js) {
		PublicacionDao publicacionDao = PublicacionDao.getInstancia();
		Publicacion publicacion = publicacionDao.consultar(js.getString("idPublicacion"));
		String mensaje = "";
		if(publicacion!=null) {
			if(publicacionDao.eliminar(publicacion)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Publicacion eliminada\"" +
						"}";
			}else {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Error al eliminar publicacion\"" + 
						"}";
			}
				
		}else {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa publicacion no existe\"" + 
					"}";
		}
		conn.send(mensaje);


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
	/**
	 * Método que permite editar una publicación
	 * @param conn que es el websocket del usuario que edita la publicación
	 * @param js que es el objeto json entrante
	 */
	private void editarPublicacion(WebSocket conn, JSONObject js) {
		String mensaje="";
		PublicacionDao publicacionDao = PublicacionDao.getInstancia();
		Publicacion publicacion= publicacionDao.consultar(js.getString("idPublicacion"));
		publicacion.setContenido(js.getString("contenido"));
		if(publicacionDao.actualizar(publicacion)) {
			mensaje ="{" + 
					"\"tipo\": \"ok\"," + 
					"\"mensaje\": \"Publicación actualizada\"" +
					"}";
		}else {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Error al actualizar\"" + 
					"}";
		}
		conn.send(mensaje);

	}
	/**
	 * Método que se encarga de consultar todos los snacks
	 * @param conn que es el webSocket del usuario
	 * @param js objeto json entrante
	 */
	private void consultarSnacks(WebSocket conn, JSONObject js) {
		SnackDao snackDao = SnackDao.getInstancia();
		CategoriaSnackDao categoriaSnackDao= CategoriaSnackDao.getInstancia();
		LinkedList <Snack> snacks = snackDao.listarTodos();
		String mensaje ="{\"tipo\": \"lista snacks\"}";
		JSONObject objToSend = new JSONObject(mensaje);
		JSONArray array = new JSONArray();
		for (Snack snack: snacks) {
			JSONObject snackJson = new JSONObject(snack.toJSON());
			snackJson.put("categoria", categoriaSnackDao.consultar(snack.getIdCategoria()).toJSON());
			array.put(snackJson);
		}
		objToSend.put("snacks", array);
		conn.send(objToSend.toString());
	}
	
	/**
	 * Método que se encarga de consultar todos los grupos de un usuario y verifica si el usuario está o no en ellos
	 * @param conn que es el webSocket del usuario
	 * @param js objeto json entrante
	 */
	private void consultarGrupos(WebSocket conn, JSONObject js) {
		GruposEstudioDao grupoEstudioDao = GruposEstudioDao.getInstancia();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		Usuario usuario = Buscador.buscarUsuario(conn, usuarios);
		LinkedList <GrupoEstudio> gruposEstudio = grupoEstudioDao.listarTodos();
		String mensaje ="{\"tipo\": \"lista grupos\"}";
		JSONObject objToSend = new JSONObject(mensaje);
		JSONArray array = new JSONArray();
		for(GrupoEstudio grupoEstudio: gruposEstudio) {
			JSONObject grupo = new JSONObject(grupoEstudio.toJSON());
			Materia materia = materiaDao.consultar(grupoEstudio.getIdMateria());
			if(!(js.get("idEscuela")instanceof String)) {
				String stringMateria = materia.toJSON();
				grupo.put("materia", materia.toJSON());
				array.put(grupo);
				grupo.put("unido", Buscador.estaUsuarioEnGrupo(usuario,grupoEstudio));
			}else {
				if(!(js.get("idMateria") instanceof String)) {
					if(materia.getIdEscuela().equals(js.getString("idEscuela"))) {
						String stringMateria = materia.toJSON();
						grupo.put("materia", materia.toJSON());
						array.put(grupo);
						grupo.put("unido", Buscador.estaUsuarioEnGrupo(usuario,grupoEstudio));
					}
				}else {
					if(js.getString("idMateria").equals(grupoEstudio.getIdMateria())) {
						String stringMateria = materia.toJSON();
						grupo.put("materia", materia.toJSON());
						array.put(grupo);
						grupo.put("unido", Buscador.estaUsuarioEnGrupo(usuario,grupoEstudio));
					}
				}
			}
			
		}
		objToSend.put("grupos", array);
		System.out.println(objToSend.toString());
		usuario.getWebSocket().send(objToSend.toString());
	}

	private void consultarProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub

	}

	private void consultarProfesores(WebSocket conn, JSONObject js) {
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		LinkedList <Profesor> profesores = profesorDao.listarTodos();
		String mensaje ="{" + 
				"	\"tipo\": \"lista profesores\"," + 
				"	\"profesores\": [";
		for (int i = 0; i < profesores.size(); i++) {
			mensaje +=profesores.get(i).toJSON();
			if(i<profesores.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]}";
		conn.send(mensaje);
		
	}

	private void consultarPublicaciones(WebSocket conn, JSONObject js) {
		PublicacionDao publicacionesDao= PublicacionDao.getInstancia();
		LinkedList <Publicacion> publicaciones =publicacionesDao.listarTodos();
		LinkedList <Publicacion> publicacionesFiltradas = new LinkedList();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		for(Publicacion publicacion: publicaciones) {
			if(publicacion.getIdMateria().equals(String.valueOf(js.get("idMateria")))) {
				publicacionesFiltradas.add(publicacion);
			}
		}
		String mensaje ="{"
				+ "\"materia\":" +materiaDao.consultar(String.valueOf(js.get("idMateria"))).toJSON()+", "
				+ "\"tipo\": \"lista publicaciones\","
				+ "\"publicaciones\": [";
		for (int i = 0; i < publicacionesFiltradas.size(); i++) {
			if(String.valueOf(js.get("idMateria")).equals(publicacionesFiltradas.get(i).getIdMateria())) {
				mensaje +=publicacionesFiltradas.get(i).toJSON();
				if(i<publicacionesFiltradas.size()-1) {
					mensaje+=", ";
				}
			}
		}
		mensaje+="]}";
		conn.send(mensaje);
	}

	private void consultarTutorias(WebSocket conn, JSONObject js) {
		TutoriasDao tutoriasDao = TutoriasDao.getInstancia();
		LinkedList<Tutoria> tutorias = tutoriasDao.listarTodos();
		String mensaje ="{" + 
				"	\"tipo\": \"lista tutorias\"," + 
				"	\"tutorias\": [";
		for(int i =0; i<tutorias.size();i++) {
			mensaje+=tutorias.get(i).toJSON();
			if(i<tutorias.size()-1) {
				mensaje+=", ";
			}
		}
		mensaje+="]}";
		conn.send(mensaje);

	}

	private void agregarHorarioProfesor(WebSocket conn, JSONObject js) {
		// TODO Auto-generated method stub
		ProfesorDao profesorDao = ProfesorDao.getInstancia();
		Profesor profesor = profesorDao.consultar(js.getString("idProfesor"));
		if(profesor.getHorarios()==null) {
			profesor.setHorarios(new LinkedList());
		}
		profesor.getHorarios().add(js.getString("hora"));
		String mensaje= "{" + 
				"\"tipo\": \"ok\"," + 
				"\"mensaje\": \"Horario asignado\"" +
				"}";
		conn.send(mensaje);
	}

	private void crearPublicacion(WebSocket conn, JSONObject js) {
		String mensaje="";
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		PublicacionDao publicacionDao = PublicacionDao.getInstancia();
		MateriaDao materiaDao = MateriaDao.getInstancia();
		Usuario usuario = usuariosDao.consultar(js.getString("idUsuario"));
		Materia materia = materiaDao.consultar(js.getString("idMateria"));
		if(usuario==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese usuario no existe\"" + 
					"}";
		}else if (materia==null){
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese usuario no existe\"" + 
					"}";
		}else {
			Publicacion publicacion = new Publicacion();
			publicacion.setIdMateria(materia.getIdMateria());
			publicacion.setUsuario(usuario.getUsuario());
			publicacion.setContenido(js.getString("contenido"));
			publicacion.setTitulo(js.getString("titulo"));
			String idPublicacion=Generador.generarId();
			publicacion.setIdPublicacion(idPublicacion);
			if(publicacionDao.crear(publicacion)) {
				mensaje= "{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Publicación creada\"" +
						"}";
			}
		}
		conn.send(mensaje);
	}

	private void ingresarAGrupo(WebSocket conn, JSONObject js) {
		String mensaje="";
		UsuariosDao usuariosDao = UsuariosDao.getInstancia();
		GruposEstudioDao gruposEstudioDao = GruposEstudioDao.getInstancia();
		Usuario usuario = usuariosDao.consultar(js.getString("idUsuario"));
		GrupoEstudio grupoEstudio = gruposEstudioDao.consultar(js.getString("idGrupo"));
		if(usuario==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese usuario no existe\"" + 
					"}";
		}else if(grupoEstudio==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese grupo no existe\"" + 
					"}";
		}else {
			grupoEstudio.getUsuarios().add(usuario.getUsuario());
			mensaje ="{" + 
					"\"tipo\": \"ok\"," + 
					"\"mensaje\": \"Se ha unido al grupo de estudio "+grupoEstudio.getNombreGrupo()+"\"" +
					"}";
		}
		conn.send(mensaje);
	}
	private void crearEscuela(WebSocket conn, JSONObject js) {
		EscuelasDao escuelaDao = EscuelasDao.getInstancia();
		Escuela escuela = new Escuela();
		String idEscuela =Generador.generarId();
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
						"\"mensaje\": \"Escuela creada\", " +
						"\"idEscuela\": \""+escuela.getIdEscuela()+"\""+
						"}";
			}
		}
		conn.send(mensaje);

	}

	private void crearSnack(WebSocket conn, JSONObject js) {
		SnackDao snackDao = SnackDao.getInstancia();
		CategoriaSnackDao categoriaSnackDao = CategoriaSnackDao.getInstancia();
		String mensaje ="";
		Snack snack = new Snack();
		snack.setNombreSnack(js.getString("nombreSnack"));
		snack.setPrecio(js.getFloat("precio"));
		snack.setImagen(js.getString("imagen"));
		snack.setIdCategoria(js.getString("idCategoria"));
		String idSnack = Generador.generarId();
		snack.setIdSnack(idSnack);
		if(categoriaSnackDao.consultar(js.getString("idCategoria"))==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa categoria no existe\"" + 
					"}";
		}else {
			if(snackDao.crear(snack)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\", " + 
						"\"mensaje\": \"Snack creado\", " +
						"\"idSnack\": \""+snack.getIdSnack()+"\""+
						"}";
			}
		}
		
		conn.send(mensaje);
	}

	private void crearCategoriaSnack(WebSocket conn, JSONObject js) {
		CategoriaSnackDao categoriaSnackDao = CategoriaSnackDao.getInstancia();
		CategoriaSnack categoria = new CategoriaSnack();
		String idCategoria=Generador.generarId();
		System.out.println(idCategoria);
		categoria.setIdCategoria(idCategoria);
		categoria.setNombreCategoria(js.getString("nombreCategoria"));
		String mensaje="";
		if(categoriaSnackDao.consultarPorNombre(categoria.getNombreCategoria())!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\", " + 
					"\"mensaje\": \"Esa categoria ya existe\"" + 
					"}";
		}else {
			if(categoriaSnackDao.crear(categoria)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\", " + 
						"\"mensaje\": \"Categoría creada\", " +
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
		Tutoria tutoria=tutoriasDao.consultar(String.valueOf(js.get("idTutoria")));
		String mensaje="";
		if(tutoria==null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Esa tutoría no existe\"" + 
					"}";
		}else {
			Usuario usuario = usuariosDao.consultar(js.getString("idUsuario"));
			if(usuario==null) {
				mensaje ="{" + 
						"\"tipo\": \"error\"," + 
						"\"mensaje\": \"Ese usuario no existe\"" + 
						"}";
			}else {
				if(tutoria.getUsuarios().add(usuario.getUsuario())) {
					mensaje= "{" + 
							"\"tipo\": \"ok\", " + 
							"\"mensaje\": \"Ha ingresado a la tutoría\"" + 
							"}";
				}
			}
			
		}
		conn.send(mensaje);

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
							"\"tipo\": \"ok\", " + 
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
		Profesor profesor = profesorDao.consultarPorNombre(js.getString("nombreProfesor"));
		String mensaje = "";
		if(profesor!=null) {
			mensaje ="{" + 
					"\"tipo\": \"error\"," + 
					"\"mensaje\": \"Ese profesor ya existe\"" + 
					"}";
		}else {
			profesor = new Profesor();
			String idProfesor = Generador.generarId();
			profesor.setId(idProfesor);
			profesor.setNombre(js.getString("nombreProfesor"));
			profesor.setMaterias(new LinkedList());
			if(profesorDao.crear(profesor)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\"," + 
						"\"mensaje\": \"Profesor creado\", " +
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
		grupo.setNombreGrupo(js.getString("nombreGrupo"));
		String idGrupo =Generador.generarId();
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
						"\"mensaje\": \"Grupo creado\", " +
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
			String idMateria = Generador.generarId();
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
							"\"mensaje\": \"Materia creada\", " +
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
			usuario.setToken(usuario.getUsuario()+Generador.generarId());
			if(usuariosDao.crear(usuario)) {
				mensaje ="{" + 
						"\"tipo\": \"ok\", " + 
						"\"mensaje\": \"Usuario creado\", " +
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
			mensaje = "{" + "	\"tipo\": \"error\"," + "	\"mensaje\": \"usuario o contraseña inválidos\"" + "}";
		} else {
			if (usuario.getContraseña().equals(js.getString("contraseña"))) {
				System.out.println("Entré a login");
				mensaje = "{" + "	\"tipo\": \"usuario\"," + "	\"mensaje\": " + usuario.toJSON() + "}";
				usuario.setWebSocket(conn);
				usuario.setDireccion(conn.getRemoteSocketAddress());
			} else {
				mensaje = "{" + "	\"tipo\": \"error\"," + "	\"mensaje\": \"usuario o contraseña inválidos\"" + "}";
			}
		}
		usuarios.add(usuario);
		conn.send(mensaje);
		
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		if(code==4000) {
			Usuario usuario = Buscador.buscarUsuario(conn, usuarios);
			usuario.setMotivoDesconexion(reason);
		}else if (!reason.equals("cambio")) {
			Usuario usuario = Buscador.buscarUsuario(conn, usuarios);
			if(usuario!=null) {
				this.usuarios.remove(usuario);
				usuario.setWebSocket(null);
			}
		}
		System.out.println("Client disconnected: " + reason+" "+code);
	}

	@Override
	public void onError(WebSocket conn, Exception exc) {
		System.out.println("Error happened: " + exc.getMessage());
		exc.printStackTrace();
	}

	public void sendToAll(WebSocket conn, String message) {

		for (int i = 0; i < usuarios.size(); i++) {
			WebSocket c = (WebSocket) usuarios.get(i).getWebSocket();
			if (c != conn)
				c.send(message);
		}
	}

}
