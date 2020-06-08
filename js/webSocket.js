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
/**
* Cuando se abre la conexión
*/
websocket.onopen = function(event){
	console.log("Conectado..."); //... y aparecerá en la pantalla
	ping();
	
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
			//websocket.close(1000,"cambio");
			//window.location.assign("inicio.html");
			//Se supone que aquí se accede a la página
		}else if(obj.tipo==="lista categorias"){
			if(document.title==="Estudiamos - Snacks"){
				pintarCategorias(obj.categorias);
			}
			else if(document.title==="Crear snack"){
				colocarEnSelect(obj.categorias);
			}
			categorias = obj.categorias;
		}else if(obj.tipo===""){

		}
	}
}
;
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
						'<img src="img/Snacks/'+lista.nombreCategoria+'.png">'+
						'<p>'+lista.nombreCategoria+'</p>'+
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
		var texto= '<option value="'+lista[i].idCategoria+'" >'+
						+lista[i].nombreCategoria+
					'</option>';
		$("select").append(texto);
	}
	
}