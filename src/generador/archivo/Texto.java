package generador.archivo;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import generador.contract.Archivo;
import generador.common.Iterador;
import generador.common.Util;
import generador.gui.GUI;
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
        
        String estadisticas=
            "Ruta de la carpeta raiz: " + ruta +
            "\nTotal de Archivos: " + iterador.getArchivosTotales() +
            "\nTotal de Carpetas: " + iterador.getCarpetasTotales() +
            "\nEspacio ocupado en Disco Duro: " + Util.obtenerPeso(iterador.getEspacioTotal());

        String lineas[]=estadisticas.split("\n");
        int x=0;
        for(; x<lineas.length; x++) 
            contenido.add(x,lineas[x]);
        lineas=null;
        contenido.add(x,"");
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo));
            for(int i=0;i<contenido.size();i++){
                bw.write(contenido.get(i));
                bw.newLine();
            }
            
            bw.close();
            bw=null;
            
            archivoCreado = true;
        }catch(Exception e){
            GUI.mostrarMensaje("ERROR_BUFFER: " + e);
        }

        contenido = null;
        iterador = null;

        return archivoCreado;
    }
    // </editor-fold>
}
