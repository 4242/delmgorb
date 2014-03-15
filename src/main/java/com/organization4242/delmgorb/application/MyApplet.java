package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.model.PlotBuilder;
import com.organization4242.delmgorb.model.DataModel;
import com.organization4242.delmgorb.model.MainWindowModel;
import com.organization4242.delmgorb.model.Serializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

/**
 * Created by ilya-murzinov on 11.03.14.
 */
public class MyApplet extends Applet {
    PlotBuilder plotBuilder = new PlotBuilder();
    XStream xStream = new XStream(new DomDriver());

    @Override
    public void init() {
        setLayout(new BorderLayout());
        setSize(800, 600);
        Serializer serializer;
        serializer = (Serializer) xStream.fromXML(getClass().getResourceAsStream("/sample.xml"));
        DataModel dataModel = serializer.getDataModel();
        MainWindowModel mainWindowModel = serializer.getMainWindowModel();
        plotBuilder.setDataModel(dataModel);
        plotBuilder.setMainWindowModel(mainWindowModel);
        JPanel panel = plotBuilder.getSample();
        add(panel);
    }
}
