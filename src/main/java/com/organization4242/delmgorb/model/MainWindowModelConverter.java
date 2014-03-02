package com.organization4242.delmgorb.model;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Created by ilya-murzinov on 02.03.14.
 */
public class MainWindowModelConverter implements Converter {
    private final static String VALUE = "value";

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        MainWindowModel model = (MainWindowModel) o;

        hierarchicalStreamWriter.startNode("config");
        hierarchicalStreamWriter.startNode("numberOfPoints");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getNumberOfPoints().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("timeStep");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getTimeStep().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("timePeriod");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getTimePeriod().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("angleToPlot");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getBuildingAngle().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("integrationMethod");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getIntegrationMethod().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("xMin");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getxMin().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("xMax");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getxMax().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("yMin");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getyMin().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.startNode("yMax");
        hierarchicalStreamWriter.addAttribute(VALUE, model.getyMax().toString());
        hierarchicalStreamWriter.endNode();
        hierarchicalStreamWriter.endNode();

        hierarchicalStreamWriter.startNode("data");
        for (Integer i = 0; i < model.getPointsArray().getxVal().length; i++) {
            for (Integer j = 0; j < model.getPointsArray().getxVal().length; j++) {
                hierarchicalStreamWriter.startNode("xVal");
                hierarchicalStreamWriter.addAttribute(VALUE, model.getPointsArray().getxVal()[i].toString());
                hierarchicalStreamWriter.addAttribute("i", i.toString());
                hierarchicalStreamWriter.endNode();

                hierarchicalStreamWriter.startNode("yVal");
                hierarchicalStreamWriter.addAttribute(VALUE, model.getPointsArray().getyVal()[j].toString());
                hierarchicalStreamWriter.addAttribute("j", j.toString());
                hierarchicalStreamWriter.endNode();

                hierarchicalStreamWriter.startNode("fVal");
                hierarchicalStreamWriter.addAttribute(VALUE, model.getPointsArray().getfVal()[i][j].toString());
                hierarchicalStreamWriter.addAttribute("i", i.toString());
                hierarchicalStreamWriter.addAttribute("j", j.toString());
                hierarchicalStreamWriter.endNode();
            }
        }
        hierarchicalStreamWriter.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(MainWindowModel.class);
    }
}
