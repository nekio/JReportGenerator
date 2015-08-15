package generador.common;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import java.text.DecimalFormat;
// </editor-fold>

public class Util {
    public static String obtenerPeso(long bytes){
        DecimalFormat df = new DecimalFormat("###,##0.00");
        double peso=0;
        String medida=null;
        String tamanio=null;
        
        if(bytes < 1024){
            peso=bytes;
            medida=" B";
        }if(bytes >= 1024){
            peso=(double)bytes/1024;
            medida=" KB";
        }if(bytes >= 1024*1024){
            peso=(double)bytes/1024/1024;
            medida=" MB";
        }if(bytes >= 1024*1024*1024){
            peso=(double)bytes/1024/1024/1024;
            medida=" GB";
        }
        
        tamanio=String.valueOf(df.format(peso));
        tamanio=tamanio.replace(".00","");
        
        return tamanio+medida;
    }
}
