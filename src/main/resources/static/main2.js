guardarIngreso();




function guardarIngreso() {
    // Obtener la fecha actual en el formato deseado (día, mes, año, hora, minutos, segundos)
    var fechaActual = obtenerFechaActual();

    // Crear el nombre del ingreso con la fecha
    var nombreIngreso = "INGRESO EL ---"+fechaActual;

    // Objeto con los datos del ingreso a guardar
    var ingreso = {
        nombre: nombreIngreso
    };

    // Convertir el objeto a formato JSON
    var jsonIngreso = JSON.stringify(ingreso);

    // Configuración de la solicitud HTTP
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/app/ingreso/save", true); // Corregir la ruta a "/app/ingreso/save"
    xhr.setRequestHeader("Content-Type", "application/json");

    // Manejar la respuesta de la solicitud
    xhr.onload = function () {
        if (xhr.status === 201) {
            console.log("OK USER SECURE");
        } else {
            console.error("DATA ERROR");
        }
    };

    // Enviar la solicitud con los datos del ingreso
    xhr.send(jsonIngreso);
}



// Función para obtener la fecha actual en el formato deseado
function obtenerFechaActual() {
    var fecha = new Date();
    var dia = agregarCero(fecha.getDate());
    var mes = agregarCero(fecha.getMonth() + 1); // Sumar 1 porque los meses van de 0 a 11
    var año = fecha.getFullYear();
    var hora = agregarCero(fecha.getHours());
    var minutos = agregarCero(fecha.getMinutes());
    var segundos = agregarCero(fecha.getSeconds());

    return dia + "/" + mes + "/" + año + " " + hora + ":" + minutos + ":" + segundos;
}

// Función para agregar un cero delante de los números menores a 10
function agregarCero(numero) {
    return numero < 10 ? "0" + numero : numero;
}

// Función para obtener la ubicación del usuario
function obtenerUbicacion(callback) {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var latitud = position.coords.latitude;
            var longitud = position.coords.longitude;
            // Puedes usar la API de geocodificación inversa para obtener la ubicación a partir de la latitud y longitud
            // Por simplicidad, aquí se muestra solo la latitud y longitud
            var ubicacion = "Latitud: " + latitud + ", Longitud: " + longitud;
            callback(ubicacion);
        });
    } else {

        callback("Ubicación desconocida");
    }
}



