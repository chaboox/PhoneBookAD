package com.example.annuairegsh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.annuairegsh.Adapter.DepartmentAdapter;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private ListView listView;
    public static String company;
    public static Company companyR;
    public static City city;
    private String idCity;
    public static Handler handler;
    private ImageView back;
    private TextView companyT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        initView();
        company = getIntent().getStringExtra("company");
        idCity = getIntent().getStringExtra("id");
        city = RealmManager.getCityById(idCity);
        //city = getIntent().getStringExtra("city");
        // ListDepartment departments = (ListDepartment) getIntent().getSerializableExtra("departments");
        DepartmentAdapter adapter = new DepartmentAdapter(city.getDepartments(), getApplicationContext());
        companyR = RealmManager.getCompanyByCode(company);

        companyT.setText(companyR.getName()+ ", " + city.getCode());

        listView.setAdapter(adapter);
        //  listView.setFastScrollEnabled(true);
    }

    private void initView(){
        companyT = findViewById(R.id.company_title);
        listView = findViewById(R.id.listview);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       // handler = new HandlerDepartment();
    }

   /* public class HandlerDepartment extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.CONTACT_FETCH:
                    String department= (String) msg.obj;
                    API_Manager.getContactsByDepartment(company, city, department, getApplicationContext(), handler);
                    break;
                case Constant.CONTACT:
                    ArrayList<Contact> contacts= (ArrayList<Contact>)msg.obj;

                    Intent intent = new Intent(DepartmentActivity.this, ListContactActivity.class);
                    ListContact listContact= new ListContact(contacts);
                    intent.putExtra("contacts", listContact);
                    startActivity(intent);
                    break;
            }
        }}*/
}
