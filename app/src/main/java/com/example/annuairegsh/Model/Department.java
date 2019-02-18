package com.example.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Department extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;
    private String code;
    private String description;

    public Department(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Department() {
    }

    public Department(JSONObject jsonObject) {
        try {
            this.code = jsonObject.getString("name");
            this.id = jsonObject.getString("id");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
