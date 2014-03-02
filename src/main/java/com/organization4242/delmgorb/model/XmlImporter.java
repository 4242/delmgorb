package com.organization4242.delmgorb.model;

import com.organization4242.delmgorb.application.Application;
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
    private static MainWindowModel model = new MainWindowModel();

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

    public static MainWindowModel importData(MainWindowView view) {
        init(OpenFileHelper.open(view));
        model.setPointsArray(array);
        return model;
    }

    public static MainWindowModel importConfig(MainWindowView view) {
        init(OpenFileHelper.open(view));
        return model;
    }

    private static class SAXHandler extends DefaultHandler {
        private Double[] xVal;
        private Double[] yVal;
        private Double[][] fVal;

        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes)
                throws SAXException {
            if (qName.equals("numberOfPoints")) {
                int n = Integer.parseInt(attributes.getValue(VALUE));
                model.setNumberOfPoints(n);
                xVal = new Double[n];
                yVal = new Double[n];
                fVal = new Double[n][n];
            } else if (qName.equals("timeStep")) {
                model.setTimeStep(Double.parseDouble(attributes.getValue(VALUE)));
            } else if (qName.equals("integrationMethod")) {
                model.setIntegrationMethod(IntegrationMethods.valueOf(attributes.getValue(VALUE)));
            } else if (qName.equals("timePeriod")) {
                model.setTimePeriod(Double.parseDouble(attributes.getValue(VALUE)));
            }else if (qName.equals("angleToPlot")) {
                model.setBuildingAngle(BuildingAngle.valueOf(attributes.getValue(VALUE)));
            } else if (qName.equals("xMin")) {
                model.setxMin(Float.parseFloat(attributes.getValue(VALUE)));
            } else if (qName.equals("xMax")) {
                model.setxMax(Float.parseFloat(attributes.getValue(VALUE)));
            } else if (qName.equals("yMin")) {
                model.setyMin(Float.parseFloat(attributes.getValue(VALUE)));
            } else if (qName.equals("yMax")) {
                model.setyMax(Float.parseFloat(attributes.getValue(VALUE)));
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
