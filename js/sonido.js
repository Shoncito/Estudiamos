$(function(){
    $(document).bind("contextmenu",function(e){
        return false;
    });
});

var audioInicio = new Audio();
audioInicio.src = "sonido/Inicio.mp3";
var audioCrearGrupo = new Audio();
audioCrearGrupo.src = "sonido/Crear-grupo-de-estudio.mp3";
var audioForo = new Audio();
audioForo.src = "sonido/Foro.mp3";
var audioMenuSnacks = new Audio();
audioMenuSnacks.src = "sonido/Menu-snacks.mp3";
var audioPedidoSnacks = new Audio();
audioPedidoSnacks.src = "sonido/Mi-pedido-snacks.mp3";
var audioMisGrupos = new Audio();
audioMisGrupos.src = "sonido/Mis-grupos-de-estudio.mp3";
var audioSobreNosotros = new Audio();
audioSobreNosotros.src = "sonido/Sobre-nosotros.mp3";
var audioUnirse = new Audio();
audioUnirse.src = "sonido/Unirse-a-grupo-de-estudio.mp3";