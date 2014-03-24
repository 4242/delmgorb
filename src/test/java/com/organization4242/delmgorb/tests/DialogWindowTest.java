package com.organization4242.delmgorb.tests;

import com.organization4242.delmgorb.controller.AbstractController;
import com.organization4242.delmgorb.controller.DialogWindowController;
import com.organization4242.delmgorb.model.AbstractModel;
import com.organization4242.delmgorb.view.DialogWindowView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link com.organization4242.delmgorb.view.DialogWindowView}
 *
 * @author Murzinov Ilya
 */
@RunWith(JUnit4.class)
public class DialogWindowTest implements PropertyChangeListener {
    private DialogWindowView view;
    private ActionEvent actionEvent = mock(ActionEvent.class);
    private AbstractController controller = new AbstractController() {};
    private AbstractModel model = mock(AbstractModel.class);
    private PropertyChangeSupport propertyChangeSupport =
            new PropertyChangeSupport(model);

    private String propertyName;

    @Before
    public void setUp() {
        view = new DialogWindowView("title");
        view.addPropertyChangeListener(this);
        controller.addView(view);
        propertyChangeSupport.addPropertyChangeListener(controller);
    }

    @Test
    public void testCancelButtonBeforeCalculated() throws Exception {
        propertyChangeSupport.firePropertyChange(DialogWindowController.PERCENTAGE, 0, 10);
        view.getButton().getActionListeners()[0].actionPerformed(actionEvent);
        assertEquals(propertyName, DialogWindowController.CANCEL);
        assertEquals(view.getProgressBar().getValue(), 0);
    }

    @Test
    public void testCancelButtonAfterCalculated() throws Exception {
        propertyChangeSupport.firePropertyChange(DialogWindowController.CALCULATED, 0, 1);
        assertEquals(view.getButton().isEnabled(), false);
        view.close();
        assertEquals(view.getButton().isEnabled(), true);
    }

    @Test
    public void testInitial() throws Exception {
        assertEquals(view.getDialog().getTitle(), "title");
        assertEquals(view.getTextArea().getText(), "Calculating");
        assertEquals(view.getProgressBar().getValue(), 0);
        assertEquals(view.getButton().isEnabled(), true);
    }

    @Test
    public void testPercentageUpdate() throws Exception {
        propertyChangeSupport.firePropertyChange(DialogWindowController.PERCENTAGE, 0, 10);
        assertEquals(view.getProgressBar().getValue(), 10);
    }

    @Test
    public void testDrawing() throws Exception {
        propertyChangeSupport.firePropertyChange(DialogWindowController.CALCULATED, 0, 1);
        assertEquals(view.getTextArea().getText(), "Drawing");
        assertEquals(view.getProgressBar().getValue(), 0);
        assertEquals(view.getButton().isEnabled(), false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyName = evt.getPropertyName();
    }
}
