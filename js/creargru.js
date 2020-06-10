$("#rc").click(function(evt){
    evt.preventDefault();
    var nombreGrupo = $("#nombreGru").val();
    var idMateria = $("#escuelas").val();
    var nombreMateria = $("#selectMateria").val();
    var tema = $("#tema").val();
    var fecha = $("#fecha").val();
    var hora = $("#hora").val();
    var obj={
        tipo: "crear grupo",
        nombreGrupo: nombreGrupo,
        idMateria: idMateria,
        tema: tema,
        fecha: fecha,
        hora: hora,
        lugar: lugar,
        idUsuario: id usuario
    }
    enviarMensaje(obj);
});