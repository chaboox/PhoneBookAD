package com.gsha.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class
Contact extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private String name;
            private String  company, description, city, number, voip, department, mail, pictureC;
    private byte[]  picture;
    private boolean boss;
    private boolean test;

    public Contact(String name) {
        this.name = name;
        this.id = name;
        this.pictureC = "null";
        this.company = "null";
        this.description = "null";
        this.city = "null";
        this.number = "null";
        this.voip = "null";
        this.department = "null";
        this.mail = "null";


    }


    public Contact() {
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
        this.picture = picture;


    }

    public Contact(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.name = (id).split(",")[0].substring(3);

            this.name = name.substring(0,1).toUpperCase() + name.substring(1);
            this.description = jsonObject.getString("description");
            this.company = (id).split(",")[3].substring(3);
            this.city =(id).split(",")[2].substring(3);
            if(jsonObject.getString("number").length() == 10 ) {
                this.number = jsonObject.getString("number");
            }
            else  this.number = "null";
            this.voip = jsonObject.getString("voip");
            this.department = jsonObject.getString("department");
            this.mail = jsonObject.getString("mail");
            this.boss = jsonObject.getBoolean("boss");
            //this.pictureC = "null";
           //this.pictureC = jsonObject.getString("pictureC");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    //CN=Imene AZZOUN,OU=Users,OU=SBA,OU=HTAS,DC=groupe-hasnaoui,DC=local



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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact [name=" + name + ", company=" + company + ", description=" + description + ", city=" + city
                + ", number=" + number + ", voip=" + voip + ", department=" + department + ", mail=" + mail + "]";
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }
}
