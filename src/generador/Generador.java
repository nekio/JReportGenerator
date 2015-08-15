package generador;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import generador.contract.Archivo;
import generador.archivo.Excel;
import generador.archivo.Texto;
import generador.common.Configuraciones;
import generador.enums.TipoSalida;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
// </editor-fold>

public class Generador {
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); 
    private String PREFIJO = "Reporte de ";
    
    private Archivo archivo;
    private String ruta;
    private String nombreReporte;
    private TipoSalida tipoSalida;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public Generador(String ruta, String nombreReporte){
        this(ruta, nombreReporte, TipoSalida.TEXTO_PLANO);
    }
    
    public Generador(String ruta, String nombreReporte, TipoSalida tipoSalida){
        String separador = File.separator;
        
        if(ruta.endsWith(separador))
            this.ruta = ruta;
        else
            this.ruta = ruta + separador;
        
        this.nombreReporte = nombreReporte;
        this.tipoSalida = tipoSalida;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void configurar(boolean cantidadArchivosPorCarpeta, boolean archivos, boolean pesoArchivos, boolean niveles, boolean rutaCompleta){
        Configuraciones.mostrarCantidadArchivosPorCarpeta = cantidadArchivosPorCarpeta;
        Configuraciones.mostrarArchivos = archivos;
        Configuraciones.mostrarPesoArchivos = pesoArchivos;
        Configuraciones.mostrarNiveles = niveles;
        Configuraciones.mostrarRutaCompleta = rutaCompleta;
    }
    
    public boolean generar(){
        boolean resultado = false;
        
        switch(tipoSalida){
            case TEXTO_PLANO:
                archivo = new Texto();
                break;
            case EXCEL:
                archivo = new Excel();
                break;
        }
        
        String fecha = "[" + DATE_FORMAT.format(Calendar.getInstance().getTime()) + "] ";
        String nombreArchivo = fecha + PREFIJO + nombreReporte + archivo.getExtension();
            
        if(archivo.crear(ruta, nombreArchivo)){
            try{
                Desktop.getDesktop().open(new File(archivo.getRutaArchivo()));
                resultado = true;
            }catch(Exception e){}
        }
    
        return resultado;
    }
    // </editor-fold>
}
