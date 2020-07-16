package com.gradebook.entities.classes;

public enum ClassName {
    IA("I A"),
    IB("I B"),
    IC("I C"),
    IIA("II A"),
    IIB("II B"),
    IIC("II C"),
    IIIA("III A"),
    IIIB("III B"),
    IIIC("III C"),
    IVA("IV A"),
    IVB("IV B"),
    IVC("IV C"),
    VA("V A"),
    VB("V A"),
    VC("V C");

    private final String displayValue;

    private ClassName(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
