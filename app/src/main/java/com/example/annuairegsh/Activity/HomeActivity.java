package com.example.annuairegsh.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Adapter.GridViewAdapter;
import com.example.annuairegsh.Manager.CSVManager;
import com.example.annuairegsh.Manager.UrlGenerator;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.KeyValuePair;
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
    }
    private void populateCompany(){
        companies = new ArrayList<>();
        contacts = new ArrayList<>();

        companies.add(new Company("GSHA", "GSH", R.drawable.gsh));
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
        GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
        myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));
        myrv.setAdapter(myAdapter);


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
                    GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
                    myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));
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
}
