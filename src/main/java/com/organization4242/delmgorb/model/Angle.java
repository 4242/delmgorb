package com.organization4242.delmgorb.model;

/**
 * Created by ilya-murzinov on 24.02.14.
 */
public enum Angle {
    PSI("Psi"),
    PHI("Phi"),
    THETA("Theta");

    private String name;

    private Angle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
