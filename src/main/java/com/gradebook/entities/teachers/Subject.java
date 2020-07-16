package com.gradebook.entities.teachers;

public enum Subject {
    Maths("Maths"),
    English("English"),
    History("History"),
    Chemistry("Chemistry"),
    Physics("Physics"),
    Biology("Biology"),
    PE("Physical Education"),
    CS("Computer Science");

    private final String displayValue;

    private Subject(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
