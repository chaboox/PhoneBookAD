package com.example.annuairegsh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.annuairegsh.Adapter.DepartmentAdapter;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private ListView listView;
    private String company, city;
    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        initView();
        company = getIntent().getStringExtra("company");
        city = getIntent().getStringExtra("city");
        ListDepartment departments = (ListDepartment) getIntent().getSerializableExtra("departments");
        DepartmentAdapter adapter = new DepartmentAdapter(departments.getDepartments(), getApplicationContext());

        listView.setAdapter(adapter);
      //  listView.setFastScrollEnabled(true);
    }

    private void initView(){
        listView = findViewById(R.id.listview);
        handler = new HandlerDepartment();
    }

    public class HandlerDepartment extends Handler {
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
        }}
}
