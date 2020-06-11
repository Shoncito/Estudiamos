var categorias;

/**
* Promesa
* @param {*} ms 
*/
var wait = ms => new Promise((r, j) => setTimeout(r, ms));
/**
* Dirección con protocolo ws
*/
var wsUri = "ws://25.143.152.254:8080";
/**
* Websocket
*/
var websocket = new WebSocket(wsUri);

var usuario;

var sonido=true;

var idEscuela=0;

var num=0;

var idMateria;

var a=false;

var b=false;

/**
* Cuando se abre la conexión
*/
websocket.onopen = function(event){
	console.log("Conectado..."); //... y aparecerá en la pantalla
	ping();
	if(document.title==="Crear snack"){
		var obj ={
			tipo: "consultar categorias" 
		}
		enviarMensaje(obj);
	}else if(document.title==="Crear materia" || document.title==="Crear grupo"){
		var obj ={
			tipo: "consultar escuelas" 
		}
		enviarMensaje(obj);
	}else if(document.title==="Estudiamos - Tutorias"){
		var obj ={
			tipo: "consultar escuelas" 
		}
		enviarMensaje(obj);
	}else if(document.title==="Estudiamos - Foro"){
		var obj ={
			tipo: "consultar escuelas" 
		}
		enviarMensaje(obj);
		obj.tipo="consultar materias";
		enviarMensaje(obj);
	}else if (document.title==="Crear Tutoria") {
		var obj ={
			tipo: "consultar escuelas" 
		}
		enviarMensaje(obj);
	}else if (document.title==="Estudiamos unirse grupos") {
		var obj ={
			tipo: "consultar escuelas" 
		}
		enviarMensaje(obj);
	}
	
}
/**
* Cuando el servidor envía un mensaje
*/
websocket.onmessage=function(event){
	
	if (event.data === "pong") {
		ping();
		console.log(event.data);
	} else {
		var obj = JSON.parse(event.data);
		console.log(obj);
		if(obj.tipo==="error"){
			alert("Error: "+obj.mensaje);
		}
		else if(obj.tipo ==="usuario"){
			setCookie("token",obj.mensaje.token,0);
			usuario = obj.mensaje;
			websocket.close(1000,"cambio");
			window.location.assign("inicio.html");
			//Se supone que aquí se accede a la página
		}
		else if(obj.tipo==="ok"){
			alert(JSON.stringify(obj));
		}
		else if(obj.tipo==="notificacion"){ 
			notificar(obj.subtipo,obj.mensaje);
		}
		else if(obj.tipo ==="usuario carga"){
			usuario = obj.mensaje;

			$("#nombreUser").append(usuario.usuario);
		}else if(obj.tipo==="lista categorias"){
			if(document.title==="Estudiamos - Snacks"){
				pintarCategorias(obj.categorias);
			}
			else if(document.title==="Crear snack"){
				console.log(obj);
				colocarEnSelect(obj.categorias);
			}
			categorias = obj.categorias;
		}else if(obj.tipo==="lista escuelas"){
			if(document.title==="Crear grupo" || document.title==="Estudiamos - Tutorias" || document.title==="Crear Tutoria"  || document.title=="Crear materia" || document.title==="Estudiamos unirse grupos"){
				colocarEnSelectEscuelas(obj.escuelas);
			}else if(document.title==="Estudiamos - Foro"){
				cargarSeccionesEscuelasForo(obj.escuelas);
			}
			
		}else if(obj.tipo==="lista profesores"){
			if (document.title==="Crear Tutoria"||document.title==="Estudiamos - Tutorias" ) {
				filtrarProfesores(obj.profesores);
			}
		}else if(obj.tipo==="lista tutorias"){

		}else if(obj.tipo==="lista grupos"){
			if(document.title==="Estudiamos - Buscar grupo de estudio"){
				pintarGrupos(obj.grupos);
			}
			
		}else if(obj.tipo==="materia de publicacion"){
			  guardarMateria(obj.materia);
		}else if(obj.tipo==="lista snacks"){

		}else if(obj.tipo==="lista publicaciones"){
			if(document.title==="Foro publicaciones"){
				pintarPublicaciones(obj);
				idMateria=obj.materia.idMateria;
			}
		}else if(obj.tipo==="mis grupos"){
			misgrupos(obj.grupos);
		}else if(obj.tipo==="lista escuelas"){

		}else if(obj.tipo==="lista materias"){
			if(document.title==="Estudiamos - Tutorias" || document.title==="Crear grupo" || document.title==="Crear Tutoria" || document.title==="Estudiamos unirse grupos"){
				filtarMaterias(obj.materias);
				console.log(obj.materias);
			}else if(document.title==="Estudiamos - Foro"){
				listarMaterias(obj.materias);	
			}
				
		}
	}
}
;
/**
 * 
 * @param {obj} materia 
 */
function guardarMateria(materia){
idMateria=materia.idMateria;
}


/**
 * pintar publicaciones
 * @param {array} publicaciones 
 */
