package com.organization4242.delmgorb.model;

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
