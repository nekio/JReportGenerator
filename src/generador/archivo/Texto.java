package generador.archivo;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import generador.contract.Archivo;
import generador.common.Iterador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
// </editor-fold>

public class Texto implements Archivo{
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private List<String> contenido;
    private String rutaArchivo;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Contrato">
    @Override
    public String getExtension() {
        return ".txt";
    }
    
    @Override
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    @Override
    public boolean crear(String ruta, String nombreArchivo) {
        boolean archivoCreado = false;
        
        Iterador iterador = new Iterador();
        contenido = iterador.getContenido(new File(ruta));
        
        rutaArchivo = ruta + nombreArchivo;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo));
            for(int i=0;i<contenido.size();i++){
                bw.write(contenido.get(i));
                bw.newLine();
            }
            bw.close();
            bw=null;
            
            archivoCreado = true;
        }catch(Exception e){}

        contenido = null;
        iterador = null;

        return archivoCreado;
    }
    // </editor-fold>
}
