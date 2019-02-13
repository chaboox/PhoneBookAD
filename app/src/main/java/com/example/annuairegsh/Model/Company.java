package com.example.annuairegsh.Model;

import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

public class Company {
    private String nameAD, name, description, pole;
    private int image ;

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
}