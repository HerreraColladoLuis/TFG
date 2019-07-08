
import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import say.swing.JFontChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author herre
 */
public class P_ajustes extends javax.swing.JDialog {

    private List<Font> l_fuentes = new LinkedList<>();
    private List<Font> l_fuentes_mod = new LinkedList<>();
    private List<Font> l_fuentes_def = new LinkedList<>();
    private List<Color> l_colores_def = new LinkedList<>();
    private List<Color> l_colores = new LinkedList<>();
    private List<Color> l_colores_mod = new LinkedList<>();
    /**
     * Creates new form P_ajustes
     * @param parent
     * @param modal
     * @param lf
     * @param lc
     */
    public P_ajustes(java.awt.Frame parent, boolean modal, List<Font> lf, List<Color> lc) {
        super(parent, modal);
        this.l_fuentes.addAll(lf);
        this.l_colores.addAll(lc);
        this.l_fuentes_mod.addAll(lf);
        this.l_colores_mod.addAll(lc);
        
        // Seteamos los colores y las fuentes por defecto
        this.l_fuentes_def.add(new Font("Tahoma",BOLD,18)); // Fuente ACTIVADAS
        this.l_colores_def.add(new Color(106,90,205)); // Color de fuente ACTIVADAS
        this.l_colores_def.add(Color.WHITE); // Color de fondo de fuente ACTIVADAS
        
        this.l_fuentes_def.add(new Font("Tahoma",PLAIN,16)); // Fuente NO ACTIVADAS
        this.l_colores_def.add(new Color(109,109,109));
        this.l_colores_def.add(Color.WHITE);
        
        this.l_fuentes_def.add(new Font("Tahoma",BOLD,18)); // Fuente COMPLETAS
        this.l_colores_def.add(Color.BLACK); // Color de fuente COMPLETAS
        this.l_colores_def.add(Color.WHITE); // Color de fondo de fuente COMPLETAS
        
        this.l_fuentes_def.add(new Font("Verdana",BOLD,18)); // Fuente entrada COMPLETA
        this.l_colores_def.add(Color.WHITE);
        this.l_colores_def.add(new Color(25,25,112));
        
        this.l_fuentes_def.add(new Font("Verdana",BOLD,18)); // Fuente entrada NO activada ni completa
        this.l_colores_def.add(new Color(109,109,109));
        this.l_colores_def.add(Color.WHITE);
        
        this.l_fuentes_def.add(new Font("Verdana",BOLD,18)); // Fuente entrada ACTIVADA
        this.l_colores_def.add(new Color(109,109,109));
        this.l_colores_def.add(new Color(173,216,230));
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        ImageIcon helpImage = new ImageIcon(getClass().getResource(("/Imágenes/help.png")));
        Icon iconHelp = new ImageIcon(helpImage.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        this.lb_ayuda.setIcon(iconHelp);
        
        // Configuración inicial 
        this.tx_fuente_1.setText(this.l_fuentes_mod.get(0).getName() + " " + this.l_fuentes_mod.get(0).getSize());
        this.tx_color_fuente_1.setText(this.l_colores_mod.get(0).getRed() + ", " + this.l_colores_mod.get(0).getGreen() + ", " + this.l_colores_mod.get(0).getBlue());
        this.tx_color_fondo_1.setText(this.l_colores_mod.get(1).getRed() + ", " + this.l_colores_mod.get(1).getGreen() + ", " + this.l_colores_mod.get(1).getBlue());
        
        this.tx_fuente_2.setText(this.l_fuentes_mod.get(3).getName() + " " + this.l_fuentes_mod.get(3).getSize());
        this.tx_color_fuente_2.setText(this.l_colores_mod.get(6).getRed() + ", " + this.l_colores_mod.get(6).getGreen() + ", " + this.l_colores_mod.get(6).getBlue());
        this.tx_color_fondo_2.setText(this.l_colores_mod.get(7).getRed() + ", " + this.l_colores_mod.get(7).getGreen() + ", " + this.l_colores_mod.get(7).getBlue());
        
        this.tx_pv_1.setText("");
        this.tx_pv_2.setText("");
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        
        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
        StyleConstants.setForeground(style, this.l_colores_mod.get(0));
        StyleConstants.setBackground(style, this.l_colores_mod.get(1));
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
        
        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(3).getFamily());
        StyleConstants.setForeground(style1, this.l_colores_mod.get(6));
        StyleConstants.setBackground(style1, this.l_colores_mod.get(7));
        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(3).getSize());
        if (this.l_fuentes_mod.get(3).getStyle() == 0) {
            StyleConstants.setBold(style1, false);
            StyleConstants.setItalic(style1, false);
        }    
        else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
            StyleConstants.setBold(style1, false);
            StyleConstants.setItalic(style1, true);
        } else {
            StyleConstants.setBold(style1, true);
            StyleConstants.setItalic(style1, false);
        }
        
        try {
            doc.insertString(doc.getLength(), "Palabra reservada", style);
            doc1.insertString(doc1.getLength(), "while", style1);
        } catch (BadLocationException ex) {
            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tx_fuente_1 = new javax.swing.JTextField();
        boton_cancelar = new javax.swing.JButton();
        boton_fuente_1 = new javax.swing.JButton();
        boton_aplicar = new javax.swing.JButton();
        boton_color_fuente_1 = new javax.swing.JButton();
        Separador_2 = new javax.swing.JSeparator();
        boton_color_fondo_1 = new javax.swing.JButton();
        lb_ayuda = new javax.swing.JLabel();
        lb_titulo_1 = new javax.swing.JLabel();
        lb_titulo_2 = new javax.swing.JLabel();
        scroll_lista_1 = new javax.swing.JScrollPane();
        lista_1 = new javax.swing.JList<>();
        scroll_lista_2 = new javax.swing.JScrollPane();
        lista_2 = new javax.swing.JList<>();
        lb_fuente_1 = new javax.swing.JLabel();
        lb_color_fuente_1 = new javax.swing.JLabel();
        lb_color_fondo_1 = new javax.swing.JLabel();
        tx_color_fuente_1 = new javax.swing.JTextField();
        separador_1 = new javax.swing.JSeparator();
        tx_color_fondo_1 = new javax.swing.JTextField();
        boton_defecto = new javax.swing.JButton();
        lb_fuente_2 = new javax.swing.JLabel();
        lb_color_fuente_2 = new javax.swing.JLabel();
        lb_color_fondo_2 = new javax.swing.JLabel();
        tx_color_fuente_2 = new javax.swing.JTextField();
        tx_fuente_2 = new javax.swing.JTextField();
        tx_color_fondo_2 = new javax.swing.JTextField();
        boton_fuente_2 = new javax.swing.JButton();
        boton_color_fuente_2 = new javax.swing.JButton();
        boton_color_fondo_2 = new javax.swing.JButton();
        boton_reestablecer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tx_pv_1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tx_pv_2 = new javax.swing.JTextPane();
        lb_titulo_3 = new javax.swing.JLabel();
        lb_titulo_4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("P_ajustes.title")); // NOI18N

        tx_fuente_1.setEditable(false);
        tx_fuente_1.setFocusable(false);

        boton_cancelar.setText(bundle.getString("P_ajustes.boton_cancelar.text")); // NOI18N
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });

        boton_fuente_1.setText(bundle.getString("P_ajustes.boton_fuente_1.text")); // NOI18N
        boton_fuente_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_fuente_1ActionPerformed(evt);
            }
        });

        boton_aplicar.setText(bundle.getString("P_ajustes.boton_aplicar.text")); // NOI18N
        boton_aplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_aplicarActionPerformed(evt);
            }
        });

        boton_color_fuente_1.setText(bundle.getString("P_ajustes.boton_color_fuente_1.text")); // NOI18N
        boton_color_fuente_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_color_fuente_1ActionPerformed(evt);
            }
        });

        boton_color_fondo_1.setText(bundle.getString("P_ajustes.boton_color_fondo_1.text")); // NOI18N
        boton_color_fondo_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_color_fondo_1ActionPerformed(evt);
            }
        });

        lb_ayuda.setText(bundle.getString("P_ajustes.lb_ayuda.text")); // NOI18N
        lb_ayuda.setToolTipText(bundle.getString("P_ajustes.lb_ayuda.toolTipText")); // NOI18N

        lb_titulo_1.setText(bundle.getString("P_ajustes.lb_titulo_1.text")); // NOI18N

        lb_titulo_2.setText(bundle.getString("P_ajustes.lb_titulo_2.text")); // NOI18N

        lista_1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Definiciones Regulares/Patrones activos", "Definiciones Regulares/Patrones NO activos", "Definiciones Regulares/Patrones completos" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lista_1.setSelectedIndex(0);
        lista_1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lista_1ValueChanged(evt);
            }
        });
        scroll_lista_1.setViewportView(lista_1);

        lista_2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Lexemas COMPLETAMENTE detectados en la entrada", "Lexemas NO detectados en la entrada", "Lexemas PARCIALMENTE detectados en la entrada" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lista_2.setSelectedIndex(0);
        lista_2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lista_2ValueChanged(evt);
            }
        });
        scroll_lista_2.setViewportView(lista_2);

        lb_fuente_1.setText(bundle.getString("P_ajustes.lb_fuente_1.text")); // NOI18N

        lb_color_fuente_1.setText(bundle.getString("P_ajustes.lb_color_fuente_1.text")); // NOI18N

        lb_color_fondo_1.setText(bundle.getString("P_ajustes.lb_color_fondo_1.text")); // NOI18N

        tx_color_fuente_1.setEditable(false);
        tx_color_fuente_1.setFocusable(false);

        tx_color_fondo_1.setEditable(false);
        tx_color_fondo_1.setFocusable(false);

        boton_defecto.setText(bundle.getString("P_ajustes.boton_defecto.text")); // NOI18N
        boton_defecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_defectoActionPerformed(evt);
            }
        });

        lb_fuente_2.setText(bundle.getString("P_ajustes.lb_fuente_2.text")); // NOI18N

        lb_color_fuente_2.setText(bundle.getString("P_ajustes.lb_color_fuente_2.text")); // NOI18N

        lb_color_fondo_2.setText(bundle.getString("P_ajustes.lb_color_fondo_2.text")); // NOI18N

        tx_color_fuente_2.setEditable(false);
        tx_color_fuente_2.setFocusable(false);

        tx_fuente_2.setEditable(false);
        tx_fuente_2.setFocusable(false);

        tx_color_fondo_2.setEditable(false);
        tx_color_fondo_2.setFocusable(false);
        tx_color_fondo_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_color_fondo_2ActionPerformed(evt);
            }
        });

        boton_fuente_2.setText(bundle.getString("P_ajustes.boton_fuente_2.text")); // NOI18N
        boton_fuente_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_fuente_2ActionPerformed(evt);
            }
        });

        boton_color_fuente_2.setText(bundle.getString("P_ajustes.boton_color_fuente_2.text")); // NOI18N
        boton_color_fuente_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_color_fuente_2ActionPerformed(evt);
            }
        });

        boton_color_fondo_2.setText(bundle.getString("P_ajustes.boton_color_fondo_2.text")); // NOI18N
        boton_color_fondo_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_color_fondo_2ActionPerformed(evt);
            }
        });

        boton_reestablecer.setText(bundle.getString("P_ajustes.boton_reestablecer.text")); // NOI18N
        boton_reestablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_reestablecerActionPerformed(evt);
            }
        });

        tx_pv_1.setEditable(false);
        tx_pv_1.setFocusable(false);
        jScrollPane1.setViewportView(tx_pv_1);

        tx_pv_2.setEditable(false);
        tx_pv_2.setFocusable(false);
        jScrollPane2.setViewportView(tx_pv_2);

        lb_titulo_3.setText(bundle.getString("P_ajustes.lb_titulo_3.text")); // NOI18N

        lb_titulo_4.setText(bundle.getString("P_ajustes.lb_titulo_4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scroll_lista_1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_color_fuente_1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_color_fondo_1)
                            .addComponent(lb_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tx_color_fuente_1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(tx_fuente_1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx_color_fondo_1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_color_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_color_fondo_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lb_ayuda))
                    .addComponent(separador_1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(boton_defecto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_aplicar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(boton_reestablecer))
                    .addComponent(Separador_2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_titulo_1)
                            .addComponent(lb_titulo_3)
                            .addComponent(lb_titulo_2)
                            .addComponent(lb_titulo_4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scroll_lista_2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_color_fuente_2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_color_fondo_2)
                            .addComponent(lb_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tx_color_fuente_2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx_fuente_2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx_color_fondo_2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boton_color_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_color_fondo_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_ayuda)
                .addGap(7, 7, 7)
                .addComponent(lb_titulo_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_fuente_1)
                            .addComponent(tx_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_color_fuente_1)
                            .addComponent(tx_color_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_color_fuente_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_color_fondo_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_color_fondo_1)
                            .addComponent(boton_color_fondo_1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scroll_lista_1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_titulo_3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(separador_1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_titulo_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_fuente_2)
                            .addComponent(tx_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_color_fuente_2)
                            .addComponent(tx_color_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_color_fuente_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_color_fondo_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_color_fondo_2)
                            .addComponent(boton_color_fondo_2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scroll_lista_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(lb_titulo_4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Separador_2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_defecto)
                    .addComponent(boton_cancelar)
                    .addComponent(boton_aplicar)
                    .addComponent(boton_reestablecer))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        this.l_fuentes_mod.clear();
        this.l_colores_mod.clear();
        this.l_fuentes_mod.addAll(l_fuentes);
        this.l_colores_mod.addAll(l_colores);
        this.dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    public List<Font> getL_fuentes_mod() {
        return l_fuentes_mod;
    }

    public List<Color> getL_colores_mod() {
        return l_colores_mod;
    }

    private void boton_fuente_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_fuente_1ActionPerformed
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        int i, res;
        if (!this.lista_1.isSelectionEmpty()) {
            switch (this.lista_1.getSelectedValue()) {
                case "Definiciones Regulares/Patrones activos":
                    i = 0;
                    break;
                case "Definiciones Regulares/Patrones NO activos":
                    i = 1;
                    break;
                default:
                    i = 2;
                    break;
            }
            JFontChooser jf = new JFontChooser();
            res = jf.showDialog(null);
            if (res == JFontChooser.OK_OPTION) {
                Font f = jf.getSelectedFont();
                this.l_fuentes_mod.set(i, f);
                this.tx_fuente_1.setText(this.l_fuentes_mod.get(i).getName() + " " + this.l_fuentes_mod.get(i).getSize());
                
                this.tx_pv_1.setText("");
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(i).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(i*2));
                StyleConstants.setBackground(style, this.l_colores_mod.get((i*2)+1));
                StyleConstants.setFontSize(style, this.l_fuentes_mod.get(i).getSize());
                if (this.l_fuentes_mod.get(i).getStyle() == 0) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, false);
                }    
                else if (this.l_fuentes_mod.get(i).getStyle() == 2) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, true);
                } else {
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, false);
                }
                try {
                    doc.insertString(doc.getLength(), "Palabra reservada", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_fuente_1ActionPerformed

    private void boton_color_fuente_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_color_fuente_1ActionPerformed
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        int i;
        if (!this.lista_1.isSelectionEmpty()) {
            switch (this.lista_1.getSelectedValue()) {
                case "Definiciones Regulares/Patrones activos":
                    i = 0;
                    break;
                case "Definiciones Regulares/Patrones NO activos":
                    i = 2;
                    break;
                default:
                    i = 4;
                    break;
            }
            Color c = JColorChooser.showDialog(null, "Selecciona un color", this.l_colores_mod.get(i));
            if (c!= null) {
                this.l_colores_mod.set(i, c);
                this.tx_color_fuente_1.setText(this.l_colores_mod.get(i).getRed() + ", " + this.l_colores_mod.get(i).getGreen() + ", " + this.l_colores_mod.get(i).getBlue());
                
                this.tx_pv_1.setText("");
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(i/2).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(i));
                StyleConstants.setBackground(style, this.l_colores_mod.get(i+1));
                StyleConstants.setFontSize(style, this.l_fuentes_mod.get(i/2).getSize());
                if (this.l_fuentes_mod.get(i/2).getStyle() == 0) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, false);
                }    
                else if (this.l_fuentes_mod.get(i/2).getStyle() == 2) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, true);
                } else {
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, false);
                }
                try {
                    doc.insertString(doc.getLength(), "Palabra reservada", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_color_fuente_1ActionPerformed

    private void boton_color_fondo_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_color_fondo_1ActionPerformed
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        int i;
        if (!this.lista_1.isSelectionEmpty()) {
            switch (this.lista_1.getSelectedValue()) {
                case "Definiciones Regulares/Patrones activos":
                    i = 1;
                    break;
                case "Definiciones Regulares/Patrones NO activos":
                    i = 3;
                    break;
                default:
                    i = 5;
                    break;
            }
            Color c = JColorChooser.showDialog(null, "Selecciona un color", this.l_colores_mod.get(i));
            if (c!= null) {
                this.l_colores_mod.set(i, c);
                this.tx_color_fondo_1.setText(this.l_colores_mod.get(i).getRed() + ", " + this.l_colores_mod.get(i).getGreen() + ", " + this.l_colores_mod.get(i).getBlue());
                
                this.tx_pv_1.setText("");
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(i/2).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(i-1));
                StyleConstants.setBackground(style, this.l_colores_mod.get(i));
                StyleConstants.setFontSize(style, this.l_fuentes_mod.get(i/2).getSize());
                if (this.l_fuentes_mod.get(i/2).getStyle() == 0) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, false);
                }    
                else if (this.l_fuentes_mod.get(i/2).getStyle() == 2) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, true);
                } else {
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, false);
                }
                try {
                    doc.insertString(doc.getLength(), "Palabra reservada", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_color_fondo_1ActionPerformed

    private void boton_fuente_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_fuente_2ActionPerformed
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        int i, res;
        if (!this.lista_2.isSelectionEmpty()) {
            switch (this.lista_2.getSelectedValue()) {
                case "Lexemas COMPLETAMENTE detectados en la entrada":
                    i = 3;
                    break;
                case "Lexemas NO detectados en la entrada":
                    i = 4;
                    break;
                default:
                    i = 5;
                    break;
            }
            JFontChooser jf = new JFontChooser();
            res = jf.showDialog(null);
            if (res == JFontChooser.OK_OPTION) {
                Font f = jf.getSelectedFont();
                this.l_fuentes_mod.set(i, f);
                this.tx_fuente_2.setText(this.l_fuentes_mod.get(i).getName() + " " + this.l_fuentes_mod.get(i).getSize());
                
                this.tx_pv_2.setText("");
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(i).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(i*2));
                StyleConstants.setBackground(style1, this.l_colores_mod.get((i*2)+1));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(i).getSize());
                if (this.l_fuentes_mod.get(i).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(i).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    if (i == 3)
                        doc1.insertString(doc1.getLength(), "while", style1);
                    else if (i == 4)
                        doc1.insertString(doc1.getLength(), "6&€?l", style1);
                    else
                        doc1.insertString(doc1.getLength(), "whil", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_fuente_2ActionPerformed

    private void boton_color_fuente_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_color_fuente_2ActionPerformed
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        int i;
        if (!this.lista_2.isSelectionEmpty()) {
            switch (this.lista_2.getSelectedValue()) {
                case "Lexemas COMPLETAMENTE detectados en la entrada":
                    i = 6;
                    break;
                case "Lexemas NO detectados en la entrada":
                    i = 8;
                    break;
                default:
                    i = 10;
                    break;
            }
            Color c = JColorChooser.showDialog(null, "Selecciona un color", this.l_colores_mod.get(i));
            if (c!= null) {
                this.l_colores_mod.set(i, c);
                this.tx_color_fuente_2.setText(this.l_colores_mod.get(i).getRed() + ", " + this.l_colores_mod.get(i).getGreen() + ", " + this.l_colores_mod.get(i).getBlue());
                
                this.tx_pv_2.setText("");
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(i/2).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(i));
                StyleConstants.setBackground(style1, this.l_colores_mod.get(i+1));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(i/2).getSize());
                if (this.l_fuentes_mod.get(i/2).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(i/2).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    if (i == 6)
                        doc1.insertString(doc1.getLength(), "while", style1);
                    else if (i == 8)
                        doc1.insertString(doc1.getLength(), "6&€?l", style1);
                    else
                        doc1.insertString(doc1.getLength(), "whil", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_color_fuente_2ActionPerformed

    private void boton_color_fondo_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_color_fondo_2ActionPerformed
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        int i;
        if (!this.lista_2.isSelectionEmpty()) {
            switch (this.lista_2.getSelectedValue()) {
                case "Lexemas COMPLETAMENTE detectados en la entrada":
                    i = 7;
                    break;
                case "Lexemas NO detectados en la entrada":
                    i = 9;
                    break;
                default:
                    i = 11;
                    break;
            }
            Color c = JColorChooser.showDialog(null, "Selecciona un color", this.l_colores_mod.get(i));
            if (c!= null) {
                this.l_colores_mod.set(i, c);
                this.tx_color_fondo_2.setText(this.l_colores_mod.get(i).getRed() + ", " + this.l_colores_mod.get(i).getGreen() + ", " + this.l_colores_mod.get(i).getBlue());
                
                this.tx_pv_2.setText("");
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(i/2).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(i-1));
                StyleConstants.setBackground(style1, this.l_colores_mod.get(i));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(i/2).getSize());
                if (this.l_fuentes_mod.get(i/2).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(i/2).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    if (i == 7)
                        doc1.insertString(doc1.getLength(), "while", style1);
                    else if (i == 9)
                        doc1.insertString(doc1.getLength(), "6&€?l", style1);
                    else
                        doc1.insertString(doc1.getLength(), "whil", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_boton_color_fondo_2ActionPerformed

    private void lista_1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lista_1ValueChanged
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        this.tx_pv_1.setText("");
        switch (this.lista_1.getSelectedValue()) {
            case "Definiciones Regulares/Patrones activos":
                // Configuración fuente y colores
                this.tx_fuente_1.setText(this.l_fuentes_mod.get(0).getName() + " " + this.l_fuentes_mod.get(0).getSize());
                this.tx_color_fuente_1.setText(this.l_colores_mod.get(0).getRed() + ", " + this.l_colores_mod.get(0).getGreen() + ", " + this.l_colores_mod.get(0).getBlue());
                this.tx_color_fondo_1.setText(this.l_colores_mod.get(1).getRed() + ", " + this.l_colores_mod.get(1).getGreen() + ", " + this.l_colores_mod.get(1).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(0));
                StyleConstants.setBackground(style, this.l_colores_mod.get(1));
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
                break;
            case "Definiciones Regulares/Patrones NO activos":
                // Configuración colores y fuente
                this.tx_fuente_1.setText(this.l_fuentes_mod.get(1).getName() + " " + this.l_fuentes_mod.get(1).getSize());
                this.tx_color_fuente_1.setText(this.l_colores_mod.get(2).getRed() + ", " + this.l_colores_mod.get(2).getGreen() + ", " + this.l_colores_mod.get(2).getBlue());
                this.tx_color_fondo_1.setText(this.l_colores_mod.get(3).getRed() + ", " + this.l_colores_mod.get(3).getGreen() + ", " + this.l_colores_mod.get(3).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(2));
                StyleConstants.setBackground(style, this.l_colores_mod.get(3));
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
                break;
            default:
                // Configuración fuente y colores
                this.tx_fuente_1.setText(this.l_fuentes_mod.get(2).getName() + " " + this.l_fuentes_mod.get(2).getSize());
                this.tx_color_fuente_1.setText(this.l_colores_mod.get(4).getRed() + ", " + this.l_colores_mod.get(4).getGreen() + ", " + this.l_colores_mod.get(4).getBlue());
                this.tx_color_fondo_1.setText(this.l_colores_mod.get(5).getRed() + ", " + this.l_colores_mod.get(5).getGreen() + ", " + this.l_colores_mod.get(5).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(2).getFamily());
                StyleConstants.setForeground(style, this.l_colores_mod.get(4));
                StyleConstants.setBackground(style, this.l_colores_mod.get(5));
                StyleConstants.setFontSize(style, this.l_fuentes_mod.get(2).getSize());
                if (this.l_fuentes_mod.get(2).getStyle() == 0) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, false);
                }    
                else if (this.l_fuentes_mod.get(2).getStyle() == 2) {
                    StyleConstants.setBold(style, false);
                    StyleConstants.setItalic(style, true);
                } else {
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, false);
                }
                break;
        }
        try {
            doc.insertString(doc.getLength(), "Palabra reservada", style);
        } catch (BadLocationException ex) {
            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lista_1ValueChanged

    private void lista_2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lista_2ValueChanged
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        this.tx_pv_2.setText("");
        switch (this.lista_2.getSelectedValue()) {
            case "Lexemas COMPLETAMENTE detectados en la entrada":
                // Configuración fuente y colores
                tx_fuente_2.setText(this.l_fuentes_mod.get(3).getName() + " " + this.l_fuentes_mod.get(3).getSize());
                this.tx_color_fuente_2.setText(this.l_colores_mod.get(6).getRed() + ", " + this.l_colores_mod.get(6).getGreen() + ", " + this.l_colores_mod.get(6).getBlue());
                this.tx_color_fondo_2.setText(this.l_colores_mod.get(7).getRed() + ", " + this.l_colores_mod.get(7).getGreen() + ", " + this.l_colores_mod.get(7).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(3).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(6));
                StyleConstants.setBackground(style1, this.l_colores_mod.get(7));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(3).getSize());
                if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    doc1.insertString(doc1.getLength(), "while", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Lexemas NO detectados en la entrada":
                // Configuración fuente y colores
                tx_fuente_2.setText(this.l_fuentes_mod.get(4).getName() + " " + this.l_fuentes_mod.get(4).getSize());
                this.tx_color_fuente_2.setText(this.l_colores_mod.get(8).getRed() + ", " + this.l_colores_mod.get(8).getGreen() + ", " + this.l_colores_mod.get(8).getBlue());
                this.tx_color_fondo_2.setText(this.l_colores_mod.get(9).getRed() + ", " + this.l_colores_mod.get(9).getGreen() + ", " + this.l_colores_mod.get(9).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(4).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(8));
                StyleConstants.setBackground(style1, this.l_colores_mod.get(9));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(4).getSize());
                if (this.l_fuentes_mod.get(4).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(4).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    doc1.insertString(doc1.getLength(), "6&€?l", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                // Configuración fuente y colores
                tx_fuente_2.setText(this.l_fuentes_mod.get(5).getName() + " " + this.l_fuentes_mod.get(5).getSize());
                this.tx_color_fuente_2.setText(this.l_colores_mod.get(10).getRed() + ", " + this.l_colores_mod.get(10).getGreen() + ", " + this.l_colores_mod.get(10).getBlue());
                this.tx_color_fondo_2.setText(this.l_colores_mod.get(11).getRed() + ", " + this.l_colores_mod.get(11).getGreen() + ", " + this.l_colores_mod.get(11).getBlue());
                // Configuración previsualización
                StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(5).getFamily());
                StyleConstants.setForeground(style1, this.l_colores_mod.get(10));
                StyleConstants.setBackground(style1, this.l_colores_mod.get(11));
                StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(5).getSize());
                if (this.l_fuentes_mod.get(5).getStyle() == 0) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, false);
                }    
                else if (this.l_fuentes_mod.get(5).getStyle() == 2) {
                    StyleConstants.setBold(style1, false);
                    StyleConstants.setItalic(style1, true);
                } else {
                    StyleConstants.setBold(style1, true);
                    StyleConstants.setItalic(style1, false);
                }
                try {
                    doc1.insertString(doc1.getLength(), "whil", style1);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }//GEN-LAST:event_lista_2ValueChanged

    private void boton_aplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_aplicarActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de aplicar los valores actuales?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "¡Valores cambiados con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_boton_aplicarActionPerformed

    private void boton_defectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_defectoActionPerformed
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de cambiar a los valores por defecto?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) { 
            this.l_fuentes_mod.clear();
            this.l_fuentes_mod.addAll(l_fuentes_def);
            this.l_colores_mod.clear();
            this.l_colores_mod.addAll(l_colores_def);

            if (!this.lista_1.isSelectionEmpty()) {
                this.tx_pv_1.setText("");
                this.tx_pv_2.setText("");
                switch (this.lista_1.getSelectedValue()) {
                    case "Definiciones Regulares/Patrones activos":
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(0).getName() + " " + this.l_fuentes_mod.get(0).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(0).getRed() + ", " + this.l_colores_mod.get(0).getGreen() + ", " + this.l_colores_mod.get(0).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(1).getRed() + ", " + this.l_colores_mod.get(1).getGreen() + ", " + this.l_colores_mod.get(1).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(0));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(1));
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
                        break;
                    case "Definiciones Regulares/Patrones NO activos":
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(1).getName() + " " + this.l_fuentes_mod.get(1).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(2).getRed() + ", " + this.l_colores_mod.get(2).getGreen() + ", " + this.l_colores_mod.get(2).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(3).getRed() + ", " + this.l_colores_mod.get(3).getGreen() + ", " + this.l_colores_mod.get(3).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(2));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(3));
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
                        break;
                    default:
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(2).getName() + " " + this.l_fuentes_mod.get(2).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(4).getRed() + ", " + this.l_colores_mod.get(4).getGreen() + ", " + this.l_colores_mod.get(4).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(5).getRed() + ", " + this.l_colores_mod.get(5).getGreen() + ", " + this.l_colores_mod.get(5).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(2).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(4));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(5));
                        StyleConstants.setFontSize(style, this.l_fuentes_mod.get(2).getSize());
                        if (this.l_fuentes_mod.get(2).getStyle() == 0) {
                            StyleConstants.setBold(style, false);
                            StyleConstants.setItalic(style, false);
                        }    
                        else if (this.l_fuentes_mod.get(2).getStyle() == 2) {
                            StyleConstants.setBold(style, false);
                            StyleConstants.setItalic(style, true);
                        } else {
                            StyleConstants.setBold(style, true);
                            StyleConstants.setItalic(style, false);
                        }
                        break;
                }
                try {
                    doc.insertString(doc.getLength(), "Palabra reservada", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!this.lista_2.isSelectionEmpty()) {
                switch (this.lista_2.getSelectedValue()) {
                    case "Lexemas COMPLETAMENTE detectados en la entrada":
                        tx_fuente_2.setText(this.l_fuentes_mod.get(3).getName() + " " + this.l_fuentes_mod.get(3).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(6).getRed() + ", " + this.l_colores_mod.get(6).getGreen() + ", " + this.l_colores_mod.get(6).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(7).getRed() + ", " + this.l_colores_mod.get(7).getGreen() + ", " + this.l_colores_mod.get(7).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(3).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(6));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(7));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(3).getSize());
                        if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "while", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Lexemas NO detectados en la entrada":
                        tx_fuente_2.setText(this.l_fuentes_mod.get(4).getName() + " " + this.l_fuentes_mod.get(4).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(8).getRed() + ", " + this.l_colores_mod.get(8).getGreen() + ", " + this.l_colores_mod.get(8).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(9).getRed() + ", " + this.l_colores_mod.get(9).getGreen() + ", " + this.l_colores_mod.get(9).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(4).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(8));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(9));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(4).getSize());
                        if (this.l_fuentes_mod.get(4).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(4).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "6&€?l", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        tx_fuente_2.setText(this.l_fuentes_mod.get(5).getName() + " " + this.l_fuentes_mod.get(5).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(10).getRed() + ", " + this.l_colores_mod.get(10).getGreen() + ", " + this.l_colores_mod.get(10).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(11).getRed() + ", " + this.l_colores_mod.get(11).getGreen() + ", " + this.l_colores_mod.get(11).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(5).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(10));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(11));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(5).getSize());
                        if (this.l_fuentes_mod.get(5).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(5).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "whil", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_boton_defectoActionPerformed

    private void boton_reestablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reestablecerActionPerformed
        StyledDocument doc = this.tx_pv_1.getStyledDocument();
        Style style = this.tx_pv_1.addStyle("Style", null);
        StyledDocument doc1 = this.tx_pv_2.getStyledDocument();
        Style style1 = this.tx_pv_2.addStyle("Style", null);
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de reestablecer los valores?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) { 
            this.l_fuentes_mod.clear();
            this.l_fuentes_mod.addAll(l_fuentes);
            this.l_colores_mod.clear();
            this.l_colores_mod.addAll(l_colores);

            if (!this.lista_1.isSelectionEmpty()) {
                this.tx_pv_1.setText("");
                this.tx_pv_2.setText("");
                switch (this.lista_1.getSelectedValue()) {
                    case "Definiciones Regulares/Patrones activos":
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(0).getName() + " " + this.l_fuentes_mod.get(0).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(0).getRed() + ", " + this.l_colores_mod.get(0).getGreen() + ", " + this.l_colores_mod.get(0).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(1).getRed() + ", " + this.l_colores_mod.get(1).getGreen() + ", " + this.l_colores_mod.get(1).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(0).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(0));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(1));
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
                        break;
                    case "Definiciones Regulares/Patrones NO activos":
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(1).getName() + " " + this.l_fuentes_mod.get(1).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(2).getRed() + ", " + this.l_colores_mod.get(2).getGreen() + ", " + this.l_colores_mod.get(2).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(3).getRed() + ", " + this.l_colores_mod.get(3).getGreen() + ", " + this.l_colores_mod.get(3).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(1).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(2));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(3));
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
                        break;
                    default:
                        this.tx_fuente_1.setText(this.l_fuentes_mod.get(2).getName() + " " + this.l_fuentes_mod.get(2).getSize());
                        this.tx_color_fuente_1.setText(this.l_colores_mod.get(4).getRed() + ", " + this.l_colores_mod.get(4).getGreen() + ", " + this.l_colores_mod.get(4).getBlue());
                        this.tx_color_fondo_1.setText(this.l_colores_mod.get(5).getRed() + ", " + this.l_colores_mod.get(5).getGreen() + ", " + this.l_colores_mod.get(5).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style, this.l_fuentes_mod.get(2).getFamily());
                        StyleConstants.setForeground(style, this.l_colores_mod.get(4));
                        StyleConstants.setBackground(style, this.l_colores_mod.get(5));
                        StyleConstants.setFontSize(style, this.l_fuentes_mod.get(2).getSize());
                        if (this.l_fuentes_mod.get(2).getStyle() == 0) {
                            StyleConstants.setBold(style, false);
                            StyleConstants.setItalic(style, false);
                        }    
                        else if (this.l_fuentes_mod.get(2).getStyle() == 2) {
                            StyleConstants.setBold(style, false);
                            StyleConstants.setItalic(style, true);
                        } else {
                            StyleConstants.setBold(style, true);
                            StyleConstants.setItalic(style, false);
                        }
                        break;
                }
                try {
                    doc.insertString(doc.getLength(), "Palabra reservada", style);
                } catch (BadLocationException ex) {
                    Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!this.lista_2.isSelectionEmpty()) {
                switch (this.lista_2.getSelectedValue()) {
                    case "Lexemas COMPLETAMENTE detectados en la entrada":
                        tx_fuente_2.setText(this.l_fuentes_mod.get(3).getName() + " " + this.l_fuentes_mod.get(3).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(6).getRed() + ", " + this.l_colores_mod.get(6).getGreen() + ", " + this.l_colores_mod.get(4).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(7).getRed() + ", " + this.l_colores_mod.get(7).getGreen() + ", " + this.l_colores_mod.get(5).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(3).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(6));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(7));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(3).getSize());
                        if (this.l_fuentes_mod.get(3).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(3).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "while", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Lexemas NO detectados en la entrada":
                        tx_fuente_2.setText(this.l_fuentes_mod.get(4).getName() + " " + this.l_fuentes_mod.get(4).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(8).getRed() + ", " + this.l_colores_mod.get(8).getGreen() + ", " + this.l_colores_mod.get(8).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(9).getRed() + ", " + this.l_colores_mod.get(9).getGreen() + ", " + this.l_colores_mod.get(9).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(4).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(8));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(9));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(4).getSize());
                        if (this.l_fuentes_mod.get(4).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(4).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "6&€?l", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        tx_fuente_2.setText(this.l_fuentes_mod.get(5).getName() + " " + this.l_fuentes_mod.get(5).getSize());
                        this.tx_color_fuente_2.setText(this.l_colores_mod.get(10).getRed() + ", " + this.l_colores_mod.get(10).getGreen() + ", " + this.l_colores_mod.get(10).getBlue());
                        this.tx_color_fondo_2.setText(this.l_colores_mod.get(11).getRed() + ", " + this.l_colores_mod.get(11).getGreen() + ", " + this.l_colores_mod.get(11).getBlue());
                        // Configuración previsualización
                        StyleConstants.setFontFamily(style1, this.l_fuentes_mod.get(5).getFamily());
                        StyleConstants.setForeground(style1, this.l_colores_mod.get(10));
                        StyleConstants.setBackground(style1, this.l_colores_mod.get(11));
                        StyleConstants.setFontSize(style1, this.l_fuentes_mod.get(5).getSize());
                        if (this.l_fuentes_mod.get(5).getStyle() == 0) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, false);
                        }    
                        else if (this.l_fuentes_mod.get(5).getStyle() == 2) {
                            StyleConstants.setBold(style1, false);
                            StyleConstants.setItalic(style1, true);
                        } else {
                            StyleConstants.setBold(style1, true);
                            StyleConstants.setItalic(style1, false);
                        }
                        try {
                            doc1.insertString(doc1.getLength(), "whil", style1);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(P_ajustes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_boton_reestablecerActionPerformed

    private void tx_color_fondo_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_color_fondo_2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_color_fondo_2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator Separador_2;
    private javax.swing.JButton boton_aplicar;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_color_fondo_1;
    private javax.swing.JButton boton_color_fondo_2;
    private javax.swing.JButton boton_color_fuente_1;
    private javax.swing.JButton boton_color_fuente_2;
    private javax.swing.JButton boton_defecto;
    private javax.swing.JButton boton_fuente_1;
    private javax.swing.JButton boton_fuente_2;
    private javax.swing.JButton boton_reestablecer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_ayuda;
    private javax.swing.JLabel lb_color_fondo_1;
    private javax.swing.JLabel lb_color_fondo_2;
    private javax.swing.JLabel lb_color_fuente_1;
    private javax.swing.JLabel lb_color_fuente_2;
    private javax.swing.JLabel lb_fuente_1;
    private javax.swing.JLabel lb_fuente_2;
    private javax.swing.JLabel lb_titulo_1;
    private javax.swing.JLabel lb_titulo_2;
    private javax.swing.JLabel lb_titulo_3;
    private javax.swing.JLabel lb_titulo_4;
    private javax.swing.JList<String> lista_1;
    private javax.swing.JList<String> lista_2;
    private javax.swing.JScrollPane scroll_lista_1;
    private javax.swing.JScrollPane scroll_lista_2;
    private javax.swing.JSeparator separador_1;
    private javax.swing.JTextField tx_color_fondo_1;
    private javax.swing.JTextField tx_color_fondo_2;
    private javax.swing.JTextField tx_color_fuente_1;
    private javax.swing.JTextField tx_color_fuente_2;
    private javax.swing.JTextField tx_fuente_1;
    private javax.swing.JTextField tx_fuente_2;
    private javax.swing.JTextPane tx_pv_1;
    private javax.swing.JTextPane tx_pv_2;
    // End of variables declaration//GEN-END:variables
}
