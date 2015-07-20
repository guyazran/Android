package com.example.guyazran.linkingactivitiesrecap;

import java.io.Serializable;

/**
 * Created by guyazran on 7/20/15.
 */
public class Dog implements Serializable {
    private String _name;
    private int _age;

    public Dog(String _name, int _age) {
        this._name = _name;
        this._age = _age;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int _age) {
        this._age = _age;
    }
}
