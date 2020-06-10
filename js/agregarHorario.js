$("#agregarHD").click(function(evt){
    evt.preventDefault();
    var hora = $("#hora").val();
    var idProfesor = $("#profesores").val();
    var obj={
        tipo: "agregar horario disponible",
        idProfesor : idProfesor,
        hora: hora
    }
    enviarMensaje(obj);
});