function pintarPublicaciones(obj){
	var text="";
	text +=`	
    <thead>
	<tr>
	<th align="center">${obj.materia.nombreMateria}</th>  
	</tr>
	</thead>`
	$("#tabla3").append(text);
	text="";
for(var i=0;i<obj.publicaciones.length;i++){
	text +=` 
	<tr class="carril">
	<td>
	${obj.publicaciones[i].titulo}
	<button id="${i}"  class="ui right labeled icon button" onclick="enviarIdPubli(${obj.publicaciones[i].idPublicacion})">  
	<i class="right arrow icon"></i>
	<font style="vertical-align: inherit;">
		<font style="vertical-align: inherit;">
	Mas
	</font>
</font>
</button>	
	<div id="" class="materias" >
	<table id="${obj.publicaciones[i].idPublicacion}">
	<thead id="materia">
	<tr>
	<th>${obj.publicaciones[i].contenido}</th>
	</tr>
	</thead>
	</table>	
	</div>
	</td>
	</tr>`
		$("#tabla3").append(text);
		text="";
		
	}

}

/**
 * 
 * @param {array} grupos 
 */
function pintarGrupos(grupos){
var text="";
for(var i=0;i<grupos.length;i++){
	if(!grupos[i].unido){
		text +=`
		<div class="ui link card grupo">
          <div class="content grupo">
            <div class="header grupo">
              ${grupos[i].nombreGrupo}
            </div>
            <div class="meta grupo">
			${JSON.parse(grupos[i].materia).nombreMateria}
            </div>
            <div class="description">
			${grupos[i].tema}
            </div>
          </div>
		  <div class="extra content">
		  Lugar: ${grupos[i].lugar} <br>
		  Fecha: ${grupos[i].fecha} <br>
		  Hora: ${grupos[i].hora}:00 <br>
        <div  class="ui basic orange button" id="${grupos[i].idGrupo}" onclick="enviarIdgrupo(${grupos[i].idGrupo})">Unirse</div>
        </div>
        </div>
		` 
		$("#divGruposB").append(text);
		text="";
	}else{
		text +=`
        <div class="ui link card grupo">
          <div class="content grupo">
            <div class="header grupo">
			${grupos[i].nombreGrupo}
            </div>
            <div class="meta grupo">
			${JSON.parse(grupos[i].materia).nombreMateria}
            </div>
            <div class="description">
			${grupos[i].tema}
            </div>
          </div>
		  <div class="extra content ">

		  Lugar: ${grupos[i].lugar} <br>
		  Fecha: ${grupos[i].fecha} <br>
		  Hora: ${grupos[i].hora}:00 <br>
            <div class="ui orange button">Unido</div>
          </div>
        </div>`
		$("#divGruposB").append(text);
		text="";
	}
}
}
/**
 * idGrupos y envia al momento de unirse a grupo
 * @param {id} idGrupos 
 */
function enviarIdgrupo(idGrupo){
	idGrupo=idGrupo.toString();
	var obj={
		tipo: "ingresar a grupo",
		idUsuario: usuario.usuario,
		idGrupo: idGrupo
	}
	enviarMensaje(obj);
}


/**
 * 
 * @param {array} grupos 
 */
 function misgrupos(grupos){
 	var text="";
 	for(var i=0;i<grupos.length;i++){
 		text +=`<div class="card">
 		<div class="content">
 		<div class="header grupo">
 		${grupos[i].nombreGrupo}
 		</div>
 		<div class="meta grupo">
 		${JSON.parse(grupos[i].materia).nombreMateria}
 		</div>
 		<div class="description">
 		${grupos[i].tema}
 		</div>
 		</div>
 		<div class="extra content">
 		${grupos[i].hora}
 		<br>  
 		${grupos[i].fecha}
 		<br>
 		${grupos[i].lugar}
 		</div>
 		</div>`
 		$("#divGrupos").append(text);
 		text="";
 	}
 }

/**
* Cuando el websocket se cierra
*/
websocket.onclose=function(event){
	console.log("conexión terminada");
}

/**
* Cuando el websocket presenta un error
*/
websocket.onerror=function(event){
	alert("Error de conexión "+event.data);
}
/**
 * 
 * @param {array} materias 
 */
 function listarMaterias(materias){
 	var text="";
 	for(var i=0;i<materias.length;i++){
 		text +=`
		 <tr class="carril" id="${materias[i].idMateria}">
 		<td onclick="public(${materias[i].idMateria})">${materias[i].nombreMateria}</td>
 		</tr>`;
 		$("#"+materias[i].idEscuela).append(text);
 		text="";
 	}
 }
/**
 * envar id materia foro seleccionada y cambio a foropubli
 */
function public(idMateria){
    var obj={
        tipo: "consultar publicaciones",
        idMateria: idMateria
    }
    websocket.close(4000,JSON.stringify(obj));
    console.log(websocket);
    window.location.assign("foropubli.html");
}



 function filtarMaterias(materias){
 	var materiasFil=new Array();

 	for(var i=0;i<materias.length;i++){
 		if(idEscuela===materias[i].idEscuela){
 			materiasFil.push(materias[i]);
 		}
 	}
 	$("#selectMateria").empty();
 	var text=`<option value="">Seleccione una materia</option>`;
 	for(var j=0;j<materiasFil.length;j++){
 		text +=`<option value="${materiasFil[j].idMateria}">${materiasFil[j].nombreMateria}</option>`
 	}
 	$("#selectMateria").append(text);
 }

