package com.gsha.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Company extends RealmObject {

    @PrimaryKey
    private String nameAD;
    private String name, description, pole;
    private int image ;
    private RealmList<City> cities;
   // private RealmList<Department> departments;

    public Company() {
    }

    public Company(String nameAD, String name, int image) {
        this.nameAD = nameAD;
        this.name = name;
        this.image = image;
    }

    public Company(String nameAD, String name, String description, int image) {
        this.nameAD = nameAD;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Company(String nameAD, String name, String description, String pole, int image) {
        this.nameAD = nameAD;
        this.name = name;
        this.description = description;
        this.pole = pole;
        this.image = image;
    }

    public Company(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.nameAD = jsonObject.getString("nameAD");
            this.description = jsonObject.getString("description");
            this.pole = jsonObject.getString("pole");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getNameAD() {
        return nameAD;
    }

    public void setNameAD(String nameAD) {
        this.nameAD = nameAD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public RealmList<City> getCities() {
        return cities;
    }

    public void setCities(RealmList<City> cities) {
        this.cities = cities;
    }


}
