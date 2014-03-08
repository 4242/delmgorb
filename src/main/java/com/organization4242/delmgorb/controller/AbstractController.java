package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.AbstractModel;
import com.organization4242.delmgorb.view.AbstractView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by ilya-murzinov on 07.03.14.
 */
public abstract class AbstractController implements PropertyChangeListener {
    private ArrayList<AbstractView> registeredViews;
    private ArrayList<AbstractModel> registeredModels;

    public AbstractController() {
        registeredViews = new ArrayList<AbstractView>();
        registeredModels = new ArrayList<AbstractModel>();
    }


    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }

    public void addView(AbstractView view) {
        registeredViews.add(view);
    }

    public void removeView(AbstractView view) {
        registeredViews.remove(view);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for (AbstractView view: registeredViews) {
            view.modelPropertyChange(evt);
        }
    }

    protected void setModelProperty(String propertyName, Object newValue) {
        for (AbstractModel model: registeredModels) {
            try {
                Method method = model.getClass().
                        getMethod("set"+propertyName, new Class[] {newValue.getClass()});
                method.invoke(model, newValue);
            } catch (Exception ex) {
                //  Handle exception.
            }
        }
    }


}
