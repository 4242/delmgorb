package com.organization4242.delmgorb.controller;

import com.organization4242.delmgorb.model.AbstractModel;
import com.organization4242.delmgorb.view.AbstractView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilya-murzinov on 07.03.14.
 */
public abstract class AbstractController implements PropertyChangeListener {
    private List<AbstractView> registeredViews;
    private List<AbstractModel> registeredModels;

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
        view.addPropertyChangeListener(this);
    }

    public void removeView(AbstractView view) {
        registeredViews.remove(view);
        view.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getSource().getClass().getSuperclass().equals(AbstractModel.class)) {
            for (AbstractView view: registeredViews) {
                view.modelPropertyChange(pce);
            }
        } else if (pce.getSource().getClass().getSuperclass().equals(AbstractView.class)) {
            for (AbstractModel model : registeredModels) {
                model.viewPropertyChange(pce);
            }
        }
    }
}
