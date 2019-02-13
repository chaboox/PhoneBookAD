package com.example.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Department implements Serializable {

    private String code, description;

    public Department(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Department(JSONObject jsonObject) {
        try {
            this.code = jsonObject.getString("name");
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
