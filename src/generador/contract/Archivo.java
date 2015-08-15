package generador.contract;

/**
 *
 * @author Nekio
 */

public interface Archivo {
    public boolean crear(String ruta, String nombreArchivo);
    
    public String getRutaArchivo();
    public String getExtension();
}
