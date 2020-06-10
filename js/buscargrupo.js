$("#BuscarGrupo").click(function(evt){
    evt.preventDefault();
    var idMateria = $("#selectMateria").val();
    var obj={
        tipo: "crear grupo",
        idMateria: idMateria,
    }
    enviarMensaje(obj);
});
