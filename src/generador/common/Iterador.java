package generador.common;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import java.io.File;
import java.util.ArrayList;
import java.util.List;
// </editor-fold>

public class Iterador {
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private List<String> contenido;
    
    private long espacioTotal;
    private int carpetasTotales;
    private int archivosTotales;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public Iterador(){
        contenido = null;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Encapsulados">
    public long getEspacioTotal() {
        return espacioTotal;
    }

    public int getCarpetasTotales() {
        return carpetasTotales;
    }

    public void setCarpetasTotales(int carpetasTotales) {
        this.carpetasTotales = carpetasTotales;
    }

    public int getArchivosTotales() {
        return archivosTotales;
    }
    
    public List<String> getContenido(File ruta){
        inicializar();
        
        contenido.add(ruta.toString());
        iterar(ruta, 0);
        
        return contenido;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    private void inicializar(){
        contenido = new ArrayList<String>();
        
        espacioTotal = 0L;
        carpetasTotales = 0;
        archivosTotales = 0;
    }
    
    private void iterar(File ruta,int profundidad){
        String strPesoArchivo = "";
        String strArchivosEnCarpeta = "";
        String strPresentador = null;
        
        File[] listaArchivos=ruta.listFiles();
        try{
            for(int x=0;x<listaArchivos.length;x++){
                if(Configuraciones.mostrarPesoArchivos){
                    strPesoArchivo = " [" + Util.obtenerPeso(listaArchivos[x].length()) + "]";
                }
                
                boolean esArchivo = listaArchivos[x].isFile();
                
                if(Configuraciones.mostrarRutaCompleta){
                    strPresentador = ruta.getAbsolutePath() + File.separator;
                }else{
                    strPresentador = sangrar(profundidad, esArchivo);
                }
                
                if(esArchivo){
                    if(Configuraciones.mostrarArchivos){
                        contenido.add(strPresentador + listaArchivos[x].getName() + strPesoArchivo);
                        espacioTotal += ((listaArchivos[x].length()));
                        archivosTotales++;
                    }
                }else{
                    if(Configuraciones.mostrarCantidadArchivosPorCarpeta){
                        strArchivosEnCarpeta = " [C:" + String.valueOf(listaArchivos[x].list().length) + "]";
                    }
                    
                    contenido.add(strPresentador + listaArchivos[x].getName() + strPesoArchivo + strArchivosEnCarpeta);
                    iterar(listaArchivos[x],profundidad+1);
                    carpetasTotales++;
                }
            }
        }catch(Exception e){}
    }
    
    private String sangrar(int espacios, boolean esArchivo){
        String strProfundidad = "";
        
        char espaciador;
        if(esArchivo)
            espaciador = '-';
        else
            espaciador = '=';
        
        if(Configuraciones.mostrarNiveles){
            if(espacios < 10)
                strProfundidad = "<0" + String.valueOf(espacios) + ">";
            else
                strProfundidad = "<" + String.valueOf(espacios) + ">";
        }
        
        String ubicacion="|" + strProfundidad;
        
        for(int i=0;i<espacios+1;i++){
            ubicacion+=espaciador+" ";
        }
        
        return ubicacion;
    }
    // </editor-fold>
}