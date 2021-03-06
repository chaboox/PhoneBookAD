package com.gsha.annuairegsh.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gsha.annuairegsh.Adapter.ContactAdapter;
import com.gsha.annuairegsh.Adapter.GridViewAdapter;
import com.gsha.annuairegsh.Manager.API_Manager;
import com.gsha.annuairegsh.Manager.AnimationManager;
import com.gsha.annuairegsh.Manager.MyPreferences;
import com.gsha.annuairegsh.Manager.RealmManager;
import com.gsha.annuairegsh.Model.Company;
import com.gsha.annuairegsh.Model.Constant;
import com.gsha.annuairegsh.Model.Contact;
import com.gsha.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.google.android.gms.auth.GoogleAuthUtil;
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
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
    private ImageView picHome, backPole;
    private FastScrollRecyclerView recyclerView;
    private ImageView settingButton;
    private ProgressDialog progressDialog;
    private CardView cardConst, cardPierre, cardIndustrie, cardService, cardAgro;
    private  LinearLayout linearLayout;
    private Activity activity;
    private boolean pole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_home2);

        pole = MyPreferences.getMyBool(getApplicationContext(), "notifications_new_message_vibrate", false);
        Realm.init(getApplicationContext());
        initView();
        setListener();
        populateImageHome();
       // search.setText("");
        contactsView.setVisibility(View.GONE);
        if(pole)
            populatePolePic();
        else
            populateFromAd();
        try {
            initContactAdapter();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //populateCompany();


        if(!RealmManager.areContactsInCache()){
            progressDialog.show();
            handler.sendEmptyMessage(Constant.COMPANY);
        }
        else  if(!MyPreferences.getMyBool(getApplicationContext(),"HOMESEEN", false))
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {

                            handler.sendEmptyMessage(Constant.SEARCH_EXPLAIN);
                            MyPreferences.saveMyBool(getApplicationContext(), "HOMESEEN", true);


                        }
                    },
                    1000
            );

        checkUpdate();
        //Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        //startActivity(intent);
        //populateContact();
       // testFunction();
    }

    private void testFunction() {

        Account[] accounts = null;
        final List<String> l = new ArrayList();
        final List<String> listOfAccountTypes = new ArrayList();
        String possibleEmails = "" + "\n**************** Get All Registered Accounts *****************\n";
        if (!(AccountManager.get(HomeActivity.this) == null || AccountManager.get(HomeActivity.this).getAccounts() == null)) {
            accounts = AccountManager.get(HomeActivity.this).getAccounts();
        }
        l.clear();
        l.add("Téléphone");
        listOfAccountTypes.clear();
        listOfAccountTypes.add("Téléphone");
        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        for (Account account : accounts) {
            if (pattern.matcher(account.name).matches() && account.type.equalsIgnoreCase(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)) {
                listOfAccountTypes.add(account.type);
                l.add(account.name);
                possibleEmails = possibleEmails + " --> " + account.name + " : " + account.type + " , \n";
            }
        }
        l.add("Fichier CSV ou vCard.");
        listOfAccountTypes.add("CSV_OR_vCARD");
        Log.d(TAG, "OMARUS " + l.toString());
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause:KLK ");
        pole = MyPreferences.getMyBool(getApplicationContext(), "notifications_new_message_vibrate", false);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop:KLK");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: KLK");
        super.onDestroy();
    }

    private void checkUpdate() {
        if((Integer.valueOf(MyPreferences.getMyString(getApplicationContext(), "sync_frequency", "3")) != -1)){
        long lastUpdate = MyPreferences.getMyLong(getApplicationContext(), Constant.LAST_UPDATE_TIME, System.currentTimeMillis());
       // long lastUpdate = 1551920483000L;
        long frequence = Integer.valueOf(MyPreferences.getMyString(getApplicationContext(), "sync_frequency", "3")) * 86400000;
        Log.d(TAG, "checkUpdate: " + frequence + "   :  " + System.currentTimeMillis() + " L   " + lastUpdate);
        if((lastUpdate + frequence) <= System.currentTimeMillis()){
            Log.d(TAG, "checkUpdate: WORKINGG !!!");
            handler.sendEmptyMessage(Constant.COMPANY);
        }
        }
    }

    private void setListener() {
        cardConst.setOnClickListener(this);
        cardService.setOnClickListener(this);
        cardPierre.setOnClickListener(this);
        cardIndustrie.setOnClickListener(this);
        cardAgro.setOnClickListener(this);
        backPole.setOnClickListener(this);

    }

    private void initContactAdapter() throws UnsupportedEncodingException {

        EditText editText = findViewById(R.id.search);
        try {
            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_gray_24dp, 0, 0, 0);
        }
        catch (Exception e){
            Log.d(TAG, "initContactAdapter: ");
        }
       // ListContact listContact = (ListContact) getIntent().getSerializableExtra("contacts");
         recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new ContactAdapter(getApplicationContext(), contacts, this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecoration);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG},2);
        }


    }


    private void initView(){
        //contacts = new ArrayList<>();
        contacts = RealmManager.getContactsByName("Adam2");
        myrv = findViewById(R.id.recyclerview_id);
        settingButton = findViewById(R.id.setting_ics);
        handler = new HandlerHome();
        linearLayout = findViewById(R.id.pole_linear);
        backPole = findViewById(R.id.back_pole);
        cardConst = findViewById(R.id.cardConst);
        cardIndustrie = findViewById(R.id.cardIndu);
        cardPierre = findViewById(R.id.cardPierre);
        cardService = findViewById(R.id.cardServ);
        cardAgro = findViewById(R.id.cardAgro);
        search = findViewById(R.id.search);
        contactsView = findViewById(R.id.recycler);
        picHome = findViewById(R.id.up);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Synchronisation des contacts");
        progressDialog.setMessage("Patientez un instant...");




        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  NotifyUser("YO", "Message Bla bla bla", 32)
               // MyPreferences.deletePreference(Constant.SECRET);
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
              //  finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean pole = MyPreferences.getMyBool(getApplicationContext(), "notifications_new_message_vibrate", false);
                if(s.toString().length() == 0){

                    AnimationManager.setToInvisibleRight(contactsView);

                    if(pole) {
                        if(backPole.getVisibility()  == View.VISIBLE)
                            AnimationManager.SetToVisibleLeft(myrv);
                        else
                            AnimationManager.SetToVisibleLeft(linearLayout);
                    }
                    else
                       AnimationManager.SetToVisibleLeft(myrv);
                }
                else if(s.toString().length() >  0 && (myrv.getVisibility() == View.VISIBLE || linearLayout.getVisibility() == View.VISIBLE)){

                    AnimationManager.SetToVisibleRight(contactsView);

                    if(pole) {

                        if(backPole.getVisibility()  == View.VISIBLE)
                            AnimationManager.setToInvisibleLeft(myrv);
                        else
                            AnimationManager.setToInvisibleLeft(linearLayout);
                    }
                    else
                        AnimationManager.setToInvisibleLeft(myrv);
                }

                if(s.toString().length() == 0) {
                    contacts = null;
                    try {
                        adapter = new ContactAdapter(getApplicationContext(), new ArrayList<Contact>(), activity);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
                else {
                    contacts = RealmManager.getContactsByName(s.toString());
                    //API_Manager.getPicById(contacts.get(0).getId(), getApplicationContext());
                    try {
                        adapter = new ContactAdapter(getApplicationContext(), contacts, activity);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
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


    public void populateFromAd(){
        backPole.setVisibility(View.GONE);
       // companies = new ArrayList<>();
        LinearLayout linearLayout = findViewById(R.id.pole_linear);
        myrv.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        companies = RealmManager.getCompanies();
        GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies, activity);
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
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardConst:
                populateFiliale("1");
                break;
            case R.id.cardPierre:
                populateFiliale("2");
                break;
            case R.id.cardIndu:
                populateFiliale("3");
                break;
            case R.id.cardServ:
                populateFiliale("4");
                break;
            case R.id.cardAgro:
                populateFiliale("5");
                break;

            case R.id.back_pole:
                populatePolePicWithAnimation();
                break;

        }
    }

    private void populateFiliale(String pole) {
        LinearLayout linearLayout = findViewById(R.id.pole_linear);
      //  myrv.setVisibility(View.VISIBLE);
       // linearLayout.setVisibility(View.GONE);

        AnimationManager.SetToVisibleRight(myrv);
        AnimationManager.SetToVisibleRight(backPole);

        AnimationManager.setToInvisibleLeft(linearLayout);

        companies = RealmManager.getCompanies(pole);
        GridViewAdapter myAdapter = new GridViewAdapter(HomeActivity.this,companies, activity);
        myrv.setLayoutManager(new GridLayoutManager(HomeActivity.this,4));
        // myAdapter.setHasStableIds(true);
        myrv.setAdapter(myAdapter);

    }

    /*public class HandlerHome extends Handler{
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
*/

    public class HandlerHome extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SEARCH_EXPLAIN:
                    new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                            .setTarget(R.id.search)
                            .setPrimaryText("Rechercher un collaborateur")
                            .setSecondaryText("Possibilité de rechercher par nom, numéro et poste")
                            .setPromptBackground(new RectanglePromptBackground())
                            .setPromptFocal(new RectanglePromptFocal())
                            .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                            {
                                @Override
                                public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                {
                                    if (state == MaterialTapTargetPrompt.STATE_BACK_BUTTON_PRESSED)
                                    {
                                        Log.d(TAG, "onPromptStateChanged: OLOL");
                                    }
                                    else if(state == MaterialTapTargetPrompt.STATE_DISMISSED){
                                        new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                                                .setTarget(R.id.setting_ics)
                                                .setPrimaryText("Paramètre")
                                                .setSecondaryText("Choix de l'affichage\nSynchronisation des contacts\nNotification\nDéconnexion")
                                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                                {
                                                    @Override
                                                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                                    {
                                                        if (state == MaterialTapTargetPrompt.STATE_BACK_BUTTON_PRESSED)
                                                        {
                                                            Log.d(TAG, "onPromptStateChanged: OLOL");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_DISMISSED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL2");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_FINISHED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL3");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL4");
                                                        }
                                                        else {
                                                            Log.d(TAG, "onPromptStateChanged: OLOL5");
                                                        }
                                                    }
                                                })
                                                .show();
                                        Log.d(TAG, "onPromptStateChanged: OLOL2");
                                    }
                                    else if(state == MaterialTapTargetPrompt.STATE_FINISHED){
                                        Log.d(TAG, "onPromptStateChanged: OLOL3");
                                    }
                                    else if(state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED){
                                        new MaterialTapTargetPrompt.Builder(HomeActivity.this)
                                                .setTarget(R.id.setting_ics)
                                                .setPrimaryText("Paramètre")
                                                .setSecondaryText("Choix de l'affichage\nSynchronisation des contacts\nNotification\nDéconnexion")
                                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                                {
                                                    @Override
                                                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                                    {
                                                        if (state == MaterialTapTargetPrompt.STATE_BACK_BUTTON_PRESSED)
                                                        {
                                                            Log.d(TAG, "onPromptStateChanged: OLOL");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_DISMISSED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL2");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_FINISHED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL3");
                                                        }
                                                        else if(state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED){
                                                            Log.d(TAG, "onPromptStateChanged: OLOL4");
                                                        }
                                                        else {
                                                            Log.d(TAG, "onPromptStateChanged: OLOL5");
                                                        }
                                                    }
                                                })
                                                .show();
                                    }
                                    else {
                                        Log.d(TAG, "onPromptStateChanged: OLOL5");
                                    }
                                }
                            })
                            .show();
                    break;
                case Constant.SETTING_SYNC:
                    progressDialog.show();
                    handler.sendEmptyMessage(Constant.COMPANY);
                    break;

                case Constant.COMPANY:
                    API_Manager.Syncro(getApplicationContext(), handler, Constant.CONTACT);
                    break;
                case Constant.CONTACT:
                    API_Manager.getAllContacts(getApplicationContext(), handler, Constant.CITY);
                    break;

                case Constant.CITY:
                    new RealmManager().PopulateCityIntoCompany(handler, Constant.DEPARTMENT);
                    break;

                case Constant.DEPARTMENT:
                    new RealmManager().populateDepartmentIntoCity(handler, Constant.DELETE_CONTACT);
                    break;

                case Constant.DELETE_CONTACT:
                    API_Manager.deleteContact(getApplicationContext(), handler, Constant.CONTACT_FETCH);
                    boolean pole = MyPreferences.getMyBool(getApplicationContext(), "notifications_new_message_vibrate", false);
                    if(pole) {
                        search.setText("");
                        populatePolePic();
                    }
                    else {
                        search.setText("");
                        populateFromAd();
                    }
                    Log.d(TAG, "handleMessagePROG: "+ progressDialog.isShowing());
                    if(progressDialog.isShowing())
                    progressDialog.dismiss();
                    if(!MyPreferences.getMyBool(getApplicationContext(),"HOMESEEN", false))
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {

                                        handler.sendEmptyMessage(Constant.SEARCH_EXPLAIN);
                                        MyPreferences.saveMyBool(getApplicationContext(), "HOMESEEN", true);


                                    }
                                },
                                1000
                        );
                    MyPreferences.saveLong(Constant.LAST_UPDATE_TIME, System.currentTimeMillis());
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
        activity = this;
        boolean pole = MyPreferences.getMyBool(getApplicationContext(), "notifications_new_message_vibrate", false);
        if(pole != this.pole)
        if(search.getText().toString().length() == 0)
        if(pole)
            populatePolePic();
        else
            populateFromAd();

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
            if(backPole.getVisibility() == View.VISIBLE)
            {
                populatePolePicWithAnimation();
                backPole.setVisibility(View.GONE);
            }
            else
          super.onBackPressed();
        }
        else search.setText("");
    }

    private Toolbar createToolbar() {
        Toolbar toolbar = new Toolbar(this);
        Toolbar.LayoutParams toolBarParams = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                R.attr.actionBarSize
        );
        toolbar.setLayoutParams(toolBarParams);
        // toolbar.setBackgroundColor(Color.BLUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setBackground(getDrawable(R.drawable.blue_100_600x600));
        }
        //   toolbar.setPopupTheme(R.style.AppTheme_PopupOverlay);
        toolbar.setVisibility(View.VISIBLE);
        return toolbar;
    }

    private void populatePolePic(){

        //backPole.setVisibility(View.GONE);
        myrv.setVisibility(View.GONE);
        backPole.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        ImageView imgConst = findViewById(R.id.imageConst);
        ImageView imgIndu = findViewById(R.id.imageIndustrie);
        ImageView imgRock = findViewById(R.id.imagePierre);
        ImageView imgAgri = findViewById(R.id.imageAgri);
        ImageView imgServ = findViewById(R.id.imageService);

        imgConst.setImageResource(R.drawable.construction);
        imgIndu.setImageResource(R.drawable.industrie);
        imgRock.setImageResource(R.drawable.pierre);
        imgAgri.setImageResource(R.drawable.agri);
        imgServ.setImageResource(R.drawable.service);
    }

    private void populatePolePicWithAnimation(){

        AnimationManager.setToInvisibleRight(myrv);

        AnimationManager.SetToVisibleLeft(linearLayout);
        AnimationManager.setToInvisibleRight(backPole);
        //backPole.setVisibility(View.GONE);
       // myrv.setVisibility(View.GONE);

       // linearLayout.setVisibility(View.VISIBLE);
        ImageView imgConst = findViewById(R.id.imageConst);
        ImageView imgIndu = findViewById(R.id.imageIndustrie);
        ImageView imgRock = findViewById(R.id.imagePierre);
        ImageView imgAgri = findViewById(R.id.imageAgri);
        ImageView imgServ = findViewById(R.id.imageService);

        imgConst.setImageResource(R.drawable.construction);
        imgIndu.setImageResource(R.drawable.industrie);
        imgRock.setImageResource(R.drawable.pierre);
        imgAgri.setImageResource(R.drawable.agri);
        imgServ.setImageResource(R.drawable.service);
    }





}
