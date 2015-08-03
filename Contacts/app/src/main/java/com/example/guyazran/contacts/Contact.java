package com.example.guyazran.contacts;

/**
 * Created by guyazran on 8/2/15.
 */
public class Contact {
    String firstName, lastName;
    int image;

    public Contact(String firstName, String lastName, int image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    //added for teacher solution
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "first name: " + firstName + ", last name: " + lastName;
    }
}
