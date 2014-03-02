package com.organization4242.delmgorb.utils;

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

/**
 * Created by ilya-murzinov on 02.03.14.
 */
public class XmlImporter {
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

        } catch (IOException ex) {

        } catch (ParserConfigurationException ex) {

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
                int n = Integer.parseInt(attributes.getValue("value"));
                xVal = new Double[n];
                yVal = new Double[n];
                fVal = new Double[n][n];
            } else if (qName.equals("xVal")) {
                xVal[Integer.parseInt(attributes.getValue("i"))] = Double.parseDouble(attributes.getValue("value"));
            } else if (qName.equals("yVal")) {
                yVal[Integer.parseInt(attributes.getValue("j"))] = Double.parseDouble(attributes.getValue("value"));
            } else if (qName.equals("fVal")) {
                fVal[Integer.parseInt(attributes.getValue("i"))][Integer.parseInt(attributes.getValue("j"))] =
                        Double.parseDouble(attributes.getValue("value"));
            }
        }

        @Override
        public void endDocument() {
            array = new PointsArray(xVal, yVal, fVal);
        }
    }
}
