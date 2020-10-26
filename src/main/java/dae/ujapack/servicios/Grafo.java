package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import dae.ujapack.entidades.Oficina;
import dae.ujapack.entidades.Paso;
import dae.ujapack.errores.IdPuntoControlInvalido;
import java.time.LocalDate;
import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Servicio para la generación de la ruta
 * @author sjm00010
 */
@Service
public class Grafo {
    
    // Grafo para calcular la ruta
    private Graph<String, DefaultEdge> directedGraph;

    public Grafo() {
       this.directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
    
    /**
     * Función que genera el grafo para calcular el camino mínimo
     * @param nodos ArrayList<CentroLogistico> Nodos del grafo
     */
    public void generaGrafo(ArrayList<CentroLogistico> nodos){
        // Añado los nodos        
        Graphs.addAllVertices(directedGraph, nodos.stream()
                                                    .map(nodo -> nodo.getId())
                                                    .collect(Collectors.toList()));
        
        // Añado las conexiones entre los nodos
        nodos.forEach(nodo -> nodo.getConexiones().forEach(conexion -> 
                directedGraph.addEdge(nodo.getId(), String.valueOf(conexion))
        ));
    }
    
    /**
     * Funcion que devuelve la ruta calculada, centros logisticos por los que pasa el envío
     * @param origen ID del nodo origen
     * @param destino ID del nodo destino
     * @return Lista de centros de la ruta calculada
     */
    private List<String> obtenRuta(String origen, String destino){    
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
            new DijkstraShortestPath<>(directedGraph);
        SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(origen);
        return iPaths.getPath(destino).getVertexList();
    }
    
    /**
     * Función que genera la ruta que ha de seguir el envio
     * @param origen Oficina origen
     * @param destino Oficina destino
     * @return ArrayList<Paso> Ruta calculada
     */
    public ArrayList<Paso> generaRuta(Oficina origen, Oficina destino, 
            Map<String, CentroLogistico> centros){
        ArrayList<Paso> ruta = new ArrayList<>();
        if(origen != null && destino != null){
            
            // Caso 1 : Misma provincia (BASE PARA TODOS LOS CASOS)
            ruta.add(new Paso(origen, false, LocalDate.now()));
            ruta.add(new Paso(origen, true));
            
            if(!origen.equals(destino)){
                CentroLogistico centroOrig = origen.getCentroAsociado();
                CentroLogistico centroDest = destino.getCentroAsociado();
                
                if(centroOrig.equals(centroDest)){// Caso 2 : Distinta provincia y mismo centro
                    ruta.add(new Paso(centroOrig,false));
                    ruta.add(new Paso(centroOrig,true));
                    ruta.add(new Paso(destino,false));
                    ruta.add(new Paso(destino,true));
                }else{ // Caso 3 : Distinta provincia y varios centros  
                    
                    // Calculo y añado los centros logisticos por los que pasa
                    List<String> centrosRuta = this.obtenRuta(centroOrig.getId(), centroDest.getId());
                    for (String idCentro : centrosRuta) {
                        ruta.add(new Paso(centros.get(idCentro), false));
                        ruta.add(new Paso(centros.get(idCentro), true));
                    }
                    
                    // Añado el destino
                    ruta.add(new Paso(destino, false));
                    ruta.add(new Paso(destino, true));
                }
            }
        }else{
            throw new IdPuntoControlInvalido("Error al generar envío. Los clientes tienen una localización no valida");
        }
        
        return ruta;
    }
}
