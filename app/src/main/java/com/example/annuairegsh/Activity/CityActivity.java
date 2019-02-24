package com.example.annuairegsh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.annuairegsh.Adapter.CityAdapter;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.ListCity;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity {
    public static ListView listView;
    private Company company;
    public static String companyName;
    private String city;
    //public static   ListCity cities;
    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
        //cities = (ListCity) getIntent().getSerializableExtra("cities");
        companyName = getIntent().getStringExtra("company");
        company = RealmManager.getCompanyByCode(companyName);

        if(company.getCities().size()==1) {
            Intent intent = new Intent(CityActivity.this, DepartmentActivity.class);
            intent.putExtra("id", company.getCities().get(0).getId());
            intent.putExtra("company", CityActivity.companyName);
            startActivity(intent);
            finish();
        }
        else if(company.getCities().size() == 0){
            Toast.makeText(getApplicationContext(), "Empty !", Toast.LENGTH_SHORT).show();
            finish();
        }else {

            Log.d("CITYY", "onCreate: " + company.getCities().size() + company.getCities());
            CityAdapter adapter = new CityAdapter(company.getCities(), getApplicationContext());
            listView.setAdapter(adapter);
        }
        //ItemHeighManger.setListViewHeightBasedOnChildren(listView);
        //listView.setFillViewport(true);
    }

    private void initView(){
        listView = findViewById(R.id.listview);
        //handler = new HandlerCity();
    }

    /*public class HandlerCity extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.GET_CITY:
                     city = (String) msg.obj;
                    API_Manager.getDepartement(company,city, getApplicationContext(), handler);
                    break;
                case Constant.DEPARTMENT:
                    ArrayList<Department> departments = (ArrayList<Department>)msg.obj;

                    Intent intent = new Intent(CityActivity.this, DepartmentActivity.class);
                    ListDepartment listDepartment = new ListDepartment(departments);
                    intent.putExtra("departments", listDepartment);
                    intent.putExtra("company", company);
                    intent.putExtra("city", city);
                    startActivity(intent);
                    break;
            }
        }}*/
    }