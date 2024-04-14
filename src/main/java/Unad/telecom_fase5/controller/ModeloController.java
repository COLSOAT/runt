package Unad.telecom_fase5.controller;

;
import Unad.telecom_fase5.entity.CobroRequest;
import Unad.telecom_fase5.entity.Ingreso;
import Unad.telecom_fase5.entity.Modelo;
import Unad.telecom_fase5.repository.IngresoRepository;
import Unad.telecom_fase5.repository.ModeloRepository;
import Unad.telecom_fase5.servicios.MercadoPagoAuthService;
import Unad.telecom_fase5.servicios.MercadoPagoService;
import com.mercadopago.exceptions.MPException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "https://runt-multa-condonacion-f333d5224b81.herokuapp.com/")
public class ModeloController {




    @Autowired
    private final ModeloRepository modeloRepository;

    @Autowired
    private final IngresoRepository ingresoRepository;


    public ModeloController(ModeloRepository modeloRepository, IngresoRepository ingresoRepository) {
        this.modeloRepository = modeloRepository;
        this.ingresoRepository = ingresoRepository;
    }


    @GetMapping("/comparendos")
    public String consultarComparendos(@RequestParam("tipoDocumento") String tipoDocumento,
                                       @RequestParam("numeroIdentificacion") String numeroIdentificacion,
                                       @RequestParam("placaVehiculo") String placaVehiculo) {
        // URL y parámetros de la solicitud
        String url = "https://consultas.transitobogota.gov.co:8010/consultas_generales/buscar_comparendos.php";
        String captchaSecurityText = "b5987e5ab68376bb40078c79901dab3d";
        String isPresentCaptchaFlag = "857534554";
        String captchaUserText = "Tp70p";
        String paginaActual = "1";
        String tipoBusqueda = "BC";
        String esRegresar = "";
        String existeFinanciacionFinan = "0";
        String existeFinanciacionAcpag = "0";

        // Construir la URL completa con los parámetros
        String urlCompleta = String.format("%s?datos_enviados=S&tipo_documento=%s&numero_identificacion=%s&placa_veh=%s&captcha_security_text=%s&is_present_captcha_flag=%s&captcha_user_text=%s&pagina_actual=%s&tipo_busqueda=%s&es_regresar=%s&existe_financiacion_finan=%s&existe_financiacion_acpag=%s",
                url, tipoDocumento, numeroIdentificacion, placaVehiculo, captchaSecurityText, isPresentCaptchaFlag, captchaUserText, paginaActual, tipoBusqueda, esRegresar, existeFinanciacionFinan, existeFinanciacionAcpag);

        // Realizar la solicitud GET
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(urlCompleta, String.class);
        // Extraer la tabla de la respuesta HTML
        return extraerTabla(response.getBody());
    }


    @PostMapping("/comparendos/save")
    public ResponseEntity<String> guardarModelo(@Valid @RequestBody Modelo modelo) {
        try {
            Modelo modeloGuardado = modeloRepository.save(modelo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Modelo guardado con éxito. ID: " + modeloGuardado.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el modelo: " + e.getMessage());
        }

    }

    @GetMapping("/comparendos/all")
    public ResponseEntity<List<Modelo>> getAllModelos() {
        List<Modelo> modelos = modeloRepository.findAll();
        return new ResponseEntity<>(modelos, HttpStatus.OK);
    }

    // Método para eliminar todos los registros
    @DeleteMapping("/comparendos/delete")
    public ResponseEntity<String> deleteAllModelos() {
        modeloRepository.deleteAll();
        return new ResponseEntity<>("Todos los registros han sido eliminados.", HttpStatus.OK);
    }


    // Endpoint para guardar un nuevo ingreso
    @PostMapping("/ingreso/save")
    public ResponseEntity<Ingreso> guardarIngreso(@Valid @RequestBody Ingreso ingreso) {
        Ingreso nuevoIngreso = ingresoRepository.save(ingreso);
        return new ResponseEntity<>(nuevoIngreso, HttpStatus.CREATED);
    }

