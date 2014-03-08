package com.organization4242.delmgorb.model;

import java.io.Serializable;

/**
 * Util class used to serialize both {@link com.organization4242.delmgorb.model.MainWindowModel}
 * and {@link com.organization4242.delmgorb.model.DataModel}.
 *
 * @author Murzinov Ilya
 */
public class Serializer implements Serializable {
    private MainWindowModel mainWindowModel;
    private DataModel dataModel;

    public MainWindowModel getMainWindowModel() {
        return mainWindowModel;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public Serializer(MainWindowModel mainWindowModel, DataModel dataModel) {
        this.mainWindowModel = mainWindowModel;
        this.dataModel = dataModel;
    }
}
