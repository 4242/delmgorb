package com.organization4242.delmgorb.tests;

import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.view.MainWindowView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ilya-murzinov on 20.03.14.
 */
@RunWith(JUnit4.class)
public class MainWindowViewTest implements PropertyChangeListener{
    private MainWindowView view;

    private ActionEvent actionEvent = mock(ActionEvent.class);
    private FocusEvent focusEvent = mock(FocusEvent.class);

    private String propertyName;
    private String oldValue;
    private String newValue;

    @Before
    public void setUp() throws Exception {
        view = new MainWindowView();
        view.addPropertyChangeListener(this);
    }

    @Test
    public void textFieldIntegerTest() {
        when(focusEvent.getSource()).thenReturn(view.getNumberOfPointsTextField());
        when(focusEvent.getComponent()).thenReturn(view.getNumberOfPointsTextField());
        view.getNumberOfPointsTextField().setText("10");
        view.getNumberOfPointsTextField().getFocusListeners()[2].focusGained(focusEvent);
        view.getNumberOfPointsTextField().setText("11");
        view.getNumberOfPointsTextField().getFocusListeners()[2].focusLost(focusEvent);
        assertEquals(propertyName, MainWindowController.NUMBER_OF_POINTS);
        assertEquals(oldValue, "10");
        assertEquals(newValue, "11");
    }

    @Test
    public void textFieldDoubleTest() {
        when(focusEvent.getSource()).thenReturn(view.getTimeStepTextField());
        when(focusEvent.getComponent()).thenReturn(view.getTimeStepTextField());
        view.getTimeStepTextField().setText("0.1");
        view.getTimeStepTextField().getFocusListeners()[2].focusGained(focusEvent);
        view.getTimeStepTextField().setText("0.5");
        view.getTimeStepTextField().getFocusListeners()[2].focusLost(focusEvent);
        assertEquals(propertyName, MainWindowController.TIME_STEP);
        assertEquals(oldValue, "0.1");
        assertEquals(newValue, "0.5");
    }

    @Test
    public void drawButtonTest() {
        view.getDrawButton().getActionListeners()[0].actionPerformed(actionEvent);
        assertEquals(propertyName, MainWindowController.DRAW_BUTTON_CLICK);
    }

    @Test
    public void validationPositiveTest() {
        view.getNumberOfPointsTextField().setText("1");
        view.getNumberOfPointsTextField().getFocusListeners()[0].focusLost(focusEvent);
        assertTrue(view.getDrawButton().isEnabled());
    }

    @Test
    public void validationNegativeWithDoubleTest() {
        view.getNumberOfPointsTextField().setText("11.6");
        view.getNumberOfPointsTextField().getInputVerifier().verify(view.getNumberOfPointsTextField());
        assertFalse(view.getDrawButton().isEnabled());
    }

    @Test
    public void validationNegativeWithStringTest() {
        view.getNumberOfPointsTextField().setText("aaa");
        view.getNumberOfPointsTextField().getInputVerifier().verify(view.getNumberOfPointsTextField());
        assertFalse(view.getDrawButton().isEnabled());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyName = evt.getPropertyName();
        oldValue = evt.getOldValue() instanceof String ? (String) evt.getOldValue() : "";
        newValue = evt.getNewValue() instanceof String ? (String) evt.getNewValue() : "";
    }
}