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
        if(ruta.toString().toUpperCase().contains("RECYCLE.BIN"))
            return;
        
        String strPesoArchivo = "";
        String strArchivosEnCarpeta = "";
        String strPresentador = null;
        
        boolean esArchivo = false;
        boolean anteriorEsCarpeta = false;
        File indiceActual = null;
        
        File[] listaArchivos=ruta.listFiles();
        try{            
            for(int x=0;x<listaArchivos.length;x++){
                indiceActual = listaArchivos[x];
                esArchivo = indiceActual.isFile();
                
                if(Configuraciones.mostrarRutaCompleta){
                    strPresentador = ruta.getAbsolutePath() + File.separator;
                }else{
                    strPresentador = sangrar(profundidad, esArchivo);
                }
                
                if(esArchivo){
                    if(Configuraciones.mostrarArchivos){
                        if(Configuraciones.mostrarPesoArchivos){
                            strPesoArchivo = " <[" + Util.obtenerPeso(indiceActual.length()) + "]>";
                        }
                        
                        if(anteriorEsCarpeta)
                            contenido.add(definirNivel(profundidad - 1));
                            
                        contenido.add(strPresentador + indiceActual.getName() + strPesoArchivo);
                    }
                    
                    espacioTotal += (indiceActual.length());
                    archivosTotales++;
                    
                    anteriorEsCarpeta = false;
                }else{
                    try{
                        if(Configuraciones.mostrarCantidadArchivosPorCarpeta){
                            //strArchivosEnCarpeta = " [A:" + String.valueOf(indiceActual.list().length) + "]";
                            strArchivosEnCarpeta = getContenidoFile(indiceActual);
                        }

                        if(profundidad == 0){
                            contenido.add("");
                            contenido.add("");
                        }else if(profundidad == 1){
                            contenido.add("|");
                        }else if(profundidad > 1){
                            contenido.add(definirNivel(profundidad - 1));
                        }
                        
                        contenido.add(
                            "<" + strPresentador + ">" +
                            "<<" + indiceActual.getName() + ">>" +
                            //strPesoArchivo + 
                            strArchivosEnCarpeta
                        );
                        
                        iterar(indiceActual,profundidad+1);
                        carpetasTotales++;
                    }catch(Exception e){
                        // La Carpeta Esta Vacia
                    }
                    
                    anteriorEsCarpeta = true;
                }
                
            }
        }catch(Exception e){
            contenido.add("ERROR_ITERADOR [" + ruta.toString() + "]: " + e);
        }
    }
    
    private String getContenidoFile(File file){
        String contenido = " [";
        
        int carpetas = 0;
        int archivos = 0;
        
        for(File f: file.listFiles()){
            if(f.isDirectory())
                carpetas++;
            else
                archivos++;
        }
        
        if(carpetas != 0){
            contenido += "C:" + carpetas;
            
            if(archivos != 0)
                contenido += ", A:" + archivos;
        }else{
            if(archivos != 0)
                contenido += "A:" + archivos;
            else
                // Al buscar en el archivo de texto las dobles comillas (") se descubriran las carpetas vacias
                contenido += "\""; 
        }
        
        contenido += "]";        
        
        return contenido;
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
        
        for(int i=0; i<espacios+1; i++){
            ubicacion+=espaciador+" ";
        }
        
        return ubicacion;
    }
    
    private String definirNivel(int nivel){
        String nivelador = null;
        String espaciador = "";
        String guiones = "";
        
        for(int i=0; i < nivel; i++){
            espaciador += "  ";
            guiones += "-";
        }
        
        nivelador = espaciador + "[" + guiones + "]";
    
        return nivelador;
    }
    // </editor-fold>
}
