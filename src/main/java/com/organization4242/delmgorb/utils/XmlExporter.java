package com.organization4242.delmgorb.utils;

import com.organization4242.delmgorb.view.MainWindowView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by ilya-murzinov on 01.03.14.
 */
public class XmlExporter {
    private static Document doc;
    private static Element data;
    private static Element config;

    public Boolean canExport() {
        return (doc == null);
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
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

    }

    public static void exportParameter(Double xValue, Double yValue, Double fValue) {
        // shorten way
        // staff.setAttribute("id", "1");

        // parameter elements
        Element parameter = doc.createElement("point");
        parameter.setAttribute("xVal", xValue.toString());
        parameter.setAttribute("yVal", yValue.toString());
        parameter.setAttribute("fVal", fValue.toString());
        data.appendChild(parameter);
    }

    public static void exportConfig(MainWindowView view) {
        // shorten way
        // staff.setAttribute("id", "1");

        // parameter elements
        Element parameter = doc.createElement("NumberOfPoints");
        parameter.setAttribute("value", view.getNumberOfPoints().getText());
        config.appendChild(parameter);
    }

    public static void close(File file) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
