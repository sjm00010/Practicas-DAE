package dae.ujapack.utils;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import dae.ujapack.entidades.puntosControl.Oficina;
import dae.ujapack.utils.tuplas.OficinasCentrosServicioCarga;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servicio que carga los datos de un archivo json
 *
 * @author sjm00010
 */
public class CargaDatos {

    private String ruta; // Ruta del archivo .json
    private int numCentros; // Número de centros que contiene el archivo

    public CargaDatos() {
        this.ruta = System.getProperty("user.dir") + "/redujapack.json";
        this.numCentros = 10;
    }

    /**
     * Función que crea los centros y oficinas a partir de la información leída
     * del json
     *
     * @param centro JSONObject con la información del centro a crear
     * @param id Id del centro
     * @param centrosLogisticos Mapa a rellenar con los centros
     * @param oficinas Mapa a rellenar con las oficinas
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
        conexionesTemp.forEach(conexion -> conexiones.add(conexion.toString()));

        // Creo el centro y lo añado
        centrosLogisticos.put(id, new CentroLogistico(id, nombre, localizacion, conexiones));

        // Obtengo el conjunto de provincias
        JSONArray provincias = (JSONArray) centro.get("provincias");
        provincias.forEach(provincia -> oficinas.put(provincia.toString(), new Oficina(provincia.toString(), centrosLogisticos.get(id))));
    }

    /**
     * Función que carga los datos del fichero json
     *
     * @param ServicioMensajeria Servicio al que ha de cargar los datos
     */
    public OficinasCentrosServicioCarga cargaDatos() {
        // Variables
        Map<String, Oficina> oficinas = new HashMap<>();
        Map<String, CentroLogistico> centrosLogisticos = new HashMap<>();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), "UTF-8"));) {

            // Leo el archivo JSON
            JSONObject listaCentros = (JSONObject) jsonParser.parse(reader);

            // Recorro todos los centros logisticos
            for (int i = 1; i <= numCentros; i++) {
                JSONObject centro = (JSONObject) jsonParser.parse(listaCentros.get(Integer.toString(i)).toString());
                transformarEnObjetos(centro, Integer.toString(i), centrosLogisticos, oficinas);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new OficinasCentrosServicioCarga(oficinas, centrosLogisticos);
    }
}
