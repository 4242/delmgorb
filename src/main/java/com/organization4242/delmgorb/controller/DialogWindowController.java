package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.PlotBuilder;
import com.organization4242.delmgorb.view.DialogWindowView;

/**
 * Created by Евгений on 15.03.14.
 */
public class DialogWindowController extends AbstractController {
    public static final String PERCENTAGE = "Percentage";
    public static final String INIT = "Init";
    public static final String DISPOSE = "Dispose";
    public static final String CALCULATED = "Calculated";
    public static final String CANCEL = "Cancel";

    private DialogWindowView dialogWindowView;
    private PlotBuilder plotBuilder;

    public void setDialogWindowView(DialogWindowView dialogWindowView) {
        this.dialogWindowView = dialogWindowView;
        addView(dialogWindowView);
    }

    public void setPlotBuilder(PlotBuilder plotBuilder) {
        this.plotBuilder = plotBuilder;
        addModel(plotBuilder);
    }
}
