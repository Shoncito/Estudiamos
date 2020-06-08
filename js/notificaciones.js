$(document).ready(function(){
    if(!Notification){
        alert("Las notificaciones no están habilitadas para este navegador");
        return;
    }else if(Notification.permission!=="granted"){
        Notification.requestPermission();
    }
});

/**
 * Función que notifica
 * @param {String} mensaje 
 */
function notificar(subtipo, mensaje){
    if(Notification.permission!=="granted"){
        Notification.requestPermission();
    }else{
        var notificacion = new Notification(subtipo,{
            icon: "img/estudio.jpg",
            body: mensaje,
            timestamp: 800000
        })
    }
    
}