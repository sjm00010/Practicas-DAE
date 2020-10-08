package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Oficina;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 *
 * @author sjm00010
 */
public class ServicioMensajeria {

    // Repositorio
    private ArrayList<Oficina> oficinas;
    private ArrayList<CentroLogistico> centrosLogisticos;
    private ArrayList<Envio> envios;

    // Servicio
    // Funciones auxiliares
    private static void transformarEnObjetos(JSONObject centro, int i) {
        // Obtiene un objeto centro de la lista
        JSONObject employeeObject = (JSONObject) centro.get(Integer.toString(i+1));

        // Obtengo el nombre del centro
        String nombre = (String) centro.get("nombre");
        System.out.println(nombre);

        // Obtengo la localización del centro
        String localización = (String) centro.get("localización");
        System.out.println(localización);

        // Obtengo el conjunto de conexiones
        JSONArray conexionesTemp = (JSONArray) centro.get("conexiones");
        ArrayList<Integer> conexiones;
        
        // Itero sobre las conexiones
        Iterator<JSONObject> iterator = conexionesTemp.iterator();
        while (iterator.hasNext()) {
            conexiones.add(Integer.parseInt(iterator.next()));
            
        }
    }

    /**
     * Función que carga los datos del fichero json
     *
     * @param ruta Ruta del fichero .json
     */
    public void cargaDatos(String ruta) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(ruta)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray listaCentros = (JSONArray) obj;
            System.out.println(listaCentros);

            //Iterate over employee array
            listaCentros.forEach(
                emp -> transformarEnObjetos((JSONObject) emp, centrosLogisticos.size())
            );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
