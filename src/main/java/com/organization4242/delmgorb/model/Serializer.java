package com.organization4242.delmgorb.model;

import java.io.Serializable;

/**
 * Util class used to serialize both {@link com.organization4242.delmgorb.model.MainWindowModel}
 * and {@link com.organization4242.delmgorb.model.DataModel}.
 *
 * @author Murzinov Ilya
 */
public class Serializer implements Serializable {
    public Serializer(MainWindowModel mainWindowModel, DataModel dataModel) {
        this.mainWindowModel = mainWindowModel;
        this.dataModel = dataModel;
    }
    public MainWindowModel mainWindowModel;
    public DataModel dataModel;
}
