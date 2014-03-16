package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.PlotBuilder;
import com.organization4242.delmgorb.view.DialogWindowView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Евгений on 15.03.14.
 */
public class DialogWindowController extends AbstractController {
    public static final String PERCENTAGE = "Percentage";
    public static final String INIT = "Init";
    public static final String DISPOSE = "Dispose";
    public static final String CALCULATED = "Calculated";

    private DialogWindowView dialogWindowView;
    private PlotBuilder plotBuilder;

    public void setDialogWindowView(DialogWindowView dialogWindowView) {
        this.dialogWindowView = dialogWindowView;
        this.dialogWindowView.getButton().addActionListener(actionListener);
        addView(dialogWindowView);
    }

    public void setPlotBuilder(PlotBuilder plotBuilder) {
        this.plotBuilder = plotBuilder;
        addModel(plotBuilder);
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            plotBuilder.getDataModel().stop();
            dialogWindowView.close();
            dialogWindowView.getProgressBar().setValue(0);
        }
    };

}
