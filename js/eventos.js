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
})