package com.organization4242.delmgorb.model;

import java.io.Serializable;

/**
 * Created by ilya-murzinov on 05.03.14.
 */
public class Serializer implements Serializable {
    public Serializer(MainWindowModel mainWindowModel, DataModel dataModel) {
        this.mainWindowModel = mainWindowModel;
        this.dataModel = dataModel;
    }
    public MainWindowModel mainWindowModel;
    public DataModel dataModel;
}
