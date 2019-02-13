package com.example.annuairegsh.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.annuairegsh.Adapter.DepartmentAdapter;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        initView();
        ListDepartment departments = (ListDepartment) getIntent().getSerializableExtra("departments");
        DepartmentAdapter adapter = new DepartmentAdapter(departments.getDepartments(), getApplicationContext());
        listView.setAdapter(adapter);
    }

    private void initView(){
        listView = findViewById(R.id.listview);
    }
}
