package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.controller.DialogWindowController;
import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.model.InterpolatorModel;
import com.organization4242.delmgorb.model.MainWindowModel;
import com.organization4242.delmgorb.model.PlotBuilder;
import com.organization4242.delmgorb.view.DialogWindowView;
import com.organization4242.delmgorb.view.MainWindowView;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Murzinov Ilya, murz42@gmail.com
 *         Date 12.05.14
 */
public class ApplicationNoSpring {
    private ApplicationNoSpring() {

    }

    public static void main(String[] args) {
        MainWindowModel mainWindowModel = new MainWindowModel();
        MainWindowView mainWindowView = new MainWindowView();

        InterpolatorModel interpolatorModel = new InterpolatorModel();
        PlotBuilder plotBuilder = new PlotBuilder();
        plotBuilder.setInterpolatorModel(interpolatorModel);

        MainWindowController mainWindowController = new MainWindowController();
        mainWindowController.setMainWindowView(mainWindowView);
        mainWindowController.setMainWindowModel(mainWindowModel);
        mainWindowController.setPlotBuilder(plotBuilder);
        mainWindowController.setxStream(new XStream(new DomDriver()));

        DialogWindowView dialogWindowView = new DialogWindowView("Progress");
        DialogWindowController dialogWindowController = new DialogWindowController();
        dialogWindowController.setPlotBuilder(plotBuilder);
        dialogWindowController.setDialogWindowView(dialogWindowView);

        mainWindowView.display();
    }
}
