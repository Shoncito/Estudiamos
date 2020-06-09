$("#crearSnack").click(function(evt){
    evt.preventDefault();
    var idCategoria = $("#select").val();
    var nombreSnack = $("#nombreSnack").val();
    var imagen = $("#imagenSnack").val();
    var precio = $("#precioSnack").val();
    var obj={
        tipo: "crear snack",
        idCategoria : idCategoria,
        nombreSnack : nombreSnack,
        imagen : imagen,
        precio : precio
    }
    enviarMensaje(obj);
});

