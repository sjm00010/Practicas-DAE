package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Cliente;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Oficina;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.util.Pair;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

/**
 *
 * @author sjm00010
 */
@Component
public class ServicioMensajeria {

    //          Repositorio
    private Map<String, Oficina> oficinas;
    private Map<Integer, CentroLogistico> centrosLogisticos;
    private Map<String, Envio> envios;

    public ServicioMensajeria() {
        this.oficinas = new HashMap<>();
        this.centrosLogisticos = new HashMap<>();
        this.envios = new HashMap<>();
    }
    
    //            Servicio
    
    // Funciones auxiliares
    
    /**
     * Función que crea los centros y oficinas a partir de la información leída del json
     * @param centro JSONObject con la información del centro a crear
     * @param i Número del centro
     */
    private void transformarEnObjetos(JSONObject centro, int id) {

        // Obtengo el nombre del centro
        String nombre = (String) centro.get("nombre");

        // Obtengo la localización del centro
        String localizacion = (String) centro.get("localización");

        // Obtengo el conjunto de conexiones
        JSONArray conexiones = (JSONArray) centro.get("conexiones");
        
        centrosLogisticos.put( id ,new CentroLogistico(id, nombre, localizacion, conexiones));
        
        // Obtengo el conjunto de conexiones
        JSONArray provincias = (JSONArray) centro.get("provincias");
        for (int i = 0; i < provincias.size(); i++) {
            String nombreProvincia = provincias.get(i).toString();
            oficinas.put( nombreProvincia, new Oficina(nombreProvincia, centrosLogisticos.get(centrosLogisticos.size()-1)));
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
    
    
    
    // Devielve ArrayList<Paso> ruta
    private void generaRuta(String origen, String destino){
        // Crear resultado ArrayList<Paso> ruta
        
        Oficina oficinaOrig = oficinas.get(origen);
        Oficina oficinaDest = oficinas.get(destino);
        if(oficinaOrig != null && oficinaDest != null){
            
            // Caso 1 : Misma provincia
            if(oficinaOrig == oficinaDest){
                // asignar a ruta la oficinaOrig
            }else{
                // Caso 2 : Distinta provincia y mismo centro
                CentroLogistico centroOrig = oficinaOrig.getCentroAsociado();
                CentroLogistico centroDest = oficinaDest.getCentroAsociado();
                if(centroOrig == centroDest){
                    // asignar a ruta la oficinaOrig, centroOrig, oficinaDest
                }else{ // Caso 3 : Distinta provincia y varios centros
                    // Algoritmo de Dijsktra
                }
            }
            
                
            
            // Empezar algoritmo de creacion de ruta
        }else{
            throw new RuntimeException("Error al generar envío. Los cliente tienen una localización no valida");
        }
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
                transformarEnObjetos(centro, i);
            }
            
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
     * @return Pair<String, Integer> con el identificador y el precio
     */
    public Pair<String, Integer> creaEnvio(int alto,int ancho,int peso, Cliente origen, Cliente destino){
        String id = generaId();
        envios.put( id, new Envio(id, alto, ancho, peso, origen, destino));
        return new Pair<String, Integer>(id, envios.get(envios.size()-1).calculaPrecio());
    }
    
    
}
