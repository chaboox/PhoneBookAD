package com.example.annuairegsh.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private String name, code;

    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public City(JSONObject jsonObject) {
            try {
                this.name = jsonObject.getString("name");
                this.code = jsonObject.getString("code");

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
}
