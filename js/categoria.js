$("#crearCategoria").click(function(evt){
    evt.preventDefault();
    var nombreCategoria = $("#nombreCategoria").val();
    var obj={
        tipo: "crear categoria snack",
        nombreCategoria: nombreCategoria
    }
    enviarMensaje(obj);
});