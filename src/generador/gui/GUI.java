package generador.gui;

/**
 *
 * @author Nekio
 */

// <editor-fold defaultstate="collapsed" desc="Librerias">
import generador.Generador;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
// </editor-fold>

public class GUI extends JFrame implements DropTargetListener{
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private Generador generador;
    private DropTarget dragNDrop;
    
    private JTextField txtRuta;
    private JTextField txtNombreArchivo;
    private JCheckBox chkCantidadArchivosPorCarpeta;
    private JCheckBox chkArchivos;
    private JCheckBox chkPesoArchivos;
    private JCheckBox chkNiveles;
    private JCheckBox chkRutaCompleta;
    private JButton btnRuta;
    private JButton btnAceptar;
    private JButton btnSalir;
    private JRadioButton rdSencillo;
    private JRadioButton rdCompleto;
    private JRadioButton rdPersonalizado;
    
    private String ultimaRuta;
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public GUI(){
        activar();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos GUI">
    private void activar(){
        cargarComponentes();
        cargarEscuchadores();
        
        inicializar();
    }
    
    private void cargarComponentes(){
        this.setTitle("Generador de Reportes de Archivos");
        this.setSize(600,300);
        this.setMinimumSize(new Dimension(500,200));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        Container contenedor=getContentPane();
        contenedor.add(getPanelNorte(), "North");
        contenedor.add(getPanelCentro(), "Center");
        contenedor.add(getPanelSur(), "South");        
    }
    
    private void cargarEscuchadores(){
        rdSencillo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                leerPerfil();
            }
        });
        
        rdCompleto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                leerPerfil();
            }
        });
        
        rdPersonalizado.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                leerPerfil();
            }
        });
        
        chkRutaCompleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                
                chkNiveles.setEnabled(!selected);
            }
        });
        
        chkNiveles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                
                chkRutaCompleta.setEnabled(!selected);
            }
        });
        
        btnRuta.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                leerArchivo();
            }
        });
        
        btnAceptar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                aceptar();
            }
        });
        
        btnSalir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent evt){        
                salir();
            }
        });
        
        addWindowListener( new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent evt){
                salir();
            }
        });
    }
    
    private void inicializar(){
        ultimaRuta = "";
        
        txtRuta.setText(ultimaRuta);
        btnAceptar.setEnabled(false);
        
        setVisible(true); 
    }
    
    private void leerPerfil(){
        boolean sencillo = rdSencillo.isSelected();
        boolean completo = rdCompleto.isSelected();
        boolean personalizado = rdPersonalizado.isSelected();
        
        chkCantidadArchivosPorCarpeta.setEnabled(personalizado);
        chkArchivos.setEnabled(personalizado);
        chkPesoArchivos.setEnabled(personalizado);
        chkNiveles.setEnabled(personalizado);
        chkRutaCompleta.setEnabled(personalizado);
        
        if(personalizado){
            chkCantidadArchivosPorCarpeta.setSelected(false);
            chkArchivos.setSelected(false);
            chkPesoArchivos.setSelected(false);
            chkNiveles.setSelected(false);
            chkRutaCompleta.setSelected(false);
        }else if(sencillo){
            chkCantidadArchivosPorCarpeta.setSelected(true);
            chkArchivos.setSelected(false);
            chkPesoArchivos.setSelected(true);
            chkNiveles.setSelected(false);
            chkRutaCompleta.setSelected(false);
        }else if(completo){
            chkCantidadArchivosPorCarpeta.setSelected(true);
            chkArchivos.setSelected(true);
            chkPesoArchivos.setSelected(true);
            chkNiveles.setSelected(false);
            chkRutaCompleta.setSelected(true);
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Paneles">
    private JPanel getPanelNorte(){        
        // Ruta
        JPanel pnlRuta=new JPanel(new BorderLayout());
        pnlRuta.add(new JLabel(" Ruta:              "), "West");
        
        txtRuta = new JTextField();
        pnlRuta.add(txtRuta,"Center");
        dragNDrop=new DropTarget(txtRuta, this);
        
        btnRuta = new JButton("...");
        pnlRuta.add(btnRuta,"East");
        
        // Archivo
        JPanel pnlArchivo=new JPanel(new BorderLayout());
        pnlArchivo.add(new JLabel(" Nombre Archivo: "), "West");
        
        txtNombreArchivo = new JTextField();
        pnlArchivo.add(txtNombreArchivo,"Center");
        
        // Panel Norte
        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.add(pnlRuta, "North");
        pnlNorte.add(pnlArchivo, "South");
        
        return pnlNorte;
    }
    
    private JPanel getPanelCentro(){   
        // Perfiles
        JPanel pnlPerfiles=new JPanel(new GridLayout(1,3));
        pnlPerfiles.setBackground(java.awt.Color.LIGHT_GRAY);
        
        rdSencillo = new JRadioButton("Sencillo");
        rdCompleto = new JRadioButton("Completo");
        rdPersonalizado = new JRadioButton("Personalizado");
        
        rdSencillo.setOpaque(false);
        rdCompleto.setOpaque(false);
        rdPersonalizado.setOpaque(false);
        
        rdPersonalizado.setSelected(true);
        
        ButtonGroup grupoRadios = new ButtonGroup();
        grupoRadios.add(rdSencillo);
        grupoRadios.add(rdCompleto);
        grupoRadios.add(rdPersonalizado);
        
        pnlPerfiles.add(rdSencillo);
        pnlPerfiles.add(rdCompleto);
        pnlPerfiles.add(rdPersonalizado);
        
        // Configuraciones
        JPanel pnlConfiguraciones =new JPanel(new GridLayout(3,2));
        
        chkCantidadArchivosPorCarpeta = new JCheckBox("Incluir Cantidad de Archivos por Carpeta");
        chkArchivos = new JCheckBox("Incluir Archivos");
        chkPesoArchivos = new JCheckBox("Incluir Pesos");
        chkNiveles = new JCheckBox("Incluir Nivel de profundidad");
        chkRutaCompleta = new JCheckBox("Incluir Ruta Completa");
          
        pnlConfiguraciones.add(chkCantidadArchivosPorCarpeta);
        pnlConfiguraciones.add(chkArchivos);
        pnlConfiguraciones.add(chkPesoArchivos);
        pnlConfiguraciones.add(chkNiveles);
        pnlConfiguraciones.add(chkRutaCompleta);
        
        // Panel Centro
        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.add(pnlPerfiles, "North");
        pnlCentro.add(pnlConfiguraciones, "Center");
        
        return pnlCentro;
    }
    
    private JPanel getPanelSur(){        
        JPanel pnlSur=new JPanel(new GridLayout());
        
        btnAceptar = new JButton("Aceptar");
        btnSalir = new JButton("Salir");
    
        pnlSur.add(new JLabel());
        pnlSur.add(btnAceptar);
        pnlSur.add(btnSalir);
        
        getRootPane().setDefaultButton(btnAceptar);
        
        return pnlSur;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos de implementacion Drag N' Drop">
    @Override public void dragEnter(DropTargetDragEvent dtde){}
    @Override public void dragOver(DropTargetDragEvent dtde){}
    @Override public void dropActionChanged(DropTargetDragEvent dtde){}
    @Override public void dragExit(DropTargetEvent dte){}

    @Override 
    public void drop(DropTargetDropEvent evento){
        try {
            Transferable transferencia = evento.getTransferable(); 
            DataFlavor[] metadatosTransfer = transferencia.getTransferDataFlavors();
            ArrayList<File> archivos= new ArrayList<>();
            
            for(int i=0;i<metadatosTransfer.length;i++){
                if(metadatosTransfer[i].isFlavorJavaFileListType()){
                    evento.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    
                    List lista=(List)transferencia.getTransferData(metadatosTransfer[i]);
                    for(int j=0; j < lista.size(); j++){
                        archivos.add((File)lista.get(j));
                    }

                    evento.dropComplete(true);             
                    definirRuta(archivos.get(0));
                    
                    return;
                }
            }
            
            evento.rejectDrop();
        }catch (UnsupportedFlavorException | IOException e) {
            evento.rejectDrop();
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Comportamiento">
    private void definirRuta(File archivo){
        if(archivo.isFile())
            ultimaRuta = archivo.getParent();
        else
            ultimaRuta = archivo.getAbsolutePath();
        
        txtRuta.setText(ultimaRuta);
        btnAceptar.setEnabled(true);
    }
    
    private void leerArchivo(){
        JFileChooser exploradorArchivos = new JFileChooser(ultimaRuta);
        exploradorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        int resultado = exploradorArchivos.showSaveDialog(null);
        if (resultado == JFileChooser.CANCEL_OPTION) 
            return;
        else{
            definirRuta(exploradorArchivos.getSelectedFile());
        }
    }
    
    private boolean validar(){
        boolean ok = true;
        
        if(txtRuta.getText().length() == 0){
            ok = false;
            mostrarMensaje("Falta definir una Ruta");
        }else if(!new File(txtRuta.getText()).isDirectory()){
            ok = false;
            mostrarMensaje("La Ruta proporcionada no es correcta");
        }
        
        if(txtNombreArchivo.getText().length() == 0){
            ok = false;
            mostrarMensaje("Falta definir Nombre de Archivo");
        }
        
        return ok;
    }
    
    private void aceptar(){
        if(validar()){
            String ruta = txtRuta.getText();
            String nombreArchivo = txtNombreArchivo.getText();

            boolean archivosPorCarpeta = chkCantidadArchivosPorCarpeta.isSelected();
            boolean archivos = chkArchivos.isSelected();
            boolean pesoArchivos = chkPesoArchivos.isSelected();
            boolean niveles = chkNiveles.isSelected();
            boolean rutaCompleta = chkRutaCompleta.isSelected();

            generador = new Generador(ruta, nombreArchivo);
            generador.configurar(archivosPorCarpeta, archivos, pesoArchivos, niveles, rutaCompleta);
            generador.generar();
        }
    }
    
    private void salir(){
        setVisible(false);
        dispose();
    }
    
    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    // </editor-fold>
}
