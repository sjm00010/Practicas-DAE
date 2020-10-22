package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author sjm00010
 */
@Service
public class Grafo {
    
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
        for (CentroLogistico nodo : nodos) {
            directedGraph.addVertex(nodo.getId());
        }
        
        // Añado las conexiones entre los nodos
        for (CentroLogistico nodo : nodos) {
            ArrayList<String> conexiones = nodo.getConexiones();
            for (int i = 0; i < conexiones.size(); i++) {
                directedGraph.addEdge(nodo.getId(), String.valueOf(conexiones.get(i)));
            }
        }
    }
    
    /**
     * Funcion que devuelve la ruta calculada, centros logisticos por los que pasa el envío
     * @param origen ID del nodo origen
     * @param destino ID del nodo destino
     * @return Lista de centros de la ruta calculada
     */
    public List<String> obtenRuta(String origen, String destino){    
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
            new DijkstraShortestPath<>(directedGraph);
        SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(origen);
        return iPaths.getPath(destino).getVertexList();
    }
}
