package com.organization4242.delmgorb.view;

import com.organization4242.delmgorb.model.Angle;
import com.organization4242.delmgorb.model.IntegrationMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents main window with all UI controls.
 * It uses 5 panels to place UI controls ans then place all panel inside the main panel.
 * Also it adds focus listener in order to select all text in text fields when it gains focus.
 *
 * All controls are empty in the resulting window, use
 * {@link com.organization4242.delmgorb.controller.MainWindowController} to set all values.
 * 
 * @author Murzinov Ilya
 */
public class MainWindowView extends JFrame {
    private static final int NUMBER_OF_EQUATION_PARAMETERS = 14;
    private static final int HEIGHT = 650;
    private static final int WIDTH = 400;
    private static final int TEXT_FIELD_MIN_WIDTH = 4;
    private static final Insets DEFAULT_INSETS = new Insets(5,5,5,5);
    private static final Insets EMPTY_INSETS = new Insets(0,0,0,0);

    //Menu
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("File");
    private JMenuItem importDataMenuItem = new JMenuItem("Import Data and Config");
    private JMenuItem exportDataMenuItem = new JMenuItem("Export Data and Config");

    //Internal panels
    private JPanel equationParametersPanel = new JPanel();
    private JPanel integrationParametersPanel = new JPanel();
    private JPanel interpolationParametersPanel = new JPanel();
    private JPanel initialConditionsPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private GridBagLayout gridBagLayout = new GridBagLayout();

    //Labels
    private JLabel integrationMethodLabel = new JLabel("Method:");
    private JLabel numLabel = new JLabel("Number of points:");
    private JLabel timeLabel = new JLabel("Time step:");
    private JLabel boundsLabel = new JLabel("Bounds:");
    private JLabel xLabel = new JLabel("Delta:         from ");
    private JLabel yLabel = new JLabel("Epsilon:     from ");
    private JLabel xToLabel = new JLabel("    to ");
    private JLabel yToLabel = new JLabel("    to ");
    private JLabel numberOfSpheresLabel = new JLabel("Number of spheres:");
    private JLabel integrationAngle = new JLabel("Angle to plot:");
    private JLabel periodToInterpolateLabel = new JLabel("Period:");
    private JLabel phiLabel = new JLabel(Angle.PHI + "(0):");
    private JLabel psiLabel = new JLabel(Angle.PSI + "(0):");
    private JLabel thetaLabel = new JLabel(Angle.THETA + "(0):");

    //UI Controls
    private JTextField[] textFields = new JTextField[NUMBER_OF_EQUATION_PARAMETERS];
    private JTextField[] boundsTextFields = new JTextField[TEXT_FIELD_MIN_WIDTH];
    private JTextField numberOfPointsTextField;
    private JTextField timeStepTextField;
    private JTextField periodToInterpolateTextField;
    private JComboBox<IntegrationMethods> integrationMethodsComboBox;
    private JComboBox<Angle> buildingAngleJComboBox;
    private JTextField numberOfSpheresTextField;
    private JTextField phiTextField;
    private JTextField psiTextField;
    private JTextField thetaTextField;
    private JButton button;

    private Logger logger = Logger.getLogger("Delmgorb.logger");

    //Accessors
    public JMenuItem getImportDataMenuItem() {
        return importDataMenuItem;
    }

    public JMenuItem getExportDataMenuItem() {
        return exportDataMenuItem;
    }

    public JTextField[] getTextFields() {
        return textFields;
    }

    public JTextField[] getBoundsTextFields() {
        return boundsTextFields;
    }

    public JTextField getNumberOfPoints() {
        return numberOfPointsTextField;
    }

    public JTextField getTimeStep() {
        return timeStepTextField;
    }

    public JTextField getPsiTextField() {
        return psiTextField;
    }

    public JTextField getPhiTextField() {
        return phiTextField;
    }

    public JTextField getThetaTextField() {
        return thetaTextField;
    }

    public JTextField getPeriodToInterpolate() {
        return periodToInterpolateTextField;
    }

    public JTextField getNumberOfSpheresTextField() {
        return numberOfSpheresTextField;
    }

    public JButton getButton() {
        return button;
    }

