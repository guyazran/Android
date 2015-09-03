package com.example.guyazran.readingandwritingobjectsexercise;

/**
 * Created by guyazran on 9/3/15.
 */
public class Dog {
    private String name;
    private int birthYear;

    public Dog(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
