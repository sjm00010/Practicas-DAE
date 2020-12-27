package dae.ujapack.entidades;

import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.errores.PuntosAnterioresNulos;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.entidades.puntosControl.PuntoControl;
import dae.ujapack.utils.Utils.Estado;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Entidad envio
 *
 * @author sjm00010
 */
@Entity
public class Envio implements Serializable {

    @Id
    @Size(min = 10, max = 10)
    private String id;

    @Positive
    private int alto;

    @Positive
    private int ancho;

    @Positive
    private int peso;

    /*  Como tengo dos clientes (origen y destino) como objetos valor es necesario,
        
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dni", column = @Column(name = "origen_dni")),
        @AttributeOverride(name = "nombre", column = @Column(name = "origen_nombre")),
        @AttributeOverride(name = "apellidos", column = @Column(name = "origen_apellidos")),
        @AttributeOverride(name = "localizacion", column = @Column(name = "origen_localizacion"))
    })
    @Valid
    private Cliente origen;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dni", column = @Column(name = "destino_dni")),
        @AttributeOverride(name = "nombre", column = @Column(name = "destino_nombre")),
        @AttributeOverride(name = "apellidos", column = @Column(name = "destino_apellidos")),
        @AttributeOverride(name = "localizacion", column = @Column(name = "destino_localizacion"))
    })
    @Valid
    private Cliente destino;

    // Ruta del envio, la cargo junto con el envio debido a la alta depencia con este. Es practicamente un objeto embebido
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "Paso")
    @NotNull
    private List<@Valid Paso> ruta;

    @PastOrPresent
    private LocalDateTime entrega; // Representación de la entrega del envio, sacado de la ruta(sustutiye al repartidor)

    @NotNull
    private Estado estado;

    @PastOrPresent
    private LocalDateTime fechaActualizado;

    public Envio() {
    }

    // Para no hacer mas engorrosa la creación del envío el cliente ya viene creado, solo se vincula
    public Envio(String id, int alto, int ancho, int peso, Cliente origen,
            Cliente destino, ArrayList<@Valid Paso> ruta) {
        this.id = id;
        this.alto = alto;
        this.ancho = ancho;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
        this.ruta = ruta;
        this.entrega = null;
        this.estado = estado.EN_TRANSITO; // Por defecto cuando se crea un envío está EN_TRANSITO dado que esta en la oficina de origen
        getUltimoPunto().ifPresentOrElse(
                paso -> this.fechaActualizado = paso.getFecha(),
                () -> this.fechaActualizado = LocalDateTime.now());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @return the origen
     */
    public Cliente getOrigen() {
        return origen;
    }

    /**
     * @return the destino
     */
    public Cliente getDestino() {
        return destino;
    }

    /**
     * @return the ruta
     */
    public List<Paso> getRuta() {
        return ruta;
    }

    /**
     * Función que calcula el precio de un envío
     *
     * @return precio
     */
    public float getPrecio() {
        return peso * (alto * ancho) * (ruta.size() / 2 + 1) / 1000;
    }

    /**
     * @return the entrega
     */
    public LocalDateTime getEntrega() {
        return entrega;
    }

    /**
     * @param entrega the entrega to set
     */
    public void setEntrega(LocalDateTime entrega) {
        this.estado = Estado.ENTREGADO;
        this.entrega = entrega;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Función que devuelve el punto actual del envío
     *
     * @return PuntoControl punto de control actual
     */
    public Optional<Paso> getUltimoPunto() {
        return Optional.ofNullable(ruta.stream()
                .reduce(null, (anterior, actual) -> actual.getFecha() == null ? anterior : actual));
    }

    /**
     * Funcion que actualiza la fecha de un punto de la ruta
     *
     * @param fecha Fecha que hay que actualizar
     * @param inOut Entrada o salida del Paso
     * @param pc Punto de control a actualizar
     */
    public void actualizar(LocalDateTime fecha, boolean inOut, PuntoControl pc) {
        /* Se busca en los pasos aquel que tenga el PuntoControl igual al
           dado y que coincida con el valor de inOut para añadirle la fecha */
        if (pc == null) {
            throw new IdPuntoControlInvalido("Punton de control nulo");
        }

        boolean anterior = false, esta = false;
        for (Paso paso : ruta) {
            if (paso.getPasoPuntos().getId().equals(pc.getId()) && paso.isInOut() == inOut) {
                paso.setFecha(fecha);
                fechaActualizado = fecha;
                if (pc.getId().equals(destino.getLocalizacion()) && inOut) {
                    this.estado = Estado.EN_REPARTO;
                }
                esta = true;
                break;
            } else if (paso.getFecha() == null) {
                anterior = true;
            }
        }

        if (!esta) {
            throw new IdPuntoControlInvalido("Se intenta actualizar un punto que no esta en la ruta");
        } else if (anterior) {
            throw new PuntosAnterioresNulos("Algun punto anterior no ha sido actualizado");
        }
    }

    public boolean estaExtravido() {
        if (estado.equals(Estado.EXTRAVIADO)) {
            return true;
        } else if (estado.equals(Estado.ENTREGADO)) {
            return false;
        }

        AtomicBoolean extraviado = new AtomicBoolean(false);
        final Period periodo = Period.ofDays(7);

        ruta.stream().filter(paso -> paso.getFecha() != null)
                .reduce((anterior, actual) -> {
                    if (actual.getFecha().minus(periodo).isAfter(anterior.getFecha())) {
                        extraviado.set(true);
                    }

                    return actual;
                });

        if (extraviado.get()) {
            this.estado = Estado.EXTRAVIADO;
        }

        return extraviado.get();
    }
}
