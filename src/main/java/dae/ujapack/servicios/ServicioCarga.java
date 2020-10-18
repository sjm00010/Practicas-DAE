package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Oficina;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

/**
 * Servicio que carga los datos de un archivo json
 * @author sjm00010
 */
@Service
public class ServicioCarga {
    private String ruta; // Ruta del archivo .json
    private int numCentros;

    public ServicioCarga() {
        this.ruta = "D:\\DESCARGAS\\redujapack.json";
        this.numCentros = 10;
    }
    
    
    /**
     * Función que crea los centros y oficinas a partir de la información leída del json
     * @param centro JSONObject con la información del centro a crear
     * @param i Número del centro
     */
    private void transformarEnObjetos(JSONObject centro, String id, 
            Map<String, CentroLogistico> centrosLogisticos, Map<String, Oficina> oficinas) {

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
     * Función que carga los datos del fichero json
     *
     * @param ServicioMensajeria Servicio al que ha de cargar los datos
     */
    public void cargaDatos(ServicioMensajeria sm) {
        // Variables
        Map<String, Oficina> oficinas = new HashMap<>();
        Map<String, CentroLogistico> centrosLogisticos = new HashMap<>();
        Grafo grafo = new Grafo();
        
        
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), "UTF-8"));) {
            System.out.println("Archivo leido");
            
            // Leo el archivo JSON
            JSONObject listaCentros = (JSONObject) jsonParser.parse(reader);

            // Recorro todos los centros logisticos
            for (int i = 1; i <= numCentros; i++) {
                JSONObject centro = (JSONObject) jsonParser.parse(listaCentros.get(Integer.toString(i)).toString());
                transformarEnObjetos(centro, Integer.toString(i), centrosLogisticos, oficinas);
            }
            
            // Aprovecho que ya tengo lso centros para generar el grafo para la ruta
            grafo.generaGrafo((ArrayList<CentroLogistico>) centrosLogisticos.values().stream().collect(Collectors.toList()));
            
            // Prueba
            System.out.println("Centros leidos : "+centrosLogisticos.size());
            
            sm.setCentrosLogisticos(centrosLogisticos);
            sm.setOficinas(oficinas);
            sm.setGrafo(grafo);
            
            System.out.println("Centros y Oficinas cargadas");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
}