/**
 * 
 * @param {array} escuelas 
 */
 function cargarSeccionesEscuelasForo(escuelas){

 	var texto="";
 	for(var i=0;i<escuelas.length;i++){
 		texto +=`
 		<tr class="carril">
 		<td>
 		${escuelas[i].nombreEscuela}
 		<button id="${i}"  class="ui right labeled icon button " onclick="enviarId(${escuelas[i].idEscuela})">
 		<i class="right arrow icon"></i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 		Mas
 		</font></font></button>	
 		<div id="${escuelas[i].idEscuela}+1" class="materias" >
 		<table id="${escuelas[i].idEscuela}">
 		<thead id="materia">
 		<tr>
 		<th>MATERIAS</th>
 		</tr>
 		</thead>

 		</table>	


 		</div>
 		</td>
 		</tr>
 		` 
 		$("#tabla2").append(texto);
		//$("#"+escuelas[i].idEscuela).toggle("slow");
		texto="";
		$("#"+escuelas[i].idEscuela).hide();
	}
	var obj ={
		tipo: "consultar materias" 
	}
	enviarMensaje(obj);

}
/**
 * Se envia la id cuando da click en el boton para mostrar las materias de cada escuela en foro.html
 * @param {id} escuelas 
 */
 function enviarId(escuelas){
	 
    if(!a){
		a=true;
	console.log("sirve")
	$("#"+escuelas).show();
	}else{
		a=false;
		console.log("sirve")
		$("#"+escuelas).hide();
	}
 }

/**
 * 
 * @param {id} id
 */
 function enviarIdPubli(id){
    if(!b){
		b=true;
	console.log("sirve")
	$("#"+id).show();
	}else{
		b=false;
		console.log("sirve")
		$("#"+id).hide();
	}
 }

 $(".ui.right.labeled.icon.button").click(function() {
 	console.log("boton sirve");
 	$("#"+escuelas[$(this).id].idEscuela+"-"+$(this).id).toggle("slow");
 });
/**
 * 
 * @param {array} escuelas 
 */
 function colocarEnSelectEscuelas(escuelas){
 	var text="";
 	for(var i=0;i<escuelas.length;i++){
 		text +=`
 		<option value="${escuelas[i].idEscuela}">${escuelas[i].nombreEscuela}</option>` 
 	}
 	$("#escuelas").append(text);
 }

 $("#escuelas").click(function(){
 	idEscuela=$(this).val();
 	var obj ={
 		tipo: "consultar materias" 
 	}
 	enviarMensaje(obj);
 });



 $("#selectMateria").click(function(){
	 if(document.title==="Estudiamos - Tutorias" || document.title==="Crear Tutoria"){
		idMateria=$(this).val();
		var obj ={
			tipo: "consultar profesor por materia",
			idMateria: idMateria
		}
		enviarMensaje(obj);
	 }
	 else if(document.title==="Estudiamos unirse grupos"){
		var obj={
			tipo: "consultar escuelas",
		}
		enviarMensaje(obj);	 
	}
 	
 });


 function filtrarProfesores(profesores){
 	$("#profesores").empty();
 	var text=`<option value="">Seleccione un profesor</option>`;
 	for(var j=0;j<profesores.length;j++){
 		text +=`<option value="${profesores[j].idProfesor}">${profesores[j].nombre}</option>`
 	}
 	$("#profesores").append(text);
 }



/**
* Pong
*/
function ping(){
	myPing = {tipo: "ping",message: "heartbeating"};
	var prom = wait(28000)  // prom, is a promise
	var showdone = () => enviarMensaje(myPing);
	prom.then(showdone)
}



/**
 * Función que envía un mensaje al servidor
 * @param {*} object 
 */
 function enviarMensaje(object){
 	var stringObject = JSON.stringify(object);
 	websocket.send(stringObject);
 	console.log("Enviando: "+stringObject);
 }

/**
 * Función que envía una notificación
 * @param {String} subtipo que es el título de la notificación Pomodoro o tutoría
 * @param {String} mensaje que es el mensaje
 */
 function enviarNotificacion(subtipo, mensaje){
 	notificar(subtipo,mensaje);
 }
/**
 * 
 * @param {Array} lista
 */
 function pintarCategorias(lista){
 	for (let i = 0; i < lista.length; i++) {
 		var texto = '<div class="CategoriaSnack">'+
 		'<img src="img/Snacks/'+lista[i].nombreCategoria+'.png">'+
 		'<p>'+lista[i].nombreCategoria+'</p>'+
 		'</div>';
 		$("#menuSnacks").append(texto);
 	}
 }
/**
 * 
 * @param {Array} lista 
 */
 function colocarEnSelect(lista){
 	for (let i = 0; i < lista.length; i++) {
 		var texto= '<option value="'+lista[i].idCategoria+'" >'
 		+lista[i].nombreCategoria+
 		'</option>';
 		$("select").append(texto);
 	}

 }