package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Cliente;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Oficina;
import dae.ujapack.entidades.Paso;
import dae.ujapack.entidades.Repartidor;
import dae.ujapack.interfaces.PuntoControl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

/**
 *
 * @author sjm00010
 */
@Component
public class ServicioMensajeria {
    
    // Variables auxiliares
    Grafo grafo;

    //          Repositorio
    private Map<String, Oficina> oficinas;
    private Map<String, CentroLogistico> centrosLogisticos;
    private Map<String, Envio> envios;

    public ServicioMensajeria() {
        this.oficinas = new HashMap<>();
        this.centrosLogisticos = new HashMap<>();
        this.envios = new HashMap<>();
        this.grafo = new Grafo();
    }
    
    //            Servicio
    
    // Funciones auxiliares
    
    /**
     * Función que crea los centros y oficinas a partir de la información leída del json
     * @param centro JSONObject con la información del centro a crear
     * @param i Número del centro
     */
    private void transformarEnObjetos(JSONObject centro, String id) {

        // Obtengo el nombre del centro
        String nombre = (String) centro.get("nombre");

        // Obtengo la localización del centro
        String localizacion = (String) centro.get("localización");

        // Obtengo el conjunto de conexiones
        JSONArray conexionesTemp = (JSONArray) centro.get("conexiones");
        ArrayList<String> conexiones = new ArrayList<>();
        for (Object object : conexionesTemp) {
            conexiones.add(object.toString());
        }
        
        centrosLogisticos.put( id ,new CentroLogistico(id, nombre, localizacion, conexiones));
        
        // Obtengo el conjunto de conexiones
        JSONArray provincias = (JSONArray) centro.get("provincias");
        for (int i = 0; i < provincias.size(); i++) {
            String nombreProvincia = provincias.get(i).toString();
            oficinas.put( nombreProvincia, new Oficina(nombreProvincia, centrosLogisticos.get(id)));
        }   
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
     * Funcion que genera la ruta que ha de seguir el envio
     * @param origen
     * @param destino
     * @return 
     */
    private ArrayList<Paso> generaRuta(String origen, String destino){
        ArrayList<Paso> ruta = new ArrayList<>();
        Oficina oficinaOrig = oficinas.get(origen);
        Oficina oficinaDest = oficinas.get(destino);
        System.out.println("Generando ruta");
        if(oficinaOrig != null && oficinaDest != null){
            // Caso 1 : Misma provincia
            if(oficinaOrig.equals(oficinaDest)){
                ruta.add(new Paso(oficinaOrig, false));
                ruta.add(new Paso(oficinaOrig, true));
            }else{
                CentroLogistico centroOrig = oficinaOrig.getCentroAsociado();
                CentroLogistico centroDest = oficinaDest.getCentroAsociado();
                if(centroOrig.equals(centroDest)){// Caso 2 : Distinta provincia y mismo centro
                    ruta.add(new Paso(oficinaOrig,false));
                    ruta.add(new Paso(oficinaOrig,true));
                    ruta.add(new Paso(centroOrig,false));
                    ruta.add(new Paso(centroOrig,true));
                    ruta.add(new Paso(oficinaDest,false));
                    ruta.add(new Paso(oficinaDest,true));
                }else{ // Caso 3 : Distinta provincia y varios centros
                    // Añado el origen
                    ruta.add(new Paso(oficinaOrig,false));
                    ruta.add(new Paso(oficinaOrig,true));
                    
                    // Calculo y añado los centros logisticos por los que pasa
                    List<String> centrosRuta = grafo.obtenRuta(centroOrig.getId(), centroDest.getId());
                    for (String idCentro : centrosRuta) {
                        ruta.add(new Paso(centrosLogisticos.get(idCentro), false));
                        ruta.add(new Paso(centrosLogisticos.get(idCentro), true));
                    }
                    
                    // Añado el destino
                    ruta.add(new Paso(oficinaDest, false));
                    ruta.add(new Paso(oficinaDest, true));
                }
            }
            // Añado el final de la ruta
            ruta.add(new Paso(new Repartidor(), false));
            ruta.add(new Paso(new Repartidor(), true));
            
            // Asigno la fecha actual a la entrada del envio
            ruta.get(0).setFecha(LocalDate.now());
        }else{
            throw new RuntimeException("Error al generar envío. Los cliente tienen una localización no valida");
        }
        
        return ruta;
    }
    
    // Fin funciones auxiliares

    /**
     * Función que carga los datos del fichero json
     *
     * @param ruta Ruta del fichero .json
     * @param numCentros Número de centros en el archivo .json
     */
    public void cargaDatos(String ruta , int numCentros) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), "UTF-8"));) {
            System.out.println("Archivo leido");
            
            // Leo el archivo JSON
            JSONObject listaCentros = (JSONObject) jsonParser.parse(reader);

            // Recorro todos los centros logisticos
            for (int i = 1; i <= numCentros; i++) {
                JSONObject centro = (JSONObject) jsonParser.parse(listaCentros.get(Integer.toString(i)).toString());
                transformarEnObjetos(centro, Integer.toString(i));
            }
            
            // Aprovecho que ya tengo lso centros para generar el grafo para la ruta
            grafo.generaGrafo((ArrayList<CentroLogistico>) centrosLogisticos.values().stream().collect(Collectors.toList()));
            
            // Prueba
            System.out.println("Centros leidos : "+centrosLogisticos.size());
            
            System.out.println("Centros y Oficinas cargadas");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Función para crear el envío de un paquete
     * @param alto Alto del paquete
     * @param ancho Ancho del paquete
     * @param peso Peso del paquete
     * @param origen Cliente que envía el paquete
     * @param destino Cliente que recibe el paquete
     * @return Pair<String, Integer> Identificador y precio
     */
    public Pair<String, Integer> creaEnvio(int alto,int ancho,int peso, Cliente origen, Cliente destino){
        String id = generaId();
        ArrayList<Paso> ruta = generaRuta(origen.getLocalizacion(), destino.getLocalizacion());
        envios.put( id, new Envio(id, alto, ancho, peso, origen, destino, ruta));
        System.out.println("Envio creado");
        return new Pair<String, Integer>(id, envios.get(id).calculaPrecio());
    }
    
    /**
     * Función que obtiene la situacion de un envío
     * @param idEnvio ID del envio a localizar
     * @return Pair<PuntoControl,String> Par con el punto de control actual y la situación
     */
    public Pair<PuntoControl,String> obtenerSituacion(String idEnvio){
        Paso punto = envios.get(idEnvio).getUltimoPunto();
        String estado;
        if(punto.getPasoPuntos().getClass() == CentroLogistico.class ||
                punto.getPasoPuntos().getClass() == Oficina.class){
            estado = "en transito";
        }else{
            if (punto.isInOut()){
                estado = "entregado";
            }else{
                estado = "en reparto";
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
            punto = oficinas.get(idPc);
            if(punto == null)
                punto = centrosLogisticos.get(idPc);
        } 
        if(punto == null){
            throw new RuntimeException("Error al actualizar envío. ID del punto de control invalido");
        }
        envios.get(idEnvio).actualizar(fecha, inOut, punto);
    }
}
