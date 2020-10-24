package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Oficina;
import dae.ujapack.entidades.Paso;
import dae.ujapack.entidades.Repartidor;
import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.util.Pair;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dae.ujapack.utils.util.Estado;

/**
 *
 * @author sjm00010
 */
@Service
public class ServicioMensajeria {
    
    // Variables auxiliares
    @Autowired
    private Grafo grafo;
    
    @Autowired
    private ServicioCarga sc;

    //          Repositorio
    private Map<String, Oficina> oficinas;
    private Map<String, CentroLogistico> centrosLogisticos;
    private Map<String, Envio> envios;

    /**
     * @param grafo the grafo to set
     */
    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * @return the oficinas
     */
    public Map<String, Oficina> getOficinas() {
        return oficinas;
    }

    /**
     * @param oficinas the oficinas to set
     */
    public void setOficinas(Map<String, Oficina> oficinas) {
        this.oficinas = oficinas;
    }

    /**
     * @return the centrosLogisticos
     */
    public Map<String, CentroLogistico> getCentrosLogisticos() {
        return centrosLogisticos;
    }

    /**
     * @param centrosLogisticos the centrosLogisticos to set
     */
    public void setCentrosLogisticos(Map<String, CentroLogistico> centrosLogisticos) {
        this.centrosLogisticos = centrosLogisticos;
    }

    /**
     * @return the envios
     */
    public Map<String, Envio> getEnvios() {
        return envios;
    }
    
    /**
     * @return un envios
     */
    public Envio getEnvio(String id) {
        return envios.get(id);
    }

    /**
     * @param envios the envios to set
     */
    public void setEnvios(Map<String, Envio> envios) {
        this.envios = envios;
    }

    public ServicioMensajeria() {
        this.oficinas = new HashMap<>();
        this.centrosLogisticos = new HashMap<>();
        this.envios = new HashMap<>();
    }
    
    //            Servicio
    
    // Funciones auxiliares
    
    /**
     * Función para la carga de datos
     */
    @PostConstruct
    private void cargaDatos(){
        this.sc.cargaDatos(this);
    }
    
    /**
     * Función que genera los IDs de los envíos
     * @return Identificador creado
     */
    private String generaId(){
        boolean generado = false;
        String numero = "";
        Random rn = new Random();
        
        while(!generado){
            numero = "";
            
            // El bucle se repite 10 veces, tamaño Id envio
            for(int i = 0; i < 10;i++){
                numero += Integer.toString(rn.nextInt(10));
            }
            
            // Se comprueba que es único
            if(!envios.containsKey(numero))
                generado = true;
        }
        return numero;
    }
    
    /**
     * Función que genera la ruta que ha de seguir el envio
     * @param origen Oficina origen
     * @param destino Oficina destino
     * @return ArrayList<Paso> Ruta calculada
     */
    private ArrayList<Paso> generaRuta(String origen, String destino){
        return grafo.generaRuta(oficinas.get(origen), oficinas.get(destino), centrosLogisticos);
    }
    
    // Fin funciones auxiliares



    /**
     * Función para crear el envío de un paquete
     * @param alto Alto del paquete
     * @param ancho Ancho del paquete
     * @param peso Peso del paquete
     * @param origen Cliente que envía el paquete
     * @param destino Cliente que recibe el paquete
     * @return Pair<String, Integer> Identificador y precio
     */
    public Pair<String, Integer> creaEnvio(int alto,int ancho,int peso, Cliente origen, Cliente destino) throws IdPuntoControlInvalido{
        String id = generaId();
        ArrayList<Paso> ruta = generaRuta(origen.getLocalizacion(), destino.getLocalizacion());
        getEnvios().put( id, new Envio(id, alto, ancho, peso, origen, destino, ruta));
        return new Pair<String, Integer>(id, getEnvios().get(id).getPrecio());
    }
    
    /**
     * Función que obtiene la situacion de un envío
     * @param idEnvio ID del envio a localizar
     * @return Pair<PuntoControl,String> Par con el punto de control actual y la situación
     */
    public Pair<PuntoControl,String> obtenerSituacion(String idEnvio){
        Paso punto = getEnvios().get(idEnvio).getUltimoPunto();
        String estado;
        if(punto.getPasoPuntos().getClass() == CentroLogistico.class ||
                punto.getPasoPuntos().getClass() == Oficina.class){
            estado = Estado.EN_TRANSITO.toString();
        }else{
            if (punto.isInOut()){
                estado = Estado.ENTREGADO.toString();
            }else{
                estado = Estado.EN_REPARTO.toString();
            }
        }
        return new Pair<PuntoControl, String>(punto.getPasoPuntos(), estado);
    }
    
    /**
     * Función que actualiza el estado de un envio
     * @param idEnvio ID del envío a actualizar
     * @param fecha Fecha actual
     * @param inOut Entrada o salida del punto de control
     * @param pc Identificador del punto de control. Si es repartidor poner "Repartidor"
     */
    public void actualizar(String idEnvio, LocalDate fecha, boolean inOut, String idPc){
        PuntoControl punto = null;
        if(idPc.equals("Repartidor"))
            punto = new Repartidor();
        else{
            punto = getOficinas().get(idPc);
            if(punto == null)
                punto = getCentrosLogisticos().get(idPc);
        } 
        if(punto == null){
            throw new IdPuntoControlInvalido("Error al actualizar envío. ID del punto de control invalido");
        }
        getEnvios().get(idEnvio).actualizar(fecha, inOut, punto);
    }
}
