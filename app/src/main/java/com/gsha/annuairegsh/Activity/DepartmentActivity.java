package com.gsha.annuairegsh.Activity;

import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsha.annuairegsh.Adapter.DepartmentAdapter;
import com.gsha.annuairegsh.Manager.RealmManager;
import com.gsha.annuairegsh.Model.City;
import com.gsha.annuairegsh.Model.Company;
import com.gsha.annuairegsh.R;

public class DepartmentActivity extends BaseSwipeBackActivity {
    private ListView listView;
    public static String company;
    public static Company companyR;
    public static City city;
    private String idCity;
    public static Handler handler;
    private ImageView back;
    private TextView companyT;
    private RelativeLayout relativeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_department;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_department);
        initView();
        company = getIntent().getStringExtra("company");
        idCity = getIntent().getStringExtra("id");
        city = RealmManager.getCityById(idCity);
        //city = getIntent().getStringExtra("city");
        // ListDepartment departments = (ListDepartment) getIntent().getSerializableExtra("departments");
        DepartmentAdapter adapter = new DepartmentAdapter(city.getDepartments(), getApplicationContext(), this);
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
                overridePendingTransition(R.anim.fade_out_right, R.anim.fade_right_finish);
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

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: BACKK");
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out_right, R.anim.fade_right_finish);
    }
}
