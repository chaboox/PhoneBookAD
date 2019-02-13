package com.example.annuairegsh.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListDepartment implements Serializable {
    private ArrayList<Department> departments;

    public ListDepartment(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
}
