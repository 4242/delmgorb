package com.organization4242.delmgorb.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by ilya-murzinov on 07.03.14.
 */
public abstract class AbstractModel
{
    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
