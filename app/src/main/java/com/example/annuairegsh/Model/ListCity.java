package com.example.annuairegsh.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListCity implements Serializable {

    private ArrayList<City> cities;

    public ListCity(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
}