    public JComboBox<IntegrationMethods> getIntegrationMethodsComboBox() {
        return integrationMethodsComboBox;
    }

    public JComboBox<Angle> getAngleJComboBox() {
        return buildingAngleJComboBox;
    }

    /**
    * Initializes main window and creates its structure without showing it.
    */
    public MainWindowView() {
        //Setting window parameters
        setTitle("Delmgorb v1.0");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Setting theme
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }

        init();
        addMenu();
        createPanelStructure();
        placeControls();
    }

    private void addMenu() {
        menu.add(importDataMenuItem);
        menu.add(new JSeparator());
        menu.add(exportDataMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * Initializes all controls in the window.
     */
    private void init() {

        for (int i=0; i< NUMBER_OF_EQUATION_PARAMETERS; i++) {
            textFields[i] = new JTextField(TEXT_FIELD_MIN_WIDTH);
        }
        for (int i=0; i<4; i++) {
            boundsTextFields[i] = new JTextField(TEXT_FIELD_MIN_WIDTH);
        }

        numberOfPointsTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        timeStepTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        periodToInterpolateTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        numberOfSpheresTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        phiTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        psiTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);
        thetaTextField = new JTextField(TEXT_FIELD_MIN_WIDTH);

        button = new JButton("Draw!");
        integrationMethodsComboBox = new JComboBox<IntegrationMethods>(IntegrationMethods.values());
        integrationMethodsComboBox.setEditable(false);
        buildingAngleJComboBox = new JComboBox<Angle>(Angle.values());
        buildingAngleJComboBox.setEditable(false);
    }

    /**
     * Creates main panel, internal panels and places internal panels inside main panel.
     */
    private void createPanelStructure() {
        JPanel mainPanel = new JPanel();
        //Set main panel layout
        mainPanel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        //Add groupboxes
        equationParametersPanel.setBorder(BorderFactory.createTitledBorder("Equation parameters"));
        integrationParametersPanel.setBorder(BorderFactory.createTitledBorder("Integration parameters"));
        initialConditionsPanel.setBorder(BorderFactory.createTitledBorder("Initial conditions (in fractions of Pi)"));
        interpolationParametersPanel.setBorder(BorderFactory.createTitledBorder("Interpolation parameters"));

        //Set GridBagConstraints
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        //Apply constraints to all internal panels
        gridBagLayout.setConstraints(equationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(integrationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(initialConditionsPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(interpolationParametersPanel, constraints);
        constraints.gridy++;
        gridBagLayout.setConstraints(buttonsPanel, constraints);

        //Add all internal panels to main panel
        mainPanel.add(equationParametersPanel);
        mainPanel.add(integrationParametersPanel);
        mainPanel.add(interpolationParametersPanel);
        mainPanel.add(initialConditionsPanel);
        mainPanel.add(buttonsPanel);

        //Add main panel to window
        getContentPane().add(mainPanel);
    }

    /**
     * Places all UI controls to equation parameters panel.
     */
    private void placeEquationParameters() {
        equationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < NUMBER_OF_EQUATION_PARAMETERS /2; i++) {
            constraints.gridx = i;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }
        constraints.gridy++;
        for (int i = NUMBER_OF_EQUATION_PARAMETERS /2; i < NUMBER_OF_EQUATION_PARAMETERS; i++) {
            constraints.gridx = i - NUMBER_OF_EQUATION_PARAMETERS /2;
            gridBagLayout.setConstraints(textFields[i], constraints);
            equationParametersPanel.add(textFields[i]);
        }

        JPanel internalPane = new JPanel();
        internalPane.setLayout(gridBagLayout);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.insets = EMPTY_INSETS;
        constraints.gridwidth = NUMBER_OF_EQUATION_PARAMETERS/2;
        gridBagLayout.setConstraints(internalPane, constraints);

        constraints.insets = DEFAULT_INSETS;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        gridBagLayout.setConstraints(boundsLabel, constraints);

        constraints.gridy++;
        gridBagLayout.setConstraints(xLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[0], constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(yToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[1], constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        gridBagLayout.setConstraints(yLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[2], constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(xToLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(boundsTextFields[3], constraints);

        for (int i = 0; i < 4; i++) {
            internalPane.add(boundsTextFields[i]);
        }

        internalPane.add(xLabel);
        internalPane.add(yLabel);
        internalPane.add(xToLabel);
        internalPane.add(yToLabel);
        internalPane.add(boundsLabel);

        equationParametersPanel.add(internalPane);
    }

    /**
     * Places all UI controls to integration parameters panel.
     */
    private void placeIntegrationParameters() {
        //Set layout to all internal panels
        integrationParametersPanel.setLayout(gridBagLayout);

        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //Set constraints to all UI controls
        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(numLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(numberOfPointsTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(timeLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(timeStepTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(periodToInterpolateLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(periodToInterpolateTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(integrationMethodLabel, constraints);
        constraints.gridx++;
        constraints.gridwidth = 4;
        gridBagLayout.setConstraints(integrationMethodsComboBox, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(integrationAngle, constraints);
        constraints.gridx++;
        constraints.gridwidth = 4;
        gridBagLayout.setConstraints(buildingAngleJComboBox, constraints);

        integrationParametersPanel.add(numLabel);
        integrationParametersPanel.add(numberOfPointsTextField);
        integrationParametersPanel.add(timeLabel);
        integrationParametersPanel.add(timeStepTextField);
        integrationParametersPanel.add(periodToInterpolateLabel);
        integrationParametersPanel.add(periodToInterpolateTextField);
        integrationParametersPanel.add(integrationMethodLabel);
        integrationParametersPanel.add(integrationMethodsComboBox);
        integrationParametersPanel.add(integrationAngle);
        integrationParametersPanel.add(buildingAngleJComboBox);
    }

    /**
     * Places all UI controls to interpolation parameters panel.
     */
    private void placeInterpolationParameters() {
        interpolationParametersPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(numberOfSpheresLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(numberOfSpheresTextField, constraints);

        interpolationParametersPanel.add(numberOfSpheresLabel);
        interpolationParametersPanel.add(numberOfSpheresTextField);
    }

    /**
     * Places all UI controls to initial conditions panel.
     */
    private void placeInitialConditions() {
        initialConditionsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(phiLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(phiTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(psiLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(psiTextField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        gridBagLayout.setConstraints(thetaLabel, constraints);
        constraints.gridx++;
        gridBagLayout.setConstraints(thetaTextField, constraints);

        initialConditionsPanel.add(phiLabel);
        initialConditionsPanel.add(phiTextField);
        initialConditionsPanel.add(psiLabel);
        initialConditionsPanel.add(psiTextField);
        initialConditionsPanel.add(thetaLabel);
        initialConditionsPanel.add(thetaTextField);
    }

    /**
     * Places all UI controls to buttons panel.
     */
    private void placeButtonControls() {
        buttonsPanel.setLayout(gridBagLayout);

        //Create GridBagConstraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = DEFAULT_INSETS;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        gridBagLayout.setConstraints(button, constraints);

        buttonsPanel.add(button);
    }

    /**
    * Combines all controls in main window panel.
    */
    private void placeControls() {
        placeEquationParameters();
        placeIntegrationParameters();
        placeInterpolationParameters();
        placeInitialConditions();
        placeButtonControls();
    }

    /**
    * When a text field gets focus, all text should be selected.
    */
    private void addActionListeners() {
        FocusListener focusListener = new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JTextField tf = (JTextField) e.getComponent();
                        tf.selectAll();
                    }
                });
            }
        };
        for (JTextField tf : getTextFields()) {
            tf.addFocusListener(focusListener);
        }
        for (JTextField tf : getBoundsTextFields()) {
            tf.addFocusListener(focusListener);
        }
        getNumberOfPoints().addFocusListener(focusListener);
        getTimeStep().addFocusListener(focusListener);
        getNumberOfSpheresTextField().addFocusListener(focusListener);
        getPeriodToInterpolate().addFocusListener(focusListener);
        getPhiTextField().addFocusListener(focusListener);
        getPsiTextField().addFocusListener(focusListener);
        getThetaTextField().addFocusListener(focusListener);
    }

    /**
    * Shows main window.
    */
    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });    
    }
}
