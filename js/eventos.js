/**
 * Al registrar un usuario
 */
$("#registro").click(function(evt){
    evt.preventDefault();
    var usuario = $("#nombreRegistro").val();
    var contraseña = $("#contraRegistro").val();
    var correo = $("#emailRegistro").val();
    var obj={
        tipo: "registro",
        usuario: usuario,
        contraseña: contraseña,
        correo: correo
    }
    enviarMensaje(obj);
});
/**
 * Al ingresar un usuario
 */
$("#login").click(function(evt){
    evt.preventDefault();
    var usuario = $("#nombreLogin").val();
    var contraseña = $("#contraLogin").val();
    var obj={
        tipo: "login",
        usuario: usuario,
        contraseña: contraseña
    }
    enviarMensaje(obj);
});
/**
 * Al cambiar a crear un grupo
 */
$("#menuCrear").click(function(){
    websocket.close(1000,"cambio");
    console.log(websocket);
	window.location.assign("creargrup.html");
});

$("#btnCrear").click(function(){
    websocket.close(1000,"cambio");
    console.log(websocket);
    window.location.assign("creargrup.html");
});
/**
 * Al unirse a un grupo
 */
$("#menuMisGrupos").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("misGrupos.html");
});

$("#btnMisGrupos").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("misGrupos.html");
});
/**
* Ver los grupos unidos
*/
$("#menuUnirse").click(function(){
    websocket.close(1000,"cambio");
    window.location.assign("nindex.html");
});

$("#btnUnirse").click(function(){
    websocket.close(1000,"cambio");
    window.location.assign("nindex.html");
});

/**
 * Al acceder al foro
 */
$("#menuForos").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("foro.html");
});

$("#btnForo").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("foro.html");
});
/**
 * Al acceder al menú de snacks
 */
$("#menuMenu").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("snacks.html");
});

$("#btnMenu").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("snacks.html");
});
/**
 * Al acceder al pedido
 */
$("#menuPedido").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("pedidoSnacks.html");
});

$("#btnPedido").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("pedidoSnacks.html");
});

$("#botonComprar").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("pedidoSnacks.html");
});
/**
 * Al inicio
 */
$("#menuInicio").click(function(){
    websocket.close(1000,"cambio");
	window.location.assign("inicio.html");
});


$("#btnCrearTuto").click(function(){
    websocket.close(1000,"cambio");
    window.location.assign("creartuto.html");
});



