$("#buscarTutoria").click(function(evt){
    evt.preventDefault();
    var idMateria = $("#selectMateria").val();
    var idProfesor = $("#profesores").val();
    var obj={
        tipo: "consultar tutorias",
        idMateria: idMateria,
        idProfesor : idProfesor
    }
    enviarMensaje(obj);
});