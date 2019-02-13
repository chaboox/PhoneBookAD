package com.example.annuairegsh.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Adapter.GridViewAdapter;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.CSVManager;
import com.example.annuairegsh.Manager.UrlGenerator;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.KeyValuePair;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Company> companies;
    ArrayList<Contact> contacts;
    private String TAG = "HOME";
    private RecyclerView myrv;
    public static Handler handler;
    private String company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        initView();
        populateCompany();
        populateFromAd();
        populateContact();
    }

    private void initView(){
        myrv = findViewById(R.id.recyclerview_id);
        handler = new HandlerHome();
      //  myrv.setNestedScrollingEnabled(false);
    }
    private void populateCompany(){
        companies = new ArrayList<>();
        contacts = new ArrayList<>();

      //  companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
       /* companies.add(new Company("ALUX", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));*/
      //  GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
       // myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));
      //  myrv.setAdapter(myAdapter);


    }

    public void populateFromAd(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
       /* List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("name","Adel"));
        params.add(new KeyValuePair("number","3"));*/
        String url = Constant.API_URL + "/company";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                byte[] b4 = null;
                try {
                    for(int i = 0; i < response.length(); i++){
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    companies.add(new Company(jsonObject));
                    //String name = jsonObject.getString("name");
                    }
                    CSVManager.CreateRootFolder();
                    CSVManager.saveInCSV(companies);
                   // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                    GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
                    myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));
                   // myAdapter.setHasStableIds(true);
                    myrv.setAdapter(myAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void populateContact(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        /*List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("name","a"));
        params.add(new KeyValuePair("number","1000"));*/
        String url = Constant.API_URL +  "/allContacts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());
                byte[] b4 = null;
                try {
                    for(int i = 0; i < response.length(); i++){
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    String name = jsonObject.getString("name");
                    contacts.add(new Contact(jsonObject));
                    }
                    CSVManager.saveContactsInCSV(contacts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public class HandlerHome extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.GET_CITY:
                    company = (String)msg.obj;
                    API_Manager.getCity((String)msg.obj, HomeActivity.this, handler);
                    break;
                case Constant.CITY:
                    ArrayList<City> cities = (ArrayList<City>)msg.obj;
                    if(cities.size() > 1){
                        Intent intent = new Intent(HomeActivity.this, CityActivity.class);
                        intent.putExtra("cities", cities);
                    }
                    else if(cities.size() == 1){
                      API_Manager.getDepartement(company, cities.get(0).getCode(), getApplicationContext(), handler);

                    }
                    else {
                        Toast.makeText(HomeActivity.this, "Erreur!", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("CITY", "handleMessage: " + cities.get(0).getName());
                    break;
                case Constant.DEPARTMENT:
                    ArrayList<Department> departments = (ArrayList<Department>)msg.obj;

                    Intent intent = new Intent(HomeActivity.this, DepartmentActivity.class);
                    ListDepartment listDepartment = new ListDepartment(departments);
                    intent.putExtra("departments", listDepartment);
                    startActivity(intent);
                    break;
            }
        }
    }
}
