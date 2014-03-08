package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.model.MainWindowModel;
import com.organization4242.delmgorb.view.MainWindowView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main application class.
 *
 * @author Murzinov Ilya
 */
public final class Application {
    private Application() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        MainWindowView mainWindowView = (MainWindowView) context.getBean("mainWindowView");
        MainWindowController controller = (MainWindowController) context.getBean("mainWindowController");
        MainWindowModel mainWindowModel = (MainWindowModel) context.getBean("mainWindowModel");
//
//        MainWindowView mainWindowView = new MainWindowView();
//        MainWindowModel mainWindowModel= new MainWindowModel();
//        mainWindowModel.setNumberOfPoints(10);
//        mainWindowModel.setTimeStep(0.5);
//        mainWindowModel.setTimePeriod(100d);
//        mainWindowModel.setXMin(0.05f);
//        mainWindowModel.setXMax(1f);
//        mainWindowModel.setYMin(1f);
//        mainWindowModel.setYMax(2f);
//        mainWindowModel.setAngle(Angle.PSI);
//        mainWindowModel.setIntegrationMethod(IntegrationMethods.CLASSICAL_RUNGE_KUTTA);
//        mainWindowModel.setPhi0(0.05);
//        mainWindowModel.setPsi0(0.05);
//        mainWindowModel.setTheta0(0.05);
//
//        MainWindowController controller = new MainWindowController();
//
//        controller.setMainWindowView(mainWindowView);
//        controller.setMainWindowModel(mainWindowModel);

        mainWindowView.display();
//        mainWindowModel.setNumberOfPoints(10);
//        mainWindowModel.setTimeStep(0.5);
//        mainWindowModel.setTimePeriod(100d);
//        mainWindowModel.setXMin(0.05f);
//        mainWindowModel.setXMax(1f);
//        mainWindowModel.setYMin(1f);
//        mainWindowModel.setYMax(2f);
//        mainWindowModel.setAngle(Angle.PSI);
//        mainWindowModel.setIntegrationMethod(IntegrationMethods.CLASSICAL_RUNGE_KUTTA);
//        mainWindowModel.setPhi0(0.05);
//        mainWindowModel.setPsi0(0.05);
//        mainWindowModel.setTheta0(0.05);
    }
}