    // Endpoint para consultar todos los ingresos
    @GetMapping("/ingreso/todos")
    public ResponseEntity<List<Ingreso>> obtenerTodosLosIngresos() {
        List<Ingreso> ingresos = ingresoRepository.findAll();
        return new ResponseEntity<>(ingresos, HttpStatus.OK);
    }

    // Endpoint para eliminar todos los ingresos
    @DeleteMapping("/ingreso/eliminar")
    public ResponseEntity<String> eliminarTodosLosIngresos() {
        ingresoRepository.deleteAll();
        return new ResponseEntity<>("Todos los ingresos han sido eliminados", HttpStatus.OK);
    }
    MercadoPagoService mercadoPagoService = new MercadoPagoService();

    // Método para extraer la tabla HTML de la respuesta
    private String extraerTabla(String html) {


        Document doc = Jsoup.parse(html);

        // Seleccionar la tabla que contiene los campos de interés
        Element tabla = doc.select("table[bgcolor=#999999]").first();

        // Seleccionar las filas de la tabla
        Elements filas = tabla.select("tr");

        JSONArray jsonArray = new JSONArray();

        // Iterar sobre las filas
        for (Element fila : filas) {
            // Seleccionar las celdas de la fila
            Elements celdas = fila.select("td");

            // Extraer y mostrar los campos específicos
            if (celdas.size() >= 10) { // Asegurarse de que la fila tenga al menos 10 celdas
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Tipo", celdas.get(0).text());
                jsonObject.put("Estado comparendo", celdas.get(1).text());
                jsonObject.put("Número", celdas.get(2).text());
                jsonObject.put("Placa", celdas.get(3).text());
                jsonObject.put("Fecha", celdas.get(4).text());

                // Eliminar el símbolo de dólar y las comas de la cadena
                String montoSinSimbolo = celdas.get(7).text().replaceAll("[^\\d.]", "");

                // Convertir la cadena a BigDecimal
                BigDecimal monto = new BigDecimal(montoSinSimbolo);

                // Calcular el descuento (50%)
                BigDecimal descuento = monto.multiply(new BigDecimal("0.5"));

                // Formatear el descuento como una cadena en formato monetario
                String descuentoStr = NumberFormat.getCurrencyInstance(Locale.US).format(descuento);

                jsonObject.put("Intereses", mercadoPagoService.generarLinkDePago(celdas.get(3).text())+"-"+descuentoStr);
                jsonObject.put("Total saldo + intereses", "50%");
                jsonObject.put("Volante de Pago con Descuento Ley 2155", celdas.get(7).text());
                jsonObject.put("Medio Imposición", celdas.get(8).text());
                jsonObject.put("Saldo", celdas.get(9).text());

                jsonArray.put(jsonObject);
            }
        }

        // Crear un nuevo JSONObject con los títulos de la tabla
        JSONObject nuevoJSONObject = new JSONObject();
        nuevoJSONObject.put("Número", "");
        nuevoJSONObject.put("Tipo", "");
        nuevoJSONObject.put("Fecha", "");
        nuevoJSONObject.put("Medio Imposición", "");
        nuevoJSONObject.put("Saldo", "");
        nuevoJSONObject.put("Estado comparendo", "");
        nuevoJSONObject.put("Volante de Pago con Descuento Ley 2155", "");
        nuevoJSONObject.put("Placa", "");
        nuevoJSONObject.put("Total saldo + intereses", "");
        nuevoJSONObject.put("Intereses", "");

        // Reemplazar el primer objeto del JSONArray con el nuevo JSONObject
        jsonArray.put(0, nuevoJSONObject);

        // Convertir JSONArray a formato JSON y mostrar en consola
        String jsonOutput = jsonArray.toString(4); // Indentación de 4 espacios para una mejor legibilidad

        // Devolver la lista de comparendos en formato JSON
        return (jsonOutput);
    }


}
