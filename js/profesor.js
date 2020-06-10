$("#crearProfesor").click(function(evt){
    evt.preventDefault();
    var nombreProfesor = $("#nombreProfesor").val();
    var obj={
        tipo: "crear profesor",
        nombreProfesor: nombreProfesor
    }
    enviarMensaje(obj);
});