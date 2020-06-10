$("#reg").click(function(evt){
    evt.preventDefault();
    var nombreGrupo = $("#nombreGru").val();
    var idMateria = $("#selectMateria").val();
    var tema = $("#tema").val();
    var fecha = $("#fecha").val();
    var hora = $("#hora").val();
    var lugar = $("#lugar").val();
    var obj={
        tipo: "crear grupo",
        nombreGrupo: nombreGrupo,
        idMateria: idMateria,
        tema: tema,
        fecha: fecha,
        hora: hora,
        lugar: lugar,
        idUsuario: usuario.usuario
    }
    enviarMensaje(obj);
});