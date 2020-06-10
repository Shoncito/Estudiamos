$("#asociarMP").click(function(evt){
	evt.preventDefault();
	var idProfesor = $("#profesores").val();
	var idMateria = $("#materias").val();
	var obj={
		tipo: "asociar materia",
		idProfesor: idProfesor,
		idMateria: idMateria
	}
	enviarMensaje(obj);
});