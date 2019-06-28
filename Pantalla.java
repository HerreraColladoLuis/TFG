
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author herre
 */
public class Pantalla extends javax.swing.JFrame {

    private List<String> entrada = new LinkedList<>();
    private List<List<Analizador.Estado>> estadoEntrada = new LinkedList<>();
    private List<String> lER;
    private List<String> lAuxER;
    private List<String> lAuxMacro;
    private List<Analizador.Estado> lEaux;
    private List<Analizador.Estado> lE = new LinkedList<>();
    private List<Integer> lnER = new LinkedList<>();
    private int ini = -1;
    private int fin = -1;
    private int auxini = -1;
    private List<Integer> auxl = new LinkedList<>();
    private List<Integer> aux2 = new LinkedList<>();
    private boolean anteriorFinal = false;
    private boolean anteriorNoRec = false;
    private boolean anteriorBorrado = false;
    private int ind = -1;
    private P_ajustes pAjustes;
    private List<Font> l_fuentes = new LinkedList<>();
    private List<Color> l_colores = new LinkedList<>();
    private List<Font> l_fuentes_mod = new LinkedList<>();
    private List<Color> l_colores_mod = new LinkedList<>();

    public List<Font> getL_fuentes_mod() {
        return l_fuentes_mod;
    }

    public List<Color> getL_colores_mod() {
        return l_colores_mod;
    }
    
    public List<Font> getL_fuentes() {
        return l_fuentes;
    }

    public List<Color> getL_colores() {
        return l_colores;
    }

