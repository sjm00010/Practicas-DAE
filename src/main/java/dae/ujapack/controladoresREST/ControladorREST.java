package dae.ujapack.controladoresREST;

import dae.ujapack.controladoresREST.DTOs.DTOEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOLocalizadorPrecioEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOPaso;
import dae.ujapack.controladoresREST.DTOs.DTOPuntoControlEstadoEnvio;
import dae.ujapack.entidades.Envio;
import dae.ujapack.errores.EnvioNoExiste;
import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.errores.PuntosAnterioresNulos;
import dae.ujapack.servicios.ServicioMensajeria;
import dae.ujapack.utils.Utils.Periodo;
import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;
import dae.ujapack.utils.tuplas.PuntoControlEstadoEnvio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/ujapack")
public class ControladorREST {

    @Autowired
    ServicioMensajeria servicios;

    /**
     * Handler para excepciones de violación de restricciones y puntos anteriores nulos
     */
    @ExceptionHandler({ConstraintViolationException.class, PuntosAnterioresNulos.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlerViolacionRestricciones() {
    }

    /**
     * Handler para excepciones de accesos a entidades que no existen
     */
    @ExceptionHandler({IdPuntoControlInvalido.class, EnvioNoExiste.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlerClienteNoRegistrado() {
    }

    /**
     * Crear un envio y devolver identificador y precio
     */
    @PostMapping("/envio")
    ResponseEntity<DTOLocalizadorPrecioEnvio> creaEnvio(@RequestBody DTOEnvio envio) {
        LocalizadorPrecioEnvio localizador = servicios.creaEnvio(envio.getAlto(), envio.getAncho(), envio.getPeso(), envio.getOrigen(), envio.getDestino());
        return ResponseEntity.status(HttpStatus.CREATED).body(new DTOLocalizadorPrecioEnvio(localizador));
    }

    /**
     * Obtiene el envio con ID
     */
    @GetMapping("/envio/{id}")
    ResponseEntity<DTOEnvio> getEnvio(@PathVariable("id") String idEnvio) {
        Envio envio = servicios.getEnvio(idEnvio);
        return ResponseEntity.ok(new DTOEnvio(envio));
    }

    /**
     * Actualiza el envio con ID
     */
    @PutMapping("/envio/{id}")
    @ResponseStatus(HttpStatus.OK)
    void entregaEnvio(@PathVariable("id") String idEnvio) {
        servicios.notificarEntrega(idEnvio, LocalDateTime.now());
    }

    /**
     * Obtiene la situación de un envio con ID
     */
    @GetMapping("/envio/{id}/situacion")
    ResponseEntity<DTOPuntoControlEstadoEnvio> getSituacion(@PathVariable("id") String idEnvio) {
        PuntoControlEstadoEnvio estado = servicios.obtenerSituacion(idEnvio);
        return ResponseEntity.ok(new DTOPuntoControlEstadoEnvio(estado.getPc().getId(), estado.getEstado()));
    }

    /**
     * Obtiene los puntos de control de un envio con ID
     */
    @GetMapping("/envio/{id}/puntoControl")
    @ResponseStatus(HttpStatus.OK)
    List<DTOPaso> getPuntosControl(@PathVariable("id") String idEnvio) {
        return servicios.getEnvio(idEnvio).getRuta()
                .stream()
                .map(paso -> new DTOPaso(paso))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza la entrega del envio con ID
     */
    @PutMapping("/envio/{id}/puntoControl/{idPuntoControl}")
    @ResponseStatus(HttpStatus.OK)
    void actualizaEnvio(@PathVariable("id") String idEnvio,
            @PathVariable("idPuntoControl") String idPC,
            @RequestParam boolean isSalida) {
        servicios.actualizar(idEnvio, LocalDateTime.now(), isSalida, idPC);
    }

    // Opcionales, para usarlos se debe insertar un envío como en el test que comprueba los extraviados
    /**
     * Obtiene los envios extraviados. Indicar opcionalmente fecha de inicio y
     * fin
     */
    @GetMapping("/envio/extraviados")
    ResponseEntity<List<DTOEnvio>> getExtraviados(@RequestParam Optional<LocalDateTime> inicio,
            @RequestParam Optional<LocalDateTime> fin) {
        ResponseEntity<List<DTOEnvio>> response;
        try {
            List<Envio> extraviados = servicios.obtenerExtraviados(inicio.orElse(null), fin.orElse(null));
            response = ResponseEntity.ok(extraviados.stream()
                    .map(envio -> new DTOEnvio(envio))
                    .collect(Collectors.toList()));
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    /**
     * Obtiene el porcentaje de envios extraviados. Indicar periodo(DIA, MES,
     * ANIO)
     */
    @GetMapping("/envio/extraviados/{periodo}")
    ResponseEntity<DTOEnvio> getPorcentaje(@PathVariable Periodo periodo) {
        ResponseEntity response;
        try {
            float porcentaje = servicios.porcentajeExtraviados(periodo);
            response = ResponseEntity.ok(porcentaje);
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
}
