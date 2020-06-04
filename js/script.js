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

