package dae.ujapack.servicios;

import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Paso;
import dae.ujapack.errores.EnvioNoExiste;
import dae.ujapack.errores.PuntosAnterioresNulos;
import dae.ujapack.entidades.puntosControl.PuntoControl;
import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.repositorios.RepositorioCentroLogistico;
import dae.ujapack.repositorios.RepositorioEnvios;
import dae.ujapack.repositorios.RepositorioOficina;
import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;
import dae.ujapack.utils.tuplas.PuntoControlEstadoEnvio;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dae.ujapack.utils.Utils.Estado;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author sjm00010
 */
@Service
@Validated
public class ServicioMensajeria {

    // Variables auxiliares
    @Autowired
    private ServicioEnrutado servicioEnrutado;

    //          Repositorio
    @Autowired
    RepositorioEnvios repositorioEnvios;

    @Autowired
    RepositorioCentroLogistico repositorioCentroLogistico;

    @Autowired
    RepositorioOficina repositorioOficina;

  
    /**
     * Getter de envio
     *
     * @param id ID del envio a localizar
     * @return El envío localizado, si no existe lanza un error
     */
    public Envio getEnvio(String id) {
        return repositorioEnvios.buscar(id).orElseThrow(() -> new EnvioNoExiste("No existe ningun envío con id: " + id));
    }

    /*************************
     *        Servicio       *
     *************************/
     
    // ------ Funciones auxiliares ------
    
    
    /**
     * Función que genera los IDs de los envíos
     *
     * @return Identificador creado
     */
    private String generaId() {
        boolean generado = false;
        String numero = "";
        Random rn = new Random();

        while (!generado) {
            numero = "";

            // El bucle se repite 10 veces, tamaño Id envio
            for (int i = 0; i < 10; i++)
                numero += Integer.toString(rn.nextInt(10));

            // Se comprueba que es único
            generado = !repositorioEnvios.buscar(numero).isPresent();
        }
        
        return numero;
    }

    /**
     * Función que genera la ruta que ha de seguir el envio
     *
     * @param origen Oficina origen
     * @param destino Oficina destino
     * @return ArrayList<Paso> Ruta calculada
     */
    private ArrayList<Paso> generaRuta(@NotBlank String origen, @NotBlank String destino) {        
        return servicioEnrutado.generaRuta(repositorioOficina.buscar(origen).orElseThrow(() -> new IdPuntoControlInvalido("El id " + origen + " de la oficina de origen es inválido")),
                                           repositorioOficina.buscar(destino).orElseThrow(() -> new IdPuntoControlInvalido("El id " + destino + " de la oficina de destino es inválido")));
    }

    // ------ Fin funciones auxiliares ------
    
    
    /**
     * Función para crear el envío de un paquete
     *
     * @param alto Alto del paquete
     * @param ancho Ancho del paquete
     * @param peso Peso del paquete
     * @param origen Cliente que envía el paquete
     * @param destino Cliente que recibe el paquete
     * @return LocalizadorPrecioEnvio tupla identificador y precio
     */
    public LocalizadorPrecioEnvio creaEnvio(@Positive int alto, @Positive int ancho,
            @Positive int peso, @Valid @NotNull Cliente origen, @Valid @NotNull Cliente destino) {
        String id = generaId();
        ArrayList<Paso> ruta = generaRuta(origen.getLocalizacion(), destino.getLocalizacion());

        Envio envio = new Envio(id, alto, ancho, peso, origen, destino, ruta);
        repositorioEnvios.crear(envio);

        return new LocalizadorPrecioEnvio(id, envio.getPrecio());
    }

    /**
     * Función que obtiene la situacion de un envío
     *
     * @param idEnvio ID del envio a localizar
     * @return PuntoControlEstadoEnvio tupla con el punto de control actual y la
     * situación
     */
    public PuntoControlEstadoEnvio obtenerSituacion(@Size(min = 10, max = 10) String idEnvio) {
        Envio envio = getEnvio(idEnvio);
        Paso punto = envio.getUltimoPunto();
        Estado estado = Estado.EN_TRANSITO;

        if (envio.getEntrega() != null) {
            estado = Estado.ENTREGADO;
        } else if (envio.getRuta().size() - 1 == envio.getRuta().indexOf(punto)) {
            estado = Estado.EN_REPARTO;
        }

        return new PuntoControlEstadoEnvio(punto.getPasoPuntos(), estado);
    }

    /**
     * Función que actualiza el estado de un envio
     *
     * @param idEnvio ID del envío a actualizar
     * @param fecha Fecha actual
     * @param inOut Entrada o salida del punto de control
     * @param idPc Identificador del punto de control. Si es repartidor poner
     * "Repartidor"
     */
    @Transactional
    public void actualizar(@Size(min = 10, max = 10) String idEnvio,
            @PastOrPresent LocalDateTime fecha, boolean inOut, @NotBlank String idPc) {

        // Compruebo que el envio existe, en caso de no existir lanza un error
        Envio envio = getEnvio(idEnvio);

        PuntoControl punto = repositorioOficina.buscar(idPc).orElse(null);
                if(punto == null){
                    punto = repositorioCentroLogistico.buscar(idPc).orElseThrow(() -> new IdPuntoControlInvalido("El id " + idPc + " del punto de control es inválido"));
                }
        envio.actualizar(fecha, inOut, punto);
    }

    /**
     * Función para notificar la entrega del envío
     *
     * @param idEnvio Id del envio ha actualizar
     * @param fecha Fecha de entrega
     */
    @Transactional
    public void notificarEntrega(@Size(min = 10, max = 10) String idEnvio, @PastOrPresent LocalDateTime fecha) {
        // Me aseguro que el envio existe y obtengo el ultimo punto
        Envio envio = getEnvio(idEnvio);
        Paso punto = envio.getUltimoPunto();

        if (envio.getRuta().size() - 1 == envio.getRuta().indexOf(punto)) {
            envio.setEntrega(fecha);
        } else {
            throw new PuntosAnterioresNulos("Algun punto anterior no ha sido actualizado");
        }
    }
}
