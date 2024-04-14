package Unad.telecom_fase5.servicios;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MercadoPagoService {


    public String generarLinkDePago(String placa) {
        try {
            Map<String, String> codigosEnlaces = new HashMap<>();

            // Agregar los códigos y enlaces al Map
            codigosEnlaces.put("BJP430", "https://mpago.li/2fCySvj");
            codigosEnlaces.put("UKK84C", "https://mpago.li/2ahJ5pQ");
            codigosEnlaces.put("AXG55B", "https://mpago.li/14ozsvK");
            codigosEnlaces.put("NVB70A", "https://mpago.li/2Smve27");
            codigosEnlaces.put("CZU877", "https://mpago.li/2yBogh9");
            codigosEnlaces.put("BPA270", "https://mpago.li/2Vxd4AA");
            codigosEnlaces.put("MDX21B", "https://mpago.li/17MbpA1");
            codigosEnlaces.put("EJH95D", "https://mpago.li/25niDBg");
            codigosEnlaces.put("NVM896", "https://mpago.li/1jAM2eo");
            codigosEnlaces.put("970ADE", "https://mpago.li/2wfpDfi");
            codigosEnlaces.put("FTR48C", "https://mpago.li/25smWg1");
            codigosEnlaces.put("KSG18D", "https://mpago.li/1Vv377d");
            codigosEnlaces.put("THC83C", "https://mpago.li/2T7acYJ");
            codigosEnlaces.put("MKM59", "https://mpago.li/2xsse2K");
            codigosEnlaces.put("GSD13C", "https://mpago.li/2zVwtkS");
            codigosEnlaces.put("KST85D", "https://mpago.li/2i96gbq");
            codigosEnlaces.put("CVJ81B", "https://mpago.li/1DG9c8B");
            codigosEnlaces.put("PBZ92D", "https://mpago.li/1GWB6VZ");
            codigosEnlaces.put("BNY617", "https://mpago.li/1GA4vjB");
            codigosEnlaces.put("TML70D", "https://mpago.li/2siCEcf");
            codigosEnlaces.put("UJN39C", "https://mpago.li/2uoVvNA");
            codigosEnlaces.put("HFY372", "https://mpago.li/1UwKBS3");
            String respuesta = codigosEnlaces.get(placa);
            if (respuesta.length() > 3) {
                return respuesta;
            } else {
                return "link.mercadopago.com.co/bjp430";
            }
        } catch (Exception e) {
            return "https://link.mercadopago.com.co/bjp430";
        }
    }
}
