package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.System.*;

public class DataModel {
    private ArrayList<Point3D> listOfPoints;
    {
        listOfPoints = new ArrayList<Point3D>();
    }

    public ArrayList<Point3D> getListOfPoints() {
        return listOfPoints;
    }

    public DataModel(int num, double time_step, IntegrationMethods method) {
        //Here listOfPoints gets assigned
        double angle = PI/20;
        listOfPoints = buildPoints(num, 100, time_step, angle, angle, angle, method);
    }

    private ArrayList<Point3D> buildPoints(int num_of_points, double time, double time_step,
                                           double phi_0, double theta_0, double psi_0, IntegrationMethods method) {

        out.println("Inside buildPoints");
        ArrayList<Point3D> list;
        list = new ArrayList<Point3D>();
        out.println(method);
        FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        switch (method) {
            case Euler: integrator = new EulerIntegrator(0.01); break;
            case Midpoint: integrator = new MidpointIntegrator(0.05); break;
            case ClassicalRungeKutta: integrator = new ClassicalRungeKuttaIntegrator(0.1); break;
            case Gill: integrator = new GillIntegrator(0.1); break;
            case ThreeEights: integrator = new ThreeEighthesIntegrator(0.05); break;
            case HighamAndHall: integrator = new HighamHall54Integrator(0.05, 0.1, 1.0, 0.5);break;
            case DormandPrince5: integrator = new DormandPrince54Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);break;
            case DormandPrince8: integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case GraggBulirschStoer: integrator = new GraggBulirschStoerIntegrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case AdamsBashforth: integrator = new AdamsBashforthIntegrator(3, 0.01, 0.05, 1.0, 0.5); break;
            case AdamsMoulton: integrator = new AdamsMoultonIntegrator(2, 0.01, 0.05, 1.0, 0.5); break;
        }

        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/

        double S_ph0;
        S_ph0 = sin(phi_0 / 2);
        double S_ps0;
        S_ps0 = sin(psi_0 / 2);
        double S_th0;
        S_th0 = sin(theta_0 / 2);
        double C_ph0;
        C_ph0 = cos(phi_0 / 2);
        double C_ps0;
        C_ps0 = cos(psi_0 / 2);
        double C_th0;
        C_th0 = cos(theta_0 / 2);

        double lambda_0;
        lambda_0 = C_ph0*C_ps0*C_th0 + S_ph0*S_ps0*S_th0;
        double lambda_1;
        lambda_1 = S_ph0*C_ps0*C_th0 - C_ph0*S_ps0*S_th0;
        double lambda_2;
        lambda_2 = C_ph0*C_ps0*S_th0 + S_ph0*S_ps0*C_th0;
        double lambda_3;
        lambda_3 = C_ph0*S_ps0*C_th0 - S_ph0*C_ps0*S_th0;

        /*строим разбиение треугольника Белецкого (по сути плоскость параметров эпсилон-дельта) на точки*/

        int counter = 0;
        double eps = 0;
        double del = 0;
        for (int i = 1; i <= num_of_points; i++) {
            eps = 1.0 * i / (num_of_points + 1);
            for (int j = 1; j <= num_of_points; j++) {
                del = 1 + 1.0 * j / (num_of_points + 1);
                if(eps - del > -1) {
                    list.add(counter, new Point3D(del, eps, 0));
                    counter++;
                }
            }
        }

        /*в каждой точке выполняем численное интегрирование для разного времени, ищем максимум самолетного угла*/
        for (int i = 0; i <= counter - 1; i++) {
            double max;
            max = 0;
            for (int t = 1; t <= time/time_step; t++) {
                double[] y0; // initial state
                y0 = new double[] { lambda_0, lambda_1, lambda_2, lambda_3, 0, 1, 0 };
                double[] y1; // final state
                y1 = new double[] { 0, 0, 0, 0, 0, 0, 0 };
                double time_state;
                time_state = 1.0*t*time_step;
                FirstOrderDifferentialEquations ode = new LibrationODE(1000, list.get(i).y, list.get(i).x, 0.001078011072);
                integrator.integrate(ode, 0.0, y0, time_state, y1);// now y1 contains final state at time t/100
                double alpha_1;//элемент матрицы направляющих косинусов
                alpha_1 = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                double beta_1;//элемент матрицы направляющих косинусов
                beta_1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                double psi;//вычисляем самолетный угол
                psi = atan(beta_1 / alpha_1);
                /*out.println("________");
                out.print("i: ");     out.print(i);
                out.print(" t: ");    out.print(t);
                out.print(" y[0]: "); out.println(y1[0]);*/
                if (psi >= max) max = psi;
            }
            double epsilon = list.get(i).y;
            double delta  = list.get(i).x;
            list.remove(i);
            list.add(i, new Point3D(delta, epsilon, max));
            System.out.println(list.get(i));
        }
        out.println("Outside buildPoints");
        return list;
    }
}
