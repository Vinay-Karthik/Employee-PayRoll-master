package model;

public enum Department {
    ENGINEERING("Engineering"),
    IT_CONSULTING("IT Consulting"),
    OPERATIONS("Operations"),
    HR("HR"),
    FINANCE("Finance"),
    MARKETING("Marketing"),
    SALES("Sales");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}