/**
* Promesa
* @param {*} ms 
*/
var wait = ms => new Promise((r, j) => setTimeout(r, ms));
/**
* Dirección con protocolo ws
*/
var wsUri = "ws://localhost:8080";
/**
* Websocket
*/
var websocket = new WebSocket(wsUri);


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
		if(obj.tipo==="notificacion"){
			//Se recibe el hash y se guarda en una cookie. 
			enviarNotificacion(obj.subtipo,obj.mensaje);
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
*/

function enviarMensaje(object){
	var stringObject = JSON.stringify(object);
	websocket.send(stringObject);
	console.log("Enviando: "+stringObject);
}

/**
 * 
 * @param {String} subtipo 
 * @param {String} mensaje 
 */
function enviarNotificacion(subtipo, mensaje){

}