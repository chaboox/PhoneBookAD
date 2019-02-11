package com.example.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
    private String name, company, description, city, number, voip, department, mail, pictureC;
    private byte[]  picture;

    public Contact(String name, String firstname) {
        this.name = name;
    }




    public Contact(String name, String company, String description, String city, String number,
                   String voip, String department, String mail, byte[] picture) {
        this.name = name;
        this.company = company;
        this.description = description;
        this.city = city;
        this.number = number;
        this.voip = voip;
        this.department = department;
        this.mail = mail;
        this.picture = picture;	}

    public Contact(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.description = jsonObject.getString("description");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCompany() {
        return company;
    }




    public void setCompany(String company) {
        this.company = company;
    }




    public String getDescription() {
        return description;
    }




    public void setDescription(String description) {
        this.description = description;
    }




    public String getCity() {
        return city;
    }




    public void setCity(String city) {
        this.city = city;
    }




    public String getNumber() {
        return number;
    }




    public void setNumber(String number) {
        this.number = number;
    }




    public String getVoip() {
        return voip;
    }




    public void setVoip(String voip) {
        this.voip = voip;
    }




    public String getDepartment() {
        return department;
    }




    public void setDepartment(String department) {
        this.department = department;
    }




    public String getMail() {
        return mail;
    }




    public void setMail(String mail) {
        this.mail = mail;
    }




    public byte[] getPicture() {
        return picture;
    }




    public void setPicture(byte[] picture) {
        this.picture = picture;
    }




    public String getPictureC() {
        return pictureC;
    }




    public void setPictureC(String pictureC) {
        this.pictureC = pictureC;
    }




    @Override
    public String toString() {
        return "Contact [name=" + name + ", company=" + company + ", description=" + description + ", city=" + city
                + ", number=" + number + ", voip=" + voip + ", department=" + department + ", mail=" + mail + "]";
    }
}
