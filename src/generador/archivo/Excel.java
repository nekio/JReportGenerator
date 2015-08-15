package generador.archivo;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import generador.contract.Archivo;
// </editor-fold>

public class Excel implements Archivo{
    // <editor-fold defaultstate="collapsed" desc="Contrato">
    @Override
    public String getExtension() {
        return ".xls";
    }
    
    @Override
    public String getRutaArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean crear(String ruta, String nombreArchivo) {
        boolean archivoCreado = false;
        
        return archivoCreado;
    }
    // </editor-fold>
}
