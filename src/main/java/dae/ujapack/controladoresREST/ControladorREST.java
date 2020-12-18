package dae.ujapack.controladoresREST;

import dae.ujapack.controladoresREST.DTOs.DTOLocalizadorPrecioEnvio;
import dae.ujapack.entidades.Envio;
import dae.ujapack.servicios.ServicioMensajeria;
import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sjm00010
 */
@RestController
@RequestMapping("/ujapack")
public class ControladorREST {
    @Autowired
    ServicioMensajeria servicios;
    
    @PostMapping("/envios")
    ResponseEntity<DTOLocalizadorPrecioEnvio> creaCliente(@RequestBody Envio envio){
        LocalizadorPrecioEnvio localizador = servicios.creaEnvio(envio.getAlto(), envio.getAncho(), envio.getPeso(), envio.getOrigen(), envio.getDestino());
        return ResponseEntity.ok(new DTOLocalizadorPrecioEnvio(localizador));
    }
}
