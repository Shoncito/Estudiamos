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

$('.toggle').click(function(){
    $('.formulario').animate({
        height: "toggle",
        'padding-top': 'toggle',
        'padding-bottom': 'toggle',
        opacity: 'toggle'
    }, "slow");
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
/*
var nombreProducto=["CocaCola", "Pepsi", "H2O", "Té", "Café"];
var precioProducto=["12.000", "5.000", "6.000", "3.000", "1.000"];
var imagenProducto=["img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg", "img/estudio.jpg"];

 var PRODUCTO = document.getElementsByName("PRODUCTO");
 for (i in nombreProducto){
	
 	PRODUCTO[i].innerHTML = `<div class="content"><div name="NOMBREPRODUCTO" class="header">${nombreProducto[i]}</div></div><img src="${imagenProducto[i]}"><div name="PRECIOPRODUCTO" class="meta">${precioProducto[i]}</div><div class="extra content ui large buttons"><button class="ui brown button" id="disminuir" onclick="carrito(this)" value="disminuir${i}">-</button><div id="p"><p id="contador${i}" value="">0</p></div><button class="ui orange button" id="aumentar" onclick="carrito(this)" value="aumentar${i}">+</button></div>`
}
*/

function carrito(boton){
	for(i in valor){
		var contador = document.getElementById("contador"+i).value;
		if (boton.value=='aumentar'+i) {
	    	if (valor[i]<30) {
	    		valor[i]++;
	    		total[i]=precio[i]*valor[i];
	    	}
	    }if(boton.value=='disminuir'+i){
	    	if (valor[i]>0) {
	    		valor[i]--
	    		total[i]=precio[i]*valor[i];
	    	}
	    }
	    document.getElementById("contador"+i).textContent = valor[i];
	    console.log(total[i]);
	}
}

$("#categoriaTodo").click(function(){
	$(".Paquetes").show();
	$(".Bebidas").show();
	$(".Dulces").show();
});

$("#categoriaPaquetes").click(function(){
	$(".Paquetes").show();
	$(".Bebidas").hide();
	$(".Dulces").hide();
});

$("#categoriaBebidas").click(function(){
	$(".Paquetes").hide();
	$(".Bebidas").show();
	$(".Dulces").hide();
});

$("#categoriaDulces").click(function(){
	$(".Paquetes").hide();
	$(".Bebidas").hide();
	$(".Dulces").show();
});