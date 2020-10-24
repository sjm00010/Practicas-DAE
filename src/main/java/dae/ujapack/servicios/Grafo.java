package dae.ujapack.servicios;

import dae.ujapack.entidades.CentroLogistico;
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
    public List<String> obtenRuta(String origen, String destino){    
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg =
            new DijkstraShortestPath<>(directedGraph);
        SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(origen);
        return iPaths.getPath(destino).getVertexList();
    }
}
