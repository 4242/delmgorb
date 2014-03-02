package com.organization4242.delmgorb.model;

import com.organization4242.delmgorb.application.Application;
import com.organization4242.delmgorb.view.MainWindowView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Level;

/**
 * Created by ilya-murzinov on 01.03.14.
 */
public final class XmlExporter {
    private final static String VALUE = "value";

    private static Document doc;
    private static Element data;
    private static Element config;

    private XmlExporter() {

    }

    public static Boolean canExport() {
        return (doc != null);
    }

    public static void init() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("export");
            doc.appendChild(rootElement);

            config = doc.createElement("config");
            rootElement.appendChild(config);

            data = doc.createElement("data");
            rootElement.appendChild(data);
        } catch (ParserConfigurationException ex) {
            Application.logger.log(Level.SEVERE, ex.getMessage());
        }

    }

    public static void export(PointsArray pointsArray) {
        for (Integer i = 0; i < pointsArray.getxVal().length; i++) {
            for (Integer j = 0; j < pointsArray.getxVal().length; j++) {
                Element xVal = doc.createElement("xVal");
                xVal.setAttribute(VALUE, pointsArray.getxVal()[i].toString());
                xVal.setAttribute("i", i.toString());
                data.appendChild(xVal);

                Element yVal = doc.createElement("yVal");
                yVal.setAttribute(VALUE, pointsArray.getyVal()[j].toString());
                yVal.setAttribute("j", j.toString());
                data.appendChild(yVal);

                Element fVal = doc.createElement("fVal");
                fVal.setAttribute(VALUE, pointsArray.getfVal()[i][j].toString());
                fVal.setAttribute("i", i.toString());
                fVal.setAttribute("j", j.toString());
                data.appendChild(fVal);
            }
        }
    }

    public static void exportConfig(MainWindowView view) {
        Element parameter = doc.createElement("numberOfPoints");
        parameter.setAttribute(VALUE, view.getNumberOfPoints().getText());
        config.appendChild(parameter);
        parameter = doc.createElement("timeStep");
        parameter.setAttribute(VALUE, view.getTimeStep().getText());
        config.appendChild(parameter);
        parameter = doc.createElement("timePeriod");
        parameter.setAttribute(VALUE, view.getPeriodToInterpolate().getText());
        config.appendChild(parameter);
        parameter = doc.createElement("integrationMethod");
        parameter.setAttribute(VALUE, view.getIntegrationMethodsComboBox().getSelectedItem().toString());
        config.appendChild(parameter);
        parameter = doc.createElement("angleToPlot");
        parameter.setAttribute(VALUE, view.getBuildingAngleJComboBox().getSelectedItem().toString());
        config.appendChild(parameter);
        parameter = doc.createElement("xMin");
        parameter.setAttribute(VALUE, view.getBoundsTextFields()[0].getText());
        config.appendChild(parameter);
        parameter = doc.createElement("xMax");
        parameter.setAttribute(VALUE, view.getBoundsTextFields()[1].getText());
        config.appendChild(parameter);
        parameter = doc.createElement("yMin");
        parameter.setAttribute(VALUE, view.getBoundsTextFields()[2].getText());
        config.appendChild(parameter);
        parameter = doc.createElement("yMax");
        parameter.setAttribute(VALUE, view.getBoundsTextFields()[3].getText());
        config.appendChild(parameter);
    }

    public static void close(File file) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Application.logger.log(Level.SEVERE, ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Data was exported to " + file.getAbsolutePath());
    }
}
