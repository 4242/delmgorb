package com.organization4242.delmgorb.model;

public enum Angle {
    PSI("Max(Abs(Psi))"),
    THETA("Max(Abs(Theta))"),
    PHI("Max(Abs(Phi))"),
    MIN_OF_PSI_AND_ONE_THIRD_OF_PI("Min(Max(Abs(Psi)), Pi/3)"),
    MIN_OF_THETA_AND_ONE_THIRD_OF_PI("Min(Max(Abs(Theta)), Pi/3)"),
    MIN_OF_PHI_AND_ONE_THIRD_OF_PI("Min(Max(Abs(Phi)), Pi/3)"),
    MIN_OF_PSI_AND_ONE_FOURTH_OF_PI("Min(Max(Abs(Psi)), Pi/4)"),
    MIN_OF_THETA_AND_ONE_FOURTH_OF_PI("Min(Max(Abs(Theta)), Pi/4)"),
    MIN_OF_PHI_AND_ONE_FOURTH_OF_PI("Min(Max(Abs(Phi)), Pi/4)");

    private String name;

    private Angle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Angle fromString(String name) {
        if (name != null) {
            for (Angle b : Angle.values()) {
                if (name.equalsIgnoreCase(b.name)) {
                    return b;
                }
            }
        }
        return null;
    }
}
