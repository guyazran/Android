package com.example.guyazran.carmakersandmodels;

/**
 * Created by guyazran on 8/10/15.
 */
public class CarMaker {


        private String name;
        private String[] models;

        public CarMaker(String name, String[] models) {
            this.name = name;
            this.models = models;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getModels() {
            return models;
        }

        public void setModels(String[] models) {
            this.models = models;
        }

    @Override
    public String toString() {
        return name;
    }
}
