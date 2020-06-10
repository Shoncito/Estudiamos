$("#crearMateria").click(function(evt){
    evt.preventDefault();
    var idEscuela = $("#escuelas").val();
    var nombreMateria = $("#nombreMateria").val();
    var obj={
        tipo: "crear materia",
        idEscuela : idEscuela,
        nombreMateria : nombreMateria
    }
    enviarMensaje(obj);
});
