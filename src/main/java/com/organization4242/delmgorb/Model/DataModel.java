package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

import java.util.ArrayList;

public class DataModel {
    private ArrayList<Point3D> listOfPoints = new ArrayList<Point3D>();

    public ArrayList<Point3D> getListOfPoints() {
        return listOfPoints;
    }

    public DataModel(int num) {
        //Here listOfPoints gets assigned
        listOfPoints = buildPoints(num, 0.1, 0.1, 0.1, 100);
    }

    private ArrayList<Point3D> buildPoints(int num_of_points, double phi_0, double theta_0, double psi_0, double time) {

        System.out.println("Inside buildPoints");
        ArrayList<Point3D> list = new ArrayList<Point3D>();
        FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);

        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/

        double S_ph0 = Math.sin(phi_0/2);
        double S_ps0 = Math.sin(psi_0/2);
        double S_th0 = Math.sin(theta_0/2);
        double C_ph0 = Math.cos(phi_0/2);
        double C_ps0 = Math.cos(psi_0/2);
        double C_th0 = Math.cos(theta_0/2);
   
        double lambda_0 = C_ph0*C_ps0*C_th0 + S_ph0*S_ps0*S_th0;
        double lambda_1 = S_ph0*C_ps0*C_th0 - C_ph0*S_ps0*S_th0;
        double lambda_2 = C_ph0*C_ps0*S_th0 + S_ph0*S_ps0*C_th0;
        double lambda_3 = C_ph0*S_ps0*C_th0 - S_ph0*C_ps0*S_th0;

        /*строим разбиение треугольника Белецкого (по сути плоскость параметров эпсилон-дельта) на точки*/

        int counter = 0;
        double eps = 0;
        double del = 0;
        for (int i = 1; i <= num_of_points; i++) {
            eps = 1.0*i / (num_of_points + 1);
            for (int j = 1; j <= num_of_points; j++) {
                del = 1 + 1.0*j/(num_of_points + 1);
                if(eps > del - 1) {
                    list.add(counter, new Point3D(eps, del, 0));
                    counter++;
                }
            }
        }
        /*в каждой точке выполняем численное интегрирование для разного времени, ищем максимум самолетного угла*/
        for (int i = 0; i <= counter - 1; i++) {
            double max = 0;
            for (int t = 1; t <= time; t++) {
                double[] y0 = new double[] { lambda_0, lambda_1, lambda_2, lambda_3, 0, 1, 0 }; // initial state
                double[] y1 = new double[] { 0, 0, 0, 0, 0, 0, 0 }; // final state
                FirstOrderDifferentialEquations ode = new CircleODE(1000, list.get(i).x, list.get(i).y, 0.001078011072);
                dp853.integrate(ode, 0.0, y0, 1.0*t, y1);// now y1 contains final state at time t/100
                double alpha_1 = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];//элемент матрицы направляющих косинусов
                double beta_1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);//элемент матрицы направляющих косинусов
                double psi = Math.atan(beta_1/alpha_1);//вычисляем самолетный угол
                /*System.out.println("________");
                System.out.println(t);
                System.out.print(y1[0]);System.out.print(" ");System.out.print(y1[1]);System.out.print(" ");
                System.out.print(y1[2]);System.out.print(" ");System.out.print(y1[3]);System.out.print(" ");
                System.out.print(alpha_1);System.out.print(" ");System.out.print(beta_1);System.out.print(" ");
                System.out.print(Math.atan(beta_1/alpha_1));System.out.print(" ");System.out.print(max);
                System.out.println(" ");*/
                if (psi >= max) {max = psi;/*System.out.println("NEW MAX");*/}
            }
            double epsilon = list.get(i).x;
            double delta  = list.get(i).y;
            list.remove(i);
            list.add(i, new Point3D(epsilon, delta, max));
            //System.out.println(list.get(i));
        }
        System.out.println("Outside buildPoints");
        return list;
    }
}
