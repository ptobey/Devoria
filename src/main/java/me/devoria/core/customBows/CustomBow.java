package me.devoria.core.customBows;

public class CustomBow {

    public CustomBow(String name, int wage, String position) {
        this.name = name;
        this.wage = wage;
        this.position = position;

    }

    // Without a default constructor, Jackson will throw an exception
    public CustomBow() {}

    private String name;
    private int wage;
    private String position;

    // Getters and setters

    @Override
    public String toString() {
        return "\nName: " + name + "\nWage: " + wage + "\nPosition: " + position + "\nColleagues: " + "\n";
    }



}

