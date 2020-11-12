package dae.ujapack.tuplas;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import dae.ujapack.entidades.puntosControl.Oficina;
import java.util.Map;

/**
 * Clase para almacenar la tupla de Oficinas y Centros Logisticos cargados del fichero
 * @author sjm00010
 */
public class OficinasCentrosServicioCarga {
    private Map<String, Oficina> oficinas;
    private Map<String, CentroLogistico> centros;

    public OficinasCentrosServicioCarga(Map<String, Oficina> oficinas, Map<String, CentroLogistico> centros) {
        this.oficinas = oficinas;
        this.centros = centros;
    }

    /**
     * @return the oficinas
     */
    public Map<String, Oficina> getOficinas() {
        return oficinas;
    }

    /**
     * @return the centros
     */
    public Map<String, CentroLogistico> getCentros() {
        return centros;
    }

    
}
