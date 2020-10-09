package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Oficina;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

/**
 *
 * @author sjm00010
 */
@Component
public class ServicioMensajeria {

    //          Repositorio
    private ArrayList<Oficina> oficinas;
    private ArrayList<CentroLogistico> centrosLogisticos;
    private ArrayList<Envio> envios;

    public ServicioMensajeria() {
        this.oficinas = new ArrayList<>();
        this.centrosLogisticos = new ArrayList<>();
        this.envios = new ArrayList<>();
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
        
        centrosLogisticos.add( new CentroLogistico(id, nombre, localizacion));
        
        // Obtengo el conjunto de conexiones
        JSONArray provincias = (JSONArray) centro.get("provincias");
        for (int i = 0; i < provincias.size(); i++) {
            oficinas.add(new Oficina(provincias.get(i).toString(), centrosLogisticos.get(centrosLogisticos.size()-1)));
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

}
