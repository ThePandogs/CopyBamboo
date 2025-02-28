/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.ClassifyTypes;
import Controller.LectorController;
import Log.LogHandler;
import View.Customize.Theme.ThemeDetector.os.OsThemeDetector;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Desktop;
import java.awt.Window;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ThePandogs
 *
 * Clase principal que representa la interfaz de la aplicación. Extiende de
 * javax.swing.JFrame para crear una ventana de aplicación.
 */
public class mainWindow extends javax.swing.JFrame {

    /**
     * Constructor de la clase Interfaz. Inicializa los componentes de la
     * interfaz, crea una instancia de la clase Gestion, configura el fondo de
     * la ventana como transparente y habilita la funcionalidad de arrastrar la
     * ventana.
     */
    LogHandler logWindow;

    private final Map<JRadioButton, ClassifyTypes> radioButtonMap;

    public mainWindow() {
        // Obtener el detector de temas
        final OsThemeDetector detector = OsThemeDetector.getDetector();

        // Detectar el tema actual al inicio y aplicarlo
        boolean isDark = detector.isDark();
        try {
            if (isDark) {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            }
            // Refresca la apariencia de todos los componentes al inicio
            SwingUtilities.invokeLater(() -> {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    SwingUtilities.updateComponentTreeUI(window);
                }
            });
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Registrar el listener para cambios futuros en el tema
        detector.registerListener(isDarkTheme -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    if (isDarkTheme) {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    } else {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    }
                    // Refresca la apariencia de todos los componentes cuando cambia el tema
                    Window[] windows = Window.getWindows();
                    for (Window window : windows) {
                        SwingUtilities.updateComponentTreeUI(window);
                    }
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        initComponents();
        logWindow = new LogHandler(jtextLog);

        radioButtonMap = Map.of(
                radioBtnCreationDate, ClassifyTypes.CREATION_DATE,
                radioBtnMetaDate, ClassifyTypes.CREATION_DATE_META,
                radioBtnLastModificationDate, ClassifyTypes.CREATION_DATE_MODIFY,
                radioBtnExtension, ClassifyTypes.FILE_EXTENSION,
                radioBtnType, ClassifyTypes.FILE_TYPE
        );

    }

    // <editor-fold defaultstate="collapsed" desc="GETTERS_SETTERS">
    // </editor-fold>
    @SuppressWarnings(value = "unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        radioGroupOrganize = new javax.swing.ButtonGroup();
        background = new Components.Background();
        jSplitPane2 = new javax.swing.JSplitPane();
        Log = new Components.RoundedPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtextLog = new javax.swing.JTextArea();
        Form = new javax.swing.JPanel();
        panelRedondeado2 = new Components.RoundedPanel();
        txtOriginDirectory = new javax.swing.JTextField();
        lblOriginDirectory = new javax.swing.JLabel();
        txtDestinationDirectory = new javax.swing.JTextField();
        lblDestinarionDirectory = new javax.swing.JLabel();
        btnOriginDirectory = new javax.swing.JButton();
        btnDestinationDirectory = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        pnlOptions = new Components.RoundedPanel();
        pnlOrganize = new javax.swing.JPanel();
        pnlDate = new javax.swing.JPanel();
        radioBtnCreationDate = new javax.swing.JRadioButton();
        radioBtnLastModificationDate = new javax.swing.JRadioButton();
        radioBtnMetaDate = new javax.swing.JRadioButton();
        pnlTypeFile = new javax.swing.JPanel();
        radioBtnExtension = new javax.swing.JRadioButton();
        radioBtnType = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        chkrenameFileDate = new javax.swing.JCheckBox();
        chkFolderNotClasified = new javax.swing.JCheckBox();
        chkOverwriteFile = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        JMenuFile = new javax.swing.JMenu();
        jMenuExit = new javax.swing.JMenuItem();
        JMenuHelp = new javax.swing.JMenu();
        jMenuDoc = new javax.swing.JMenuItem();
        jMenuProfile = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CopyBamboo");
        setIconImage(new ImageIcon(getClass().getResource("/img/bambu.png")).getImage());
        setMinimumSize(new java.awt.Dimension(800, 800));
        setName("CopyBamboo"); // NOI18N
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        background.setBackground(new java.awt.Color(102, 102, 102));
        background.setMaximumSize(new java.awt.Dimension(700, 500));
        background.setMinimumSize(new java.awt.Dimension(700, 500));
        background.setPreferredSize(new java.awt.Dimension(700, 500));
        background.setRound(40);
        background.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        jSplitPane2.setDividerLocation(500);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Log.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        Log.setPreferredSize(new java.awt.Dimension(0, 0));
        Log.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane2.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane2.setBorder(null);

        jtextLog.setEditable(false);
        jtextLog.setColumns(20);
        jtextLog.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jtextLog.setForeground(new java.awt.Color(255, 255, 255));
        jtextLog.setLineWrap(true);
        jtextLog.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        jtextLog.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(jtextLog);

        Log.add(jScrollPane2);

        jSplitPane2.setBottomComponent(Log);

        Form.setMinimumSize(new java.awt.Dimension(0, 0));
        Form.setPreferredSize(new java.awt.Dimension(0, 0));

        panelRedondeado2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 0), java.awt.Color.white), "Directories", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 0, 18))); // NOI18N
        panelRedondeado2.setMaximumSize(new java.awt.Dimension(720, 240));
        panelRedondeado2.setMinimumSize(new java.awt.Dimension(720, 240));
        panelRedondeado2.setPreferredSize(new java.awt.Dimension(720, 240));
        panelRedondeado2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 428;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 15, 0);
        panelRedondeado2.add(txtOriginDirectory, gridBagConstraints);

        lblOriginDirectory.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lblOriginDirectory.setText("Source Directory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        panelRedondeado2.add(lblOriginDirectory, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 428;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 15, 0);
        panelRedondeado2.add(txtDestinationDirectory, gridBagConstraints);

        lblDestinarionDirectory.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        lblDestinarionDirectory.setText("Destination Directory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        panelRedondeado2.add(lblDestinarionDirectory, gridBagConstraints);

        btnOriginDirectory.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        btnOriginDirectory.setForeground(new java.awt.Color(0, 153, 51));
        btnOriginDirectory.setText("Choose Folder...");
        btnOriginDirectory.setMinimumSize(new java.awt.Dimension(0, 0));
        btnOriginDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginDirectoryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 0, 0);
        panelRedondeado2.add(btnOriginDirectory, gridBagConstraints);

        btnDestinationDirectory.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        btnDestinationDirectory.setForeground(new java.awt.Color(0, 153, 51));
        btnDestinationDirectory.setText("Choose Folder...");
        btnDestinationDirectory.setMinimumSize(new java.awt.Dimension(0, 0));
        btnDestinationDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDestinationDirectoryActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 15, 0);
        panelRedondeado2.add(btnDestinationDirectory, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 51));
        jButton1.setText("Start");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setMargin(new java.awt.Insets(4, 20, 4, 20));
        jButton1.setMaximumSize(new java.awt.Dimension(90, 29));
        jButton1.setMinimumSize(new java.awt.Dimension(90, 29));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        panelRedondeado2.add(jButton1, gridBagConstraints);

        Form.add(panelRedondeado2);

        pnlOptions.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 0), java.awt.Color.white), "Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 0, 18))); // NOI18N
        pnlOptions.setMaximumSize(new java.awt.Dimension(32767, 240));
        pnlOptions.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlOptions.setPreferredSize(new java.awt.Dimension(720, 240));
        pnlOptions.setLayout(new java.awt.GridBagLayout());

        pnlOrganize.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 0), java.awt.Color.white), "Classified By", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 0, 14))); // NOI18N
        pnlOrganize.setMinimumSize(new java.awt.Dimension(450, 175));
        pnlOrganize.setPreferredSize(new java.awt.Dimension(450, 175));

        pnlDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Bahnschrift", 0, 14))); // NOI18N
        pnlDate.setMaximumSize(new java.awt.Dimension(180, 140));
        pnlDate.setMinimumSize(new java.awt.Dimension(180, 140));
        pnlDate.setOpaque(false);
        pnlDate.setPreferredSize(new java.awt.Dimension(180, 140));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0};
        jPanel1Layout.rowHeights = new int[] {0, 0, 0, 0, 0};
        pnlDate.setLayout(jPanel1Layout);

        radioGroupOrganize.add(radioBtnCreationDate);
        radioBtnCreationDate.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        radioBtnCreationDate.setSelected(true);
        radioBtnCreationDate.setText("Creation Date");
        radioBtnCreationDate.setToolTipText("Filter files by their actual creation date on disk."); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        pnlDate.add(radioBtnCreationDate, gridBagConstraints);

        radioGroupOrganize.add(radioBtnLastModificationDate);
        radioBtnLastModificationDate.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        radioBtnLastModificationDate.setText("Last Modification");
        radioBtnLastModificationDate.setToolTipText("Filter files by their last modified date.");
        radioBtnLastModificationDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnLastModificationDateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 6, 0);
        pnlDate.add(radioBtnLastModificationDate, gridBagConstraints);

        radioGroupOrganize.add(radioBtnMetaDate);
        radioBtnMetaDate.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        radioBtnMetaDate.setText("Creation Meta Date");
        radioBtnMetaDate.setToolTipText("Filter files by metadata creation date stored in the file properties.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 6, 0);
        pnlDate.add(radioBtnMetaDate, gridBagConstraints);

        pnlOrganize.add(pnlDate);

        pnlTypeFile.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "TypeFile", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Bahnschrift", 0, 14))); // NOI18N
        pnlTypeFile.setMaximumSize(new java.awt.Dimension(180, 140));
        pnlTypeFile.setMinimumSize(new java.awt.Dimension(180, 140));
        pnlTypeFile.setPreferredSize(new java.awt.Dimension(180, 140));
        pnlTypeFile.setLayout(new java.awt.GridBagLayout());

        radioGroupOrganize.add(radioBtnExtension);
        radioBtnExtension.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        radioBtnExtension.setText("Extension");
        radioBtnExtension.setToolTipText("Filter by file type (e.g., .jpg, .pdf)");
        radioBtnExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioBtnExtensionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        pnlTypeFile.add(radioBtnExtension, gridBagConstraints);

        radioGroupOrganize.add(radioBtnType);
        radioBtnType.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        radioBtnType.setText("Pictures,Videos,Documents...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 4, 0);
        pnlTypeFile.add(radioBtnType, gridBagConstraints);

        pnlOrganize.add(pnlTypeFile);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlOptions.add(pnlOrganize, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 153, 0), java.awt.Color.white), "Parameters", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 0, 14))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(210, 175));
        jPanel2.setMinimumSize(new java.awt.Dimension(210, 175));
        jPanel2.setPreferredSize(new java.awt.Dimension(210, 175));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        chkrenameFileDate.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        chkrenameFileDate.setText("Rename File with Date");
        chkrenameFileDate.setToolTipText("Rename the file with the choosed date");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel2.add(chkrenameFileDate, gridBagConstraints);

        chkFolderNotClasified.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        chkFolderNotClasified.setSelected(true);
        chkFolderNotClasified.setText("Unclassified folder         ");
        chkFolderNotClasified.setToolTipText("Create unclassified folder");
        chkFolderNotClasified.setActionCommand("Create unclassified folder");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel2.add(chkFolderNotClasified, gridBagConstraints);

        chkOverwriteFile.setFont(new java.awt.Font("Bahnschrift", 0, 13)); // NOI18N
        chkOverwriteFile.setText("Overwrite File");
        chkOverwriteFile.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel2.add(chkOverwriteFile, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlOptions.add(jPanel2, gridBagConstraints);

        Form.add(pnlOptions);

        jSplitPane2.setLeftComponent(Form);

        background.add(jSplitPane2);

        getContentPane().add(background);

        JMenuFile.setBackground(new java.awt.Color(255, 51, 51));
        JMenuFile.setForeground(new java.awt.Color(255, 255, 255));
        JMenuFile.setText("File");

        jMenuExit.setText("Exit");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        JMenuFile.add(jMenuExit);

        jMenuBar1.add(JMenuFile);

        JMenuHelp.setText("Help");

        jMenuDoc.setText("Documentation");
        jMenuDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuDocActionPerformed(evt);
            }
        });
        JMenuHelp.add(jMenuDoc);

        jMenuProfile.setText("ThePandogs");
        jMenuProfile.setToolTipText("My github profile");
        jMenuProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuProfileActionPerformed(evt);
            }
        });
        JMenuHelp.add(jMenuProfile);

        jMenuBar1.add(JMenuHelp);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOriginDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginDirectoryActionPerformed
        openChooseFolder(txtOriginDirectory);
    }//GEN-LAST:event_btnOriginDirectoryActionPerformed

    private void btnDestinationDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDestinationDirectoryActionPerformed
        openChooseFolder(txtDestinationDirectory);
    }//GEN-LAST:event_btnDestinationDirectoryActionPerformed

    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        openDialogExit();
    }//GEN-LAST:event_jMenuExitActionPerformed

    private void jMenuDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuDocActionPerformed
        openWebpage("https://github.com/ThePandogs/CopyBamboo");
    }//GEN-LAST:event_jMenuDocActionPerformed

    private void radioBtnLastModificationDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnLastModificationDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBtnLastModificationDateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doCopy();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void doCopy() {

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String origin = txtOriginDirectory.getText();
                String destination = txtDestinationDirectory.getText();
                ClassifyTypes classifyType = getChooseClasiffyRadioButton();

                return new LectorController(logWindow).copyDirectory(
                        origin,
                        destination,
                        classifyType,
                        chkrenameFileDate.isSelected(),
                        chkFolderNotClasified.isSelected(),
                        chkOverwriteFile.isSelected()
                );

            }

            @Override
            protected void done() {
                try {
                    boolean result = get();
                    logWindow.appendLog(result ? "Finish" : "Error");
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        worker.execute();

    }
    private void radioBtnExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBtnExtensionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBtnExtensionActionPerformed

    private void jMenuProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuProfileActionPerformed
        openWebpage("https://github.com/ThePandogs/CopyBamboo");
    }//GEN-LAST:event_jMenuProfileActionPerformed

    private void openChooseFolder(JTextField textField) {

        JFileChooser fileChooser = new JFileChooser(textField.getText());

        // Only show directories
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedDirectory = fileChooser.getSelectedFile();
            textField.setText(selectedDirectory.getAbsolutePath());

        }
    }

    private ClassifyTypes getChooseClasiffyRadioButton() {

        return radioButtonMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isSelected())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(ClassifyTypes.CREATION_DATE);
    }

    private void openDialogExit() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to exit the application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void openWebpage(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));

            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(); // Manejo de errores
            }
        } else {
            // La funcionalidad de apertura de URL no es compatible en este entorno
            JOptionPane.showMessageDialog(null, "Can't open the URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(mainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new mainWindow().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Form;
    private javax.swing.JMenu JMenuFile;
    private javax.swing.JMenu JMenuHelp;
    private Components.RoundedPanel Log;
    private Components.Background background;
    private javax.swing.JButton btnDestinationDirectory;
    private javax.swing.JButton btnOriginDirectory;
    private javax.swing.JCheckBox chkFolderNotClasified;
    private javax.swing.JCheckBox chkOverwriteFile;
    private javax.swing.JCheckBox chkrenameFileDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuDoc;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuItem jMenuProfile;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jtextLog;
    private javax.swing.JLabel lblDestinarionDirectory;
    private javax.swing.JLabel lblOriginDirectory;
    private Components.RoundedPanel panelRedondeado2;
    private javax.swing.JPanel pnlDate;
    private Components.RoundedPanel pnlOptions;
    private javax.swing.JPanel pnlOrganize;
    private javax.swing.JPanel pnlTypeFile;
    private javax.swing.JRadioButton radioBtnCreationDate;
    private javax.swing.JRadioButton radioBtnExtension;
    private javax.swing.JRadioButton radioBtnLastModificationDate;
    private javax.swing.JRadioButton radioBtnMetaDate;
    private javax.swing.JRadioButton radioBtnType;
    private javax.swing.ButtonGroup radioGroupOrganize;
    private javax.swing.JTextField txtDestinationDirectory;
    private javax.swing.JTextField txtOriginDirectory;
    // End of variables declaration//GEN-END:variables

}
