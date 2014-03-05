package com.organization4242.delmgorb.application;

import com.organization4242.delmgorb.controller.MainWindowController;
import com.organization4242.delmgorb.model.MainWindowModel;
import com.organization4242.delmgorb.view.MainWindowView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ilya-murzinov on 19.02.14.
 */
public final class Application {
    private Application() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        MainWindowModel mainWindowModel = (MainWindowModel) context.getBean("mainWindowModel");
        MainWindowView view = (MainWindowView) context.getBean("mainWindowView");
        MainWindowController controller = (MainWindowController) context.getBean("mainWindowController");
        view.display();
    }
}