    /**
     * Creates new form Pantalla
     */
    public Pantalla() {
        initComponents();
        
        Color bgColor = new Color(240,241,242);
        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
        panel_activadas.putClientProperty("Nimbus.Overrides", defaults);
        panel_activadas.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        panel_activadas.setBackground(bgColor);
        
        ImageIcon settingsImage = new ImageIcon(getClass().getResource(("/Imágenes/settings.png")));
        Icon iconSettings = new ImageIcon(settingsImage.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.lbSettings.setIcon(iconSettings);
        
        ImageIcon helpImage = new ImageIcon(getClass().getResource(("/Imágenes/help.png")));
        Icon iconHelp = new ImageIcon(helpImage.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.lbHelp.setIcon(iconHelp);
        
        ImageIcon regImage = new ImageIcon(getClass().getResource(("/Imágenes/RegExp.png")));
        Icon iconReg = new ImageIcon(regImage.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
        this.lbReg.setIcon(iconReg);
        
        // Seteamos los colores y las fuentes por defecto
        this.l_fuentes.add(new Font("Tahoma",BOLD,18)); // Fuente ACTIVADAS
        this.l_colores.add(Color.BLACK); // Color de fuente ACTIVADAS
        this.l_colores.add(Color.WHITE); // Color de fondo de fuente ACTIVADAS
        
        this.l_fuentes.add(new Font("Tahoma",PLAIN,16)); // Fuente NO ACTIVADAS
        this.l_colores.add(new Color(109,109,109));
        this.l_colores.add(Color.WHITE);
        
        this.l_fuentes.add(new Font("Verdana",BOLD,18)); // Fuente entrada COMPLETA
        this.l_colores.add(Color.WHITE);
        this.l_colores.add(new Color(25,25,112));
        
        this.l_fuentes.add(new Font("Verdana",BOLD,18)); // Fuente entrada NO activada ni completa
        this.l_colores.add(new Color(109,109,109));
        this.l_colores.add(Color.WHITE);
        
        this.l_fuentes.add(new Font("Verdana",BOLD,18)); // Fuente entrada ACTIVADA
        this.l_colores.add(new Color(109,109,109));
        this.l_colores.add(new Color(173,216,230));
        
        this.l_fuentes_mod.addAll(l_fuentes);
        this.l_colores_mod.addAll(l_colores);
        
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cabecera = new javax.swing.JPanel();
        et_titulo = new javax.swing.JLabel();
        boton_nueva = new javax.swing.JButton();
        lbSettings = new javax.swing.JLabel();
        lbHelp = new javax.swing.JLabel();
        lbReg = new javax.swing.JLabel();
        scroll_panel_expr = new javax.swing.JScrollPane();
        panel_expr = new javax.swing.JTextPane();
        cabecera_entrada = new javax.swing.JPanel();
        et_entrada = new javax.swing.JLabel();
        scroll_panel_entrada = new javax.swing.JScrollPane();
        panel_entrada = new javax.swing.JTextPane();
        cabecera_esp = new javax.swing.JPanel();
        et_especificacion = new javax.swing.JLabel();
        cabecera_act = new javax.swing.JPanel();
        et_act = new javax.swing.JLabel();
        scroll_panel_activadas = new javax.swing.JScrollPane();
        panel_activadas = new javax.swing.JTextPane();
        scroll_panel_especificacion = new javax.swing.JScrollPane();
        panel_especificacion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        cabecera.setBackground(new java.awt.Color(16, 17, 18));

        et_titulo.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        et_titulo.setForeground(new java.awt.Color(112, 176, 224));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        et_titulo.setText(bundle.getString("Pantalla.et_titulo.text")); // NOI18N

        boton_nueva.setToolTipText("Añadir una nueva especificación léxica");
        boton_nueva.setBackground(new java.awt.Color(51, 51, 51));
        boton_nueva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boton_nueva.setForeground(new java.awt.Color(112, 176, 224));
        boton_nueva.setText(bundle.getString("Pantalla.boton_nueva.text")); // NOI18N
        boton_nueva.setBorder(null);
        boton_nueva.setBorderPainted(false);
        boton_nueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_nuevaActionPerformed(evt);
            }
        });

        lbSettings.setToolTipText("Configuración de usuario");
        lbSettings.setForeground(new java.awt.Color(16, 17, 18));
        lbSettings.setText(bundle.getString("Pantalla.lbSettings.text")); // NOI18N
        lbSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSettingsMouseClicked(evt);
            }
        });

        lbHelp.setToolTipText("Ayuda");
        lbHelp.setForeground(new java.awt.Color(16, 17, 18));
        lbHelp.setText(bundle.getString("Pantalla.lbHelp.text")); // NOI18N

        lbReg.setForeground(new java.awt.Color(16, 17, 18));
        lbReg.setText(bundle.getString("Pantalla.lbReg.text")); // NOI18N

        javax.swing.GroupLayout cabeceraLayout = new javax.swing.GroupLayout(cabecera);
        cabecera.setLayout(cabeceraLayout);
        cabeceraLayout.setHorizontalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbReg)
                .addGap(37, 37, 37)
                .addComponent(et_titulo)
                .addGap(54, 54, 54)
                .addComponent(boton_nueva, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lbSettings)
                .addGap(18, 18, 18)
                .addComponent(lbHelp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cabeceraLayout.setVerticalGroup(
            cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_nueva, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(et_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHelp)
                    .addComponent(lbSettings)
                    .addComponent(lbReg))
                .addContainerGap())
        );

        scroll_panel_expr.setBackground(new java.awt.Color(255, 255, 255));
        scroll_panel_expr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        panel_expr.setEditable(false);
        panel_expr.setBorder(null);
        panel_expr.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        panel_expr.setForeground(new java.awt.Color(109, 109, 109));
        panel_expr.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panel_expr.setFocusable(false);
        panel_expr.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                panel_exprHyperlinkUpdate(evt);
            }
        });
        panel_expr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_exprMouseClicked(evt);
            }
        });
        scroll_panel_expr.setViewportView(panel_expr);

        cabecera_entrada.setBackground(new java.awt.Color(112, 176, 224));

        et_entrada.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        et_entrada.setText(bundle.getString("Pantalla.et_entrada.text")); // NOI18N

        javax.swing.GroupLayout cabecera_entradaLayout = new javax.swing.GroupLayout(cabecera_entrada);
        cabecera_entrada.setLayout(cabecera_entradaLayout);
        cabecera_entradaLayout.setHorizontalGroup(
            cabecera_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabecera_entradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_entrada)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cabecera_entradaLayout.setVerticalGroup(
            cabecera_entradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabecera_entradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        scroll_panel_entrada.setBorder(null);

        panel_entrada.setEditable(false);
        panel_entrada.setBorder(null);
        panel_entrada.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        panel_entrada.setForeground(new java.awt.Color(109, 109, 109));
        panel_entrada.setFocusable(false);
        panel_entrada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                panel_entradaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                panel_entradaFocusLost(evt);
            }
        });
        panel_entrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panel_entradaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                panel_entradaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                panel_entradaKeyTyped(evt);
            }
        });
        scroll_panel_entrada.setViewportView(panel_entrada);

        cabecera_esp.setBackground(new java.awt.Color(183, 188, 192));

        et_especificacion.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        et_especificacion.setText(bundle.getString("Pantalla.et_especificacion.text")); // NOI18N

        javax.swing.GroupLayout cabecera_espLayout = new javax.swing.GroupLayout(cabecera_esp);
        cabecera_esp.setLayout(cabecera_espLayout);
        cabecera_espLayout.setHorizontalGroup(
            cabecera_espLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabecera_espLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_especificacion)
                .addContainerGap(519, Short.MAX_VALUE))
        );
        cabecera_espLayout.setVerticalGroup(
            cabecera_espLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cabecera_espLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_especificacion, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        cabecera_act.setBackground(new java.awt.Color(183, 188, 192));

        et_act.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        et_act.setText(bundle.getString("Pantalla.et_act.text")); // NOI18N

        javax.swing.GroupLayout cabecera_actLayout = new javax.swing.GroupLayout(cabecera_act);
        cabecera_act.setLayout(cabecera_actLayout);
        cabecera_actLayout.setHorizontalGroup(
            cabecera_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabecera_actLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_act)
                .addContainerGap(321, Short.MAX_VALUE))
        );
        cabecera_actLayout.setVerticalGroup(
            cabecera_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabecera_actLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(et_act, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        scroll_panel_activadas.setBorder(null);

        panel_activadas.setEditable(false);
        panel_activadas.setBorder(null);
        panel_activadas.setFocusable(false);
        scroll_panel_activadas.setViewportView(panel_activadas);

        scroll_panel_especificacion.setBorder(null);

        panel_especificacion.setEditable(false);
        panel_especificacion.setBackground(new java.awt.Color(240, 241, 242));
        panel_especificacion.setColumns(20);
        panel_especificacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel_especificacion.setRows(5);
        panel_especificacion.setFocusable(false);
        scroll_panel_especificacion.setViewportView(panel_especificacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll_panel_expr, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cabecera_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scroll_panel_entrada)
                    .addComponent(cabecera_esp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cabecera_act, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scroll_panel_activadas)
                    .addComponent(scroll_panel_especificacion)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_panel_expr)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cabecera_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(scroll_panel_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(cabecera_esp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(scroll_panel_especificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(cabecera_act, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(scroll_panel_activadas, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_nuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_nuevaActionPerformed
        String especificacion = null;
        try {
            especificacion = this.abrirArchivo();
        } catch (Exception ex) {
            Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!"".equals(especificacion))
        {
            this.panel_especificacion.setText(especificacion);
            this.panel_especificacion.setCaretPosition(0);
            this.panel_expr.setText("");
            this.panel_entrada.setText(""); // ojo
            this.panel_entrada.setEditable(false);
            this.panel_entrada.setFocusable(false);
            this.ini = -1;
            this.fin = -1;
            this.auxl.clear();
            this.entrada.clear();
            this.estadoEntrada.clear();
            StyledDocument doc1 = this.panel_entrada.getStyledDocument();
            try {
                if (doc1.getLength() > 0)
                    doc1.remove(0, doc1.getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            return;
            // Añadir advertencia de archivo nulo
        }
        this.ind = -1;
        
        if (this.lAuxER.size() > this.lAuxMacro.size()) {
            ind = this.lAuxMacro.size();
        }
        
        StyledDocument doc = this.panel_expr.getStyledDocument();
        Style style = this.panel_expr.addStyle("Style", null);
        StyleConstants.setBackground(style, this.l_colores_mod.get(3));
        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
        //StyleConstants.setForeground(style, new Color(109,109,109));
        StyleConstants.setForeground(style, this.l_colores_mod.get(2));
        //StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontSize(style, this.l_fuentes_mod.get(1).getSize());
        //StyleConstants.setBold(style, false);
        if (this.l_fuentes_mod.get(1).getStyle() == 0) {
            StyleConstants.setBold(style, false);
            StyleConstants.setItalic(style, false);
        }    
        else if (this.l_fuentes_mod.get(1).getStyle() == 2) {
            StyleConstants.setBold(style, false);
            StyleConstants.setItalic(style, true);
        } else {
            StyleConstants.setBold(style, true);
            StyleConstants.setItalic(style, false);
        }
        
        String cad = this.lER.get(0);
        for (int i = 1; i < this.lER.size(); i++)
        {
            try {
                if (i == ind)
                    doc.insertString(doc.getLength(), "\n" + "-----------------------------------------------", style);
                cad += "\n" + this.lER.get(i);
                doc.insertString(doc.getLength(), cad, style);
            } catch (BadLocationException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.panel_expr.setText(cad);
        this.panel_expr.setCaretPosition(0);
        try {
            Procesador.crearAutomata();
        } catch (Exception ex) {
            Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.panel_entrada.setEditable(true);
        this.panel_entrada.setFocusable(true);
    }//GEN-LAST:event_boton_nuevaActionPerformed

    @SuppressWarnings("null")
    private void panel_entradaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panel_entradaKeyTyped
        String tok = Character.toString(evt.getKeyChar());
        evt.consume();
        boolean vacio = false;
        boolean retroceso = false;
        boolean esFinal = false;
        boolean esLimitador = false;
        boolean esLimitadorTotal = true;
        boolean iniciando = false;
        String aux;
        if ((int) tok.toCharArray()[0] == 8)
        {
            retroceso = true;
            if (this.entrada.isEmpty()){return;}
            
            this.entrada.remove(this.entrada.size()-1); // Borramos el último elemento de la entrada
            StyledDocument doc1 = this.panel_entrada.getStyledDocument();
            try {
                doc1.remove(doc1.getLength()-1,1);
            } catch (BadLocationException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (!this.entrada.isEmpty()){
                tok = this.entrada.remove(this.entrada.size()-1);
                try {
                    doc1.remove(doc1.getLength()-1,1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.estadoEntrada.remove(this.estadoEntrada.size()-1); // Borramos los últimos estados activos
                this.estadoEntrada.remove(this.estadoEntrada.size()-1); // Borramos los estados del caracter que vamos a ver ahora
                if (!this.estadoEntrada.isEmpty())
                    this.lE = this.estadoEntrada.get(this.estadoEntrada.size()-1);
                else
                    this.lE = new LinkedList<>();
            } 
            else {
                vacio = true;
                this.ini  = -1;
                this.fin = -1;
            } 
        }
        this.lEaux = this.lE;
        List<Integer> expr = null;
        try {
            expr = Procesador.reconocer(this.lE,this.lnER,tok);
        } catch (Exception ex) {
            Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.lnER.clear();
        for (int n : expr)
        {
            if (!this.lnER.contains(n))
                this.lnER.add(n);
        }
        this.lE = Procesador.actualizarLE(this.lEaux, tok);
        
        for (Analizador.Estado estado : this.lE) {
            if (estado.limitador)
                esLimitador = true;
            else
                esLimitadorTotal = false;
            if (estado.esInicio) {
                iniciando = true;
                estado.esInicio = false;
            }
            if (Procesador.esEstadoFinal(estado.n)) {
                esFinal = true;
            }
        }
            
        this.estadoEntrada.add(this.lE);
        if ((int) tok.toCharArray()[0] != 8)
            this.entrada.add(tok);
        
        this.panel_expr.setText("");
        this.panel_activadas.setText("");
        // Cogemos los estilos del panel de expresiones regulares y del panel de activadas
        StyledDocument doc = this.panel_expr.getStyledDocument();
        Style style = this.panel_expr.addStyle("Style", null);
        StyledDocument doc1 = this.panel_activadas.getStyledDocument();
        Style style1 = this.panel_activadas.addStyle("Style", null);
        
        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(0).getFamily());
        StyleConstants.setForeground(style1, this.l_colores_mod.get(0));
        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(0).getSize()-2);
        if (this.l_fuentes_mod.get(0).getStyle() == 0) {
            StyleConstants.setBold(style1, false);
            StyleConstants.setItalic(style1, false);
        }    
        else if (this.l_fuentes_mod.get(0).getStyle() == 2) {
            StyleConstants.setBold(style1, false);
            StyleConstants.setItalic(style1, true);
        } else {
            StyleConstants.setBold(style1, true);
            StyleConstants.setItalic(style1, false);
        }
        
        StyleConstants.setBackground(style, this.l_colores_mod.get(3));
        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
        //StyleConstants.setForeground(style, new Color(109,109,109));
        StyleConstants.setForeground(style, this.l_colores_mod.get(2));
        //StyleConstants.setFontSize(style, 16);
        StyleConstants.setFontSize(style, this.l_fuentes_mod.get(1).getSize());
        //StyleConstants.setBold(style, false);
        if (this.l_fuentes_mod.get(1).getStyle() == 0) {
            StyleConstants.setBold(style, false);
            StyleConstants.setItalic(style, false);
        }    
        else if (this.l_fuentes_mod.get(1).getStyle() == 2) {
            StyleConstants.setBold(style, false);
            StyleConstants.setItalic(style, true);
        } else {
            StyleConstants.setBold(style, true);
            StyleConstants.setItalic(style, false);
        }
        
        //if (expr.contains(0))
        if (expr.contains(1) || expr.contains(-1))
        {
            StyleConstants.setBackground(style, this.l_colores_mod.get(1));   
            StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
            if (expr.contains(-1))
                StyleConstants.setForeground(style, Color.green);       
            else
                StyleConstants.setForeground(style, this.l_colores_mod.get(0));
            StyleConstants.setFontSize(style, this.l_fuentes_mod.get(0).getSize());
            if (this.l_fuentes_mod.get(0).getStyle() == 0) {
                StyleConstants.setBold(style, false);
                StyleConstants.setItalic(style, false);
            }    
            else if (this.l_fuentes_mod.get(0).getStyle() == 2) {
                StyleConstants.setBold(style, false);
                StyleConstants.setItalic(style, true);
            } else {
                StyleConstants.setBold(style, true);
                StyleConstants.setItalic(style, false);
            }
            
            try {
                if (this.lAuxMacro.size() > 0)
                    doc1.insertString(doc1.getLength(), this.lER.get(0) + " = " + this.lAuxER.get(0), style1);
                else
                    doc1.insertString(doc1.getLength(), this.lER.get(0), style1);
            } catch (BadLocationException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            doc.insertString(doc.getLength(), this.lER.get(0), style);
        } catch (BadLocationException ex) {
            Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 1; i < this.lER.size(); i++)
        {
            StyleConstants.setBackground(style, this.l_colores_mod.get(3));
            StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
            StyleConstants.setForeground(style, this.l_colores_mod.get(2));
            StyleConstants.setFontSize(style, this.l_fuentes_mod.get(1).getSize());
            if (this.l_fuentes_mod.get(1).getStyle() == 0) {
                StyleConstants.setBold(style, false);
                StyleConstants.setItalic(style, false);
            }    
            else if (this.l_fuentes_mod.get(1).getStyle() == 2) {
                StyleConstants.setBold(style, false);
                StyleConstants.setItalic(style, true);
            } else {
                StyleConstants.setBold(style, true);
                StyleConstants.setItalic(style, false);
            }
            
            if (i == this.ind) {
                try {
                    doc.insertString(doc.getLength(), "\n" + "-----------------------------------------------", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (expr.contains(i+1) || expr.contains(-i-1))
            {
                StyleConstants.setBackground(style, this.l_colores_mod.get(1));
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
                if (expr.contains(-i-1))
                    StyleConstants.setForeground(style, Color.green);               
                else
                    StyleConstants.setForeground(style, this.l_colores_mod.get(0));
                StyleConstants.setFontSize(style, this.l_fuentes_mod.get(0).getSize());
                if (this.l_fuentes_mod.get(0).getStyle() == 0) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, false);
                }    
                else if (this.l_fuentes_mod.get(0).getStyle() == 2) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, true);
                } else {
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, false);
                }
                
                try {
                    if (this.lAuxMacro.size() > i)
                        doc1.insertString(doc1.getLength(), "\n" + this.lER.get(i) + " = " + this.lAuxER.get(i), style1);
                    else
                        doc1.insertString(doc1.getLength(), "\n" + this.lER.get(i), style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                doc.insertString(doc.getLength(), "\n" + this.lER.get(i), style);
            } catch (BadLocationException ex) {
                Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        this.panel_expr.setCaretPosition(0);
        
        if (!vacio) {
            StyledDocument doc0 = this.panel_entrada.getStyledDocument();
            Style style0 = this.panel_entrada.addStyle("Style0", null);
            // Aquí vamos a cambiar los colores de la entrada
            if (expr.isEmpty()) {
                // NUEVO
                if (this.anteriorNoRec)
                    this.anteriorFinal = false;
                // FIN NUEVO
                if (this.ini != -1 && this.fin == -1) {
                    StyleConstants.setBackground(style0, this.l_colores_mod.get(7));
                    StyleConstants.setFontFamily(style0, this.l_fuentes_mod.get(3).getFamily());
                    StyleConstants.setForeground(style0, this.l_colores_mod.get(6));
                    StyleConstants.setFontSize(style0, this.l_fuentes_mod.get(3).getSize());
                    if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                        StyleConstants.setBold(style0, false);
                        StyleConstants.setItalic(style0, false);
                    }    
                    else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                        StyleConstants.setBold(style0, false);
                        StyleConstants.setItalic(style0, true);
                    } else {
                        StyleConstants.setBold(style0, true);
                        StyleConstants.setItalic(style0, false);
                    }
                    
                    try {
                        if (this.ini > doc0.getLength()) {
                            
                        }
                        else {
                            aux = doc0.getText(this.ini, doc0.getLength() - this.ini);
                            doc0.remove(this.ini, doc0.getLength() - this.ini);
                            doc0.insertString(doc0.getLength(), aux, style0);
                            this.auxl.add(ini);
                        }
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (this.anteriorFinal && retroceso)
                    this.auxl.remove(this.auxl.size()-1);
                this.ini = -1;
                this.fin = -1;
                
                StyleConstants.setBackground(style0, this.l_colores_mod.get(7));
                StyleConstants.setFontFamily(style0, this.l_fuentes_mod.get(3).getFamily());
                StyleConstants.setForeground(style0, this.l_colores_mod.get(6));
                StyleConstants.setFontSize(style0, this.l_fuentes_mod.get(3).getSize());
                if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, false);
                }    
                else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, true);
                } else {
                    StyleConstants.setBold(style0, true);
                    StyleConstants.setItalic(style0, false);
                }
                
                this.anteriorNoRec = true;
                this.anteriorBorrado = false;
            }
            else if (esFinal) {
                StyleConstants.setBackground(style0, this.l_colores_mod.get(5));
                StyleConstants.setFontFamily(style0, this.l_fuentes_mod.get(2).getFamily());
                StyleConstants.setForeground(style0, this.l_colores_mod.get(4));
                StyleConstants.setFontSize(style0, this.l_fuentes_mod.get(2).getSize());
                if (this.l_fuentes_mod.get(2).getStyle() == 0) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, false);
                }    
                else if (this.l_fuentes_mod.get(2).getStyle() == 2) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, true);
                } else {
                    StyleConstants.setBold(style0, true);
                    StyleConstants.setItalic(style0, false);
                }
                
                if (retroceso) {
                    if (this.ini == -1 && this.anteriorFinal && !this.anteriorNoRec)
                        this.auxl.remove(this.auxl.size()-1);
                    // NUEVO
                    if (this.ini != -1)
                        this.ini = -1;
                    // FIN NUEVO
                }
                else if (this.ini != -1) {
                    this.fin = doc0.getLength();
                    try {
                        aux = doc0.getText(this.ini, this.fin - this.ini);
                        doc0.remove(this.ini, this.fin - this.ini);
                        doc0.insertString(doc0.getLength(), aux, style0);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.ini = doc0.getLength();
                }
                if (!retroceso) {
                    this.auxl.add(ini);
                    this.ini = -1;
                }
                this.anteriorFinal = true;
                this.anteriorNoRec = false;
                this.anteriorBorrado = false;
            } else {
                
                if (iniciando) {
                    StyleConstants.setBackground(style0, this.l_colores_mod.get(7));
                    StyleConstants.setFontFamily(style0, this.l_fuentes_mod.get(3).getFamily());
                    StyleConstants.setForeground(style0, this.l_colores_mod.get(6));
                    StyleConstants.setFontSize(style0, this.l_fuentes_mod.get(3).getSize());
                    if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                        StyleConstants.setBold(style0, false);
                        StyleConstants.setItalic(style0, false);
                    }    
                    else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                        StyleConstants.setBold(style0, false);
                        StyleConstants.setItalic(style0, true);
                    } else {
                        StyleConstants.setBold(style0, true);
                        StyleConstants.setItalic(style0, false);
                    }
                    
                    try {
                        if (this.ini > doc0.getLength() || this.ini == -1) {
                            
                        }
                        else {
                            aux = doc0.getText(this.ini, doc0.getLength() - this.ini);
                            doc0.remove(this.ini, doc0.getLength() - this.ini);
                            doc0.insertString(doc0.getLength(), aux, style0);
                            this.auxl.add(ini);
                        }
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                StyleConstants.setBackground(style0, this.l_colores_mod.get(9));
                StyleConstants.setFontFamily(style0, this.l_fuentes_mod.get(4).getFamily());
                StyleConstants.setForeground(style0, this.l_colores_mod.get(8));
                StyleConstants.setFontSize(style0, this.l_fuentes_mod.get(4).getSize());
                if (this.l_fuentes_mod.get(4).getStyle() == 0) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, false);
                }    
                else if (this.l_fuentes_mod.get(4).getStyle() == 2) {
                    StyleConstants.setBold(style0, false);
                    StyleConstants.setItalic(style0, true);
                } else {
                    StyleConstants.setBold(style0, true);
                    StyleConstants.setItalic(style0, false);
                }
                
                if (retroceso) {
                    if ((this.ini != -1 && !this.auxl.isEmpty() && this.ini != this.auxl.get(this.auxl.size()-1) && this.ini < doc0.getLength()) || (this.ini != -1 && this.auxl.isEmpty() && this.ini < doc0.getLength())) {
                        try {
                            aux = doc0.getText(this.ini, doc0.getLength() - this.ini);
                            doc0.remove(this.ini, doc0.getLength() - this.ini);
                            doc0.insertString(doc0.getLength(), aux, style0);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if (!this.auxl.isEmpty()) {
                        try {
                            if (this.anteriorBorrado && auxini != -1 && auxini <= doc0.getLength()){
                                aux = doc0.getText(auxini, doc0.getLength() - auxini);
                                doc0.remove(auxini, doc0.getLength() - auxini);
                                auxini = -1;
                            } else {
                                int a = doc0.getLength();
                                aux = doc0.getText(auxl.get(auxl.size()-1), doc0.getLength() - auxl.get(auxl.size()-1));
                                doc0.remove(auxl.get(auxl.size()-1), doc0.getLength() - auxl.get(auxl.size()-1));
                                //auxini = -1;
                            }
                            doc0.insertString(doc0.getLength(), aux, style0);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (!anteriorBorrado) {
                            this.ini = this.auxl.remove(this.auxl.size()-1);
                            this.auxini = this.ini;
                            this.anteriorBorrado = true;
                        }
                        this.fin = -1;
                    }
                } else {
                    if (this.ini == -1)
                        this.ini = doc0.getLength();
                    if (iniciando && !anteriorFinal) {
                        //this.auxl.remove(this.auxl.size()-1);
                        //this.auxl.add(doc0.getLength());
                        this.ini = doc0.getLength();
                    }
                }
                this.anteriorFinal = false;
                this.anteriorNoRec = false;
                this.fin = -1;
            }
            try {
                doc0.insertString(doc0.getLength(), tok, style0);
            } catch (BadLocationException ex) {
                Logger.getLogger(FPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // PARECE QUE ESTÁ PERFECTO (SEGUIR PROBANDO)
    }//GEN-LAST:event_panel_entradaKeyTyped

    private void panel_entradaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panel_entradaFocusGained
//        this.panel_entrada.setText("");
//        StyledDocument doc = this.panel_entrada.getStyledDocument();
//        Style style = this.panel_entrada.addStyle("Style", null);
//        StyleConstants.setForeground(style, new Color(102,102,102));
//        StyleConstants.setItalic(style, false);
//        StyleConstants.setBold(style, true);
//        try {
//            doc.insertString(doc.getLength(), " ", style);
//        } catch (BadLocationException ex) {
//            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_panel_entradaFocusGained

    private void panel_entradaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panel_entradaFocusLost
//        this.panel_entrada.setText("");
//        StyledDocument doc = this.panel_entrada.getStyledDocument();
//        Style style = this.panel_entrada.addStyle("Style", null);
//        StyleConstants.setForeground(style, Color.LIGHT_GRAY);
//        StyleConstants.setItalic(style, true);
//        StyleConstants.setBold(style, false);
//        try {
//            doc.insertString(doc.getLength(), "Introduzca una entrada..", style);
//        } catch (BadLocationException ex) {
//            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_panel_entradaFocusLost

    private void panel_entradaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panel_entradaKeyReleased
        evt.consume();
    }//GEN-LAST:event_panel_entradaKeyReleased

    private void panel_entradaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panel_entradaKeyPressed
        this.panel_entradaKeyReleased(evt);
    }//GEN-LAST:event_panel_entradaKeyPressed

    private void panel_exprMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_exprMouseClicked
        
    }//GEN-LAST:event_panel_exprMouseClicked

    private void panel_exprHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_panel_exprHyperlinkUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_exprHyperlinkUpdate

    private void lbSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSettingsMouseClicked
        this.pAjustes = new P_ajustes(this,true,this.getL_fuentes_mod(),this.getL_colores_mod());
        this.pAjustes.setVisible(true);
        this.l_fuentes_mod.clear();
        this.l_colores_mod.clear();
        this.l_fuentes_mod.addAll(this.pAjustes.getL_fuentes_mod());
        this.l_colores_mod.addAll(this.pAjustes.getL_colores_mod());
    }//GEN-LAST:event_lbSettingsMouseClicked
    
    /**
     * Método para abrir un archivo con JFileChooser.
     * @return texto del archivo
     * @throws Exception 
     */
    @SuppressWarnings("null")
    private String abrirArchivo() throws Exception {
        String aux;   
        String texto="";
        try
        {
            JFileChooser file=new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Especificación léxica","flex","flex");
            file.setFileFilter(filter);
            file.showOpenDialog(this);
            File abre=file.getSelectedFile();
            
            if(abre!=null)
            {
                lER = Procesador.procesarEsp(abre.getPath());
                this.lAuxER = Procesador.getER();
                this.lAuxMacro = Procesador.getMacro();
                FileReader archivos=new FileReader(abre);
                try (BufferedReader lee = new BufferedReader(archivos)) {
                    while((aux=lee.readLine())!=null)
                    {
                        texto+= aux+ "\n";
                    }
                }
            }    
        }
        catch(IOException ex)
        {
          JOptionPane.showMessageDialog(null,ex+"" +
                "\nNo se ha encontrado el archivo",
                      "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        return texto;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_nueva;
    private javax.swing.JPanel cabecera;
    private javax.swing.JPanel cabecera_act;
    private javax.swing.JPanel cabecera_entrada;
    private javax.swing.JPanel cabecera_esp;
    private javax.swing.JLabel et_act;
    private javax.swing.JLabel et_entrada;
    private javax.swing.JLabel et_especificacion;
    private javax.swing.JLabel et_titulo;
    private javax.swing.JLabel lbHelp;
    private javax.swing.JLabel lbReg;
    private javax.swing.JLabel lbSettings;
    private javax.swing.JTextPane panel_activadas;
    private javax.swing.JTextPane panel_entrada;
    private javax.swing.JTextArea panel_especificacion;
    private javax.swing.JTextPane panel_expr;
    private javax.swing.JScrollPane scroll_panel_activadas;
    private javax.swing.JScrollPane scroll_panel_entrada;
    private javax.swing.JScrollPane scroll_panel_especificacion;
    private javax.swing.JScrollPane scroll_panel_expr;
    // End of variables declaration//GEN-END:variables
}
