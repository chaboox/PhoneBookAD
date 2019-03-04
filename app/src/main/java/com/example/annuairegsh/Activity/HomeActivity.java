package com.example.annuairegsh.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Adapter.ContactAdapter;
import com.example.annuairegsh.Adapter.GridViewAdapter;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.AnimationManager;
import com.example.annuairegsh.Manager.CSVManager;
import com.example.annuairegsh.Manager.MyPreferences;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.ListCity;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.Model.ListDepartment;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    RealmResults<Company> companies;
    ArrayList<Contact> contacts;
    private String TAG = "HOME";
    private RecyclerView myrv;
    private RecyclerView contactsView;
    public static Handler handler;
    private String company;
    private String city;
    private EditText search;
    private ContactAdapter adapter;
    private ArrayList<String> picsHome ;
    private ImageView picHome;
    private FastScrollRecyclerView recyclerView;
    private ImageView settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Realm.init(getApplicationContext());
        initView();
        populateImageHome();
        initContactAdapter();
        //populateCompany();
        populateFromAd();

        //Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        //startActivity(intent);
        //populateContact();
    }

    private void initContactAdapter() {

        EditText editText = findViewById(R.id.search);
       // ListContact listContact = (ListContact) getIntent().getSerializableExtra("contacts");
         recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new ContactAdapter(getApplicationContext(), contacts);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecoration);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }


    }

    private void initView(){
        //contacts = new ArrayList<>();
        contacts = RealmManager.getContactsByName("Adam");
        myrv = findViewById(R.id.recyclerview_id);
        settingButton = findViewById(R.id.setting_ics);
        handler = new HandlerHome();
        search = findViewById(R.id.search);
        contactsView = findViewById(R.id.recycler);
        picHome = findViewById(R.id.up);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPreferences.deletePreference(Constant.SECRET);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0 && myrv.getVisibility() == View.GONE){

                    AnimationManager.setToInvisibleRight(contactsView);
                    AnimationManager.SetToVisibleLeft(myrv);
                }
                else if(s.toString().length() >  0 && myrv.getVisibility() == View.VISIBLE){

                    AnimationManager.SetToVisibleRight(contactsView);
                    AnimationManager.setToInvisibleLeft(myrv);
                }

                if(s.toString().length() == 0) {
                    contacts = null;
                    adapter = new ContactAdapter(getApplicationContext(), new ArrayList<Contact>());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
                else {
                    contacts = RealmManager.getContactsByName(s.toString());
                    //API_Manager.getPicById(contacts.get(0).getId(), getApplicationContext());
                    adapter = new ContactAdapter(getApplicationContext(), contacts);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      //  myrv.setNestedScrollingEnabled(false);
    }
    private void populateCompany(){
        //companies = new ArrayList<>();
     //   contacts = new ArrayList<>();

      //  companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("ALUX", "Alux", R.drawable.gsha));
        companies.add(new Company("BTPH", "btph", R.drawable.gsha));
        companies.add(new Company("GSHA", "sodea", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
        companies.add(new Company("GSHA", "GSH", R.drawable.gsha));
       GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
        myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,4));
      myrv.setAdapter(myAdapter);


    }

    public void populateFromAd(){
       // companies = new ArrayList<>();

        companies = RealmManager.getCompanies();
        GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies);
        myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,4));
        // myAdapter.setHasStableIds(true);
        myrv.setAdapter(myAdapter);

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
       /* List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("name","Adel"));
        params.add(new KeyValuePair("number","3"));*/
      /*  String url = Constant.API_URL + "/company";

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
                    myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,4));
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
        requestQueue.add(jsonArrayRequest);*/
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
                   // CSVManager.saveContactsInCSV(contacts);

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
                        intent.putExtra("cities",new ListCity(cities));
                        intent.putExtra("company", company);
                        startActivity(intent);
                    }
                    else if(cities.size() == 1){
                        city = cities.get(0).getCode();
                  //    API_Manager.getDepartement(company, cities.get(0).getCode(), getApplicationContext(), handler);

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
                    intent.putExtra("company", company);
                    intent.putExtra("city", city);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void populateImageHome(){
        picsHome = new ArrayList<>();
        picsHome.add("home_alpostone");
        picsHome.add("home_btph");
        picsHome.add("home_giryad");
        picsHome.add("home_hta");
        picsHome.add("home_htf");
        picsHome.add("home_htf2");
        picsHome.add("home_logistique");
        picsHome.add("home_mdm");
        picsHome.add("home_puma");
        picsHome.add("home_sech");
        picsHome.add("home_sodea");

        int picId = getResources().getIdentifier(picsHome.get(new Random().nextInt(11)), "drawable", getPackageName());

        if (picId != 0) {
            picHome.setImageResource(picId);
            //Glide.with(mContext).load(picId).into(holder.imageC);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        picsHome = new ArrayList<>();
        picsHome.add("home_alpostone");
        picsHome.add("home_btph");
        picsHome.add("home_giryad");
        picsHome.add("home_hta");
        picsHome.add("home_htf");
        picsHome.add("home_htf2");
        picsHome.add("home_logistique");
        picsHome.add("home_mdm");
        picsHome.add("home_puma");
        picsHome.add("home_sech");
        picsHome.add("home_sodea");

        int picId = getResources().getIdentifier(picsHome.get(new Random().nextInt(11)), "drawable", getPackageName());

        if (picId != 0) {
            picHome.setImageResource(picId);
            //Glide.with(mContext).load(picId).into(holder.imageC);

        }


    }

    @Override
    public void onBackPressed() {
        if(search.getText().length() == 0){
        super.onBackPressed();}
        else search.setText("");
    }
}
