package com.gsha.annuairegsh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsha.annuairegsh.Adapter.CityAdapter;
import com.gsha.annuairegsh.Manager.RealmManager;
import com.gsha.annuairegsh.Model.Company;
import com.gsha.annuairegsh.R;

public class CityActivity extends BaseSwipeBackActivity {
    public static ListView listView;
    private Company company;
    public static String companyName;
    private String city;
    //public static   ListCity cities;
    public static Handler handler;
    private ImageView back;
    private TextView companyT;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_city);
        initView();
        //cities = (ListCity) getIntent().getSerializableExtra("cities");
        companyName = getIntent().getStringExtra("company");
        company = RealmManager.getCompanyByCode(companyName);

        companyT.setText(company.getName());

        if(company.getCities().size()==1) {
            Intent intent = new Intent(CityActivity.this, DepartmentActivity.class);
            intent.putExtra("id", company.getCities().get(0).getId());
            intent.putExtra("company", CityActivity.companyName);
            startActivity(intent);
            finish();
        }
        else if(company.getCities().size() == 0){
            Toast.makeText(getApplicationContext(), "Filiale vide", Toast.LENGTH_SHORT).show();
            finish();
        }else {

            Log.d("CITYY", "onCreate: " + company.getCities().size() + company.getCities());
            CityAdapter adapter = new CityAdapter(company.getCities(), getApplicationContext(), this);
            listView.setAdapter(adapter);
        }
        //ItemHeighManger.setListViewHeightBasedOnChildren(listView);
        //listView.setFillViewport(true);
    }

    private void initView(){
        companyT = findViewById(R.id.company_title);
        listView = findViewById(R.id.listview);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_out_right, R.anim.fade_right_finish);
               //overridePendingTransition(0, 0);
            }
        });
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
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: BACKK");
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out_right, R.anim.fade_right_finish);
    }
    }