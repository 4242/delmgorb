package com.organization4242.delmgorb.utils;

import com.organization4242.delmgorb.application.Application;
import com.organization4242.delmgorb.model.PointsArray;
import com.organization4242.delmgorb.view.MainWindowView;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Created by ilya-murzinov on 02.03.14.
 */
public final class XmlImporter {
    private final static String VALUE = "value";

    private static PointsArray array;

    private XmlImporter() {

    }

    private static void init(File newFile) {
        File file = newFile;
        try {
            SAXParserFactory parserFactor = SAXParserFactory.newInstance();
            SAXParser parser = parserFactor.newSAXParser();
            SAXHandler handler = new SAXHandler();
            InputStream stream = new FileInputStream(file);
            parser.parse(stream, handler);
        } catch (SAXException ex) {
            Application.logger.log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            Application.logger.log(Level.SEVERE, ex.getMessage());
        } catch (ParserConfigurationException ex) {
            Application.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    public static PointsArray importData(MainWindowView view) {
        init(OpenFileHelper.open(view));
        return array;
    }

    public static void importConfig(MainWindowView view) {
        init(OpenFileHelper.open(view));
    }

    private static class SAXHandler extends DefaultHandler {
        private Double[] xVal;
        private Double[] yVal;
        private Double[][] fVal;

        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes)
                throws SAXException {
            if (qName.equals("NumberOfPoints")) {
                int n = Integer.parseInt(attributes.getValue(VALUE));
                xVal = new Double[n];
                yVal = new Double[n];
                fVal = new Double[n][n];
            } else if (qName.equals("xVal")) {
                xVal[Integer.parseInt(attributes.getValue("i"))] =
                        Double.parseDouble(attributes.getValue(VALUE));
            } else if (qName.equals("yVal")) {
                yVal[Integer.parseInt(attributes.getValue("j"))] =
                        Double.parseDouble(attributes.getValue(VALUE));
            } else if (qName.equals("fVal")) {
                fVal[Integer.parseInt(attributes.getValue("i"))][Integer.parseInt(attributes.getValue("j"))] =
                        Double.parseDouble(attributes.getValue(VALUE));
            }
        }

        @Override
        public void endDocument() {
            array = new PointsArray(xVal, yVal, fVal);
        }
    }
}
