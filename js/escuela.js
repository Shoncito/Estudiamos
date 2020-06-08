$("#crearEscuela").click(function(evt){
    evt.preventDefault();
    var nombreEscuela = $("#nombreEscuela").val();
    var obj={
        tipo: "crear escuela",
        nombreEscuela: nombreEscuela
    }
    enviarMensaje(obj);
});