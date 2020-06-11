  $("#pub").click(function(evt){
    evt.preventDefault();
    var titulo = $("#titulop").val();
    var contenido = $("#contp").val();
    var obj={
        tipo: "crear publicación",
        idUsuario: usuario.usuario,
        titulo: titulo,
        contenido: contenido,
        idMateria: idMateria
    }
    enviarMensaje(obj);
});