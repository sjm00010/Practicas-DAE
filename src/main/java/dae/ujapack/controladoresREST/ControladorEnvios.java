package dae.ujapack.controladoresREST;

import dae.ujapack.controladoresREST.DTOs.DTOEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOLocalizadorPrecioEnvio;
import dae.ujapack.entidades.Envio;
import dae.ujapack.errores.EnvioNoExiste;
import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.servicios.ServicioMensajeria;
import dae.ujapack.utils.Utils.Periodo;
import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sjm00010
 */
@RestController
@RequestMapping("/ujapack")
public class ControladorEnvios {
    @Autowired
    ServicioMensajeria servicios;

    /** Handler para excepciones de violación de restricciones */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerViolacionRestricciones(ConstraintViolationException e) {
    }

    
    /** Handler para excepciones de accesos a puntos de control no existe */
    @ExceptionHandler({IdPuntoControlInvalido.class, EnvioNoExiste.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlerClienteNoRegistrado() {
    }
   
    /** Crea envio y devolver identificador y precio */
    @PostMapping("/envio")
    ResponseEntity<DTOLocalizadorPrecioEnvio> creaEnvio(@RequestBody Envio envio){
        LocalizadorPrecioEnvio localizador = servicios.creaEnvio(envio.getAlto(), envio.getAncho(), envio.getPeso(), envio.getOrigen(), envio.getDestino());
        return ResponseEntity.ok(new DTOLocalizadorPrecioEnvio(localizador));
    }
    
    /** Obtiene el envio con ID */
    @GetMapping("/envio/{id}")
    ResponseEntity<DTOEnvio> getEnvio(@PathVariable("id") String idEnvio){
        Envio envio = servicios.getEnvio(idEnvio);
        return ResponseEntity.ok(new DTOEnvio(envio));
    }
    
    /** Actualiza la entrega del envio con ID */
    @PutMapping("/envio/{id}/{idPuntoControl}/{entradaSalida}")
    ResponseEntity actualizaEnvio(@PathVariable("id") String idEnvio, 
            @PathVariable("idPuntoControl") String idPC, @PathVariable("entradaSalida") boolean inOut){
        servicios.actualizar(idEnvio, LocalDateTime.now(), inOut, idPC);
        return ResponseEntity.ok("Envio actualizado correctamente.");
    }
    
    /** Actualiza el envio con ID */
    @PutMapping("/envio/{id}/entrega")
    ResponseEntity entregaEnvio(@PathVariable("id") String idEnvio){
        servicios.notificarEntrega(idEnvio, LocalDateTime.now());
        return ResponseEntity.ok("Envio entregado correctamente.");
    }
    
    // Opcionales
    
    /** Obtiene los envios extraviados. Indicar opcionalmente fecha de inicio y fin */
    @GetMapping("/envio/extraviados")
    ResponseEntity<DTOEnvio> getExtraviados(@RequestParam Optional<LocalDateTime> inicio, 
            @RequestParam Optional<LocalDateTime> fin){
        ResponseEntity response;
        try{
            List<Envio> extraviados = servicios.obtenerExtraviados(inicio.orElse(null), fin.orElse(null));
            response = ResponseEntity.ok(extraviados.stream().map((envio) -> {
                return new DTOEnvio(envio);
            }).collect(Collectors.toList()));
        }catch(IllegalArgumentException e){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
    
    /** Obtiene el porcentaje de envios extraviados. Indicar periodo(DIA, MES, ANIO) */
    @GetMapping("/envio/extraviados/{periodo}")
    ResponseEntity<DTOEnvio> getProcentaje(@PathVariable Periodo periodo){
        ResponseEntity response;
        try{
            float porcentaje = servicios.porcentajeExtraviados(periodo);
            response = ResponseEntity.ok(porcentaje);
        }catch(IllegalArgumentException e){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
}
