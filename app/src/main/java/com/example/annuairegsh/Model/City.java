package com.example.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private String  code;
    private String name;

    private RealmList<Department> departments;

    public City() {
    }

    public City(String name, String code, String id) {
        this.name = name;
        this.code = code;
        this.id = id;
    }

    public City(JSONObject jsonObject) {
            try {
                this.name = jsonObject.getString("name");
                this.code = jsonObject.getString("code");
                this.id = jsonObject.getString("id");

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(RealmList<Department> departments) {
        this.departments = departments;
    }
}
