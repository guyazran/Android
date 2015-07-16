package com.example.guyazran.linkingactivities;

import java.io.Serializable;

/**
 * Created by guyazran on 7/16/15.
 */
public class Dog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String _name;
    private String _breed;

    public Dog(String _name, String _breed) {
        this._name = _name;
        this._breed = _breed;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getBreed() {
        return _breed;
    }

    public void setBreed(String _breed) {
        this._breed = _breed;
    }
}
