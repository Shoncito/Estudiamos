$(document).ready(function(){
 	$("#Login").click(function() {
		$("#g2").hide("slow");
		$("#g1").show("slow");
		$("#btn").css("left","0");
		$("#btn").css("right","40.99%");
	});
	$("#Register").click(function() {
		$("#g1").hide("slow");
		$("#g2").show("slow");
		$("#btn").css("right","0");
		$("#btn").css("left","40.99%");
	});
});

let menu = document.getElementById("menu");

function myFunction(x) {
  menu.classList.toggle("change-menu");
  x.classList.toggle("change");
  $("#divMenuVertical").toggle();
}

$('.special.cards .image').dimmer({
  	on: 'hover'
});

$(".CategoriaSnack").click(function(){
	$(".CategoriaSnack").each(function(){
		if($(this).hasClass("brillo")){
			$(this).removeClass("brillo");
		}
	});
	$(this).addClass("brillo");
});

$('.special.cards .image').dimmer({
  on: 'hover'
});

var valor=0;
function carrito(boton){
    var contador = document.getElementById("contador").value;
    if (boton.value=='aumentar') {
    	if (valor<30) {
    		valor++
    	}
    }else{
    	if (valor>0) {
    		valor--
    	}
    }
    document.getElementById("contador").textContent = valor;
}


var nombreProducto=["CocaCola", "Pepsi", "H2O", "Té", "Café"];
var precioProducto=["12.000", "5.000", "6.000", "3.000", "1.000"];
var imagenProducto=["img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg"];

 var PRODUCTO = document.getElementsByName("PRODUCTO");
 for (i in nombreProducto){
 PRODUCTO[i].innerHTML = '<div class="content"><div name="NOMBREPRODUCTO" class="header">'+nombreProducto[i]+' </div></div><img src="' +imagenProducto[i]+ '"><div name="PRECIOPRODUCTO" class="meta">' +precioProducto[i]+ '</div><div class="extra content ui large buttons"><button class="ui brown button" id="disminuir"'+i+' onclick="carrito(this)" value="disminuir">-</button><div id="p'+i+'"><p id="contador" value=""></p></div><button class="ui orange button" id="aumentar'+i+'" onclick="carrito(this)" value="aumentar">+</button></div>';
 }

