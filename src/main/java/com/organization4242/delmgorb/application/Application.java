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

        mainWindowView.display();

//        mainWindowModel.setNumberOfPoints(10);
//        mainWindowModel.setTimeStep(0.5);
//        mainWindowModel.setTimePeriod(100d);
//        mainWindowModel.setXMin(1f);
//        mainWindowModel.setXMax(2f);
//        mainWindowModel.setYMin(0.05f);
//        mainWindowModel.setYMax(1f);
//        mainWindowModel.setAngle(Angle.PSI);
//        mainWindowModel.setIntegrationMethod(IntegrationMethods.DORMAND_PRINCE_8);
//        mainWindowModel.setPhi(0.05);
//        mainWindowModel.setPsi(0.05);
//        mainWindowModel.setTheta(0.05);
//        mainWindowModel.setNumberOfSpheres(50);
    }
}
