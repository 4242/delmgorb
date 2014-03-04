package com.organization4242.delmgorb.model;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public enum IntegrationMethods {
    EULER("Euler"),
    MIDPOINT("Midpoint"),
    CLASSICAL_RUNGE_KUTTA("Classical Runge-Kutta"),
    GILL("Gill"),
    THREE_EIGHTS("Three eights"),
    HIGHAM_AND_HALL("Higham and Hall"),
    DORMAND_PRINCE_5("Dormand-Prince 5"),
    DORMAND_PRINCE_8("Dormand-Prince 5"),
    GRAGG_BULIRSCH_STOER("Grass-Bulirch-Stoer"),
    ADAMS_BASHFORTH("Adams-Bashforth"),
    ADAMS_MOULTON("Adams-Moulton");

    private final String name;

    private IntegrationMethods(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
