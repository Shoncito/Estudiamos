var texto = "" ;

$(document).ready(function(){
	agregar_materias();
 	$("#derecho").click(function() {
 		console.log("boton sirve");
		$("#mataria_derecho").toggle("slow");
	});
});
function agregar_materias(){
		texto = '<table id="tablamateria">'+
					'<thead id="materia">'+
						'<tr>'+
							'<th>MATERIAS</th>'+
						'</tr>'+
					'</thead>'+
					'<tr class="carril">'+
						'<td>ESCUELA MAYOR DE DERECHO</td>'+
					'</tr>'+
					'<tr class="carril">'+
						'<td>ESCUELA DE PUBLICIDAD</td>'+
					'</tr>'+
					'<tr class="carril">'+
						'<td>ESCUELA INTERNACIONAL DE ADMINISTRACIÓN Y MARKETING</td>'+
				'</table>';
				$("#mataria_derecho").append(texto);
}