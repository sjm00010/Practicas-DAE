package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;

/**
 * Entidad Repartidor, solo sirve para representar el punto de la ruta del repartidor
 * @author sjm00010
 */
public class Repartidor implements PuntoControl{
    private String id;

    /*  Es informacion irrelevante, solo sirve para identificar que que punto es 
        de tipo repartidor */
    public Repartidor() {
        this.id="Repartidor";
    }

    @Override
    public String getId() {
        return this.id;
    }
}
