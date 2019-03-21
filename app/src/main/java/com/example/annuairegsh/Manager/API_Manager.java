package com.example.annuairegsh.Manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.annuairegsh.Activity.HomeActivity;
import com.example.annuairegsh.Activity.LoginActivity;
import com.example.annuairegsh.Adapter.GridViewAdapter;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.KeyValuePair;
import com.example.annuairegsh.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;

public class API_Manager {
    private static ArrayList<City> cities;
    private static ArrayList<Department> departments;
    private static ArrayList<Contact> contacts;
    public static HashMap<String, String> directionDescription;
    public static ArrayList<Company> companies;
    private static Context context;
    private static HashMap<String, String> turn;


    public static void SyncBdd(Context contextP){
        context = contextP;
        //First phase getting the companies


    }

    public static void getCity(String company, Context context,  final  Handler handler){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        cities = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        String url = Constant.API_URL + "/CitiesByCompany";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        cities.add(new City(jsonObject));
                    }

                    Message message = new Message();
                    message.obj = cities;
                    message.what = Constant.CITY;

                    handler.sendMessage(message);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getCity2(final String company, final Context context, final Company cp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        cities = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        String url = Constant.API_URL + "/CitiesByCompany";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    cities.clear();
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        cities.add(new City(jsonObject));


                    }
                    new RealmManager().saveCity(cities, cp);

                    for (City c : cities){
                        getDepartement2(company,c, context );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getDepartement(String company, String city, Context context,  final  Handler handler){
      //  directionDescription = new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        departments = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        params.add(new KeyValuePair("city", city));
        String url = Constant.API_URL + "/DirectionByCompany";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Department department = new Department(jsonObject);
                        String desc = directionDescription.get(jsonObject.getString("name"));
                        if(desc == null)
                            department.setDescription("");
                        else department.setDescription(desc);
                        departments.add(department);

                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                    Message message = new Message();
                    message.obj = departments;
                    message.what = Constant.DEPARTMENT;

                    handler.sendMessage(message);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getDepartement2(final String company, final City city, final Context context){
       // directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        departments = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        params.add(new KeyValuePair("city", city.getCode()));
        String url = Constant.API_URL + "/DirectionByCompany";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    departments.clear();
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Department department = new Department(jsonObject);
                        String desc = directionDescription.get(jsonObject.getString("name"));
                        if(desc == null)
                            department.setDescription("");
                        else department.setDescription(desc);
                        departments.add(department);

                        //String name = jsonObject.getString("name");
                    }

                 // new RealmManager().saveDepartment(departments, company, city);
                    for(Department d :departments){
                    getContactsByDepartment2(company, city.getCode(), d.getCode(), context);}
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getContactsByDepartment(String company, String city, String department, Context context,  final  Handler handler){
        directionDescription= new HashMap<>();
       // getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        contacts = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        params.add(new KeyValuePair("city", city));
        params.add(new KeyValuePair("department", department));
        String url = Constant.API_URL + "/contactsByDepartment";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Contact contact = new Contact(jsonObject);

                        contacts.add(contact);

                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                    Message message = new Message();
                    message.obj = contacts;
                    message.what = Constant.CONTACT;

                    handler.sendMessage(message);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getContactsByDepartment2(String company, String city, String department, final Context context){
        directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        contacts = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("company", company));
        params.add(new KeyValuePair("city", city));
        params.add(new KeyValuePair("department", department));
        String url = Constant.API_URL + "/contactsByDepartment";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Contact contact = new Contact(jsonObject);

                        contacts.add(contact);
                        Log.d("APII", "onResponse: " + contact.getName());
                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                   //new RealmManager().saveContacts(contacts);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    public static void login(String username, String password, final Context context, final Handler handler, final int what){
        directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        byte[] encodedPass = Base64.encode(password.getBytes(), Base64.DEFAULT);
        contacts = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("username", username));
        params.add(new KeyValuePair("password", new String(encodedPass)));
        String url = Constant.API_URL + "/login";

        StringRequest jsonString = new StringRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESS", "onResponse: " + response);

                if(response.length() > 1) {
                    MyPreferences.saveString(Constant.SECRET, response, context);
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);//adam.deboosere@groupe-hasnaoui.com
                   // handler.sendEmptyMessage(what);
                    handler.sendEmptyMessage(Constant.FINISH);
                }
                else {
                    handler.sendEmptyMessage(what);
                    Toast.makeText(context, "Email ou mot de passe invalide", Toast.LENGTH_SHORT).show();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.sendEmptyMessage(what);
                Toast.makeText(context, "Vérifiez votre connexion internet", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonString);
    }

    public static void getAllContacts(final Context context, final Handler handler, final int what){
       // directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        contacts = new ArrayList<>();

        String url = Constant.API_URL + "/allContacts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);


                        Contact contact = new Contact(jsonObject);
                        Contact preContact = RealmManager.getContactbyId(contact.getId());
                        if(preContact != null)
                        contact.setPictureC(preContact.getPictureC());

                        contacts.add(contact);
                        Log.d("APII", "onResponse: " + contact.getName());
                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                    new RealmManager().saveContacts(contacts);
                    handler.sendEmptyMessage(what);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    public static void deleteContact(final Context context, final Handler handler, final int what){
        // directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        contacts = new ArrayList<>();

        String url = Constant.API_URL + "/allDisabledContact";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.d("DEL", "onResponse: " + jsonObject.getString("id") );

                         RealmManager.DeleteById(jsonObject.getString("id"));

                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);

                    handler.sendEmptyMessage(what);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString()  );
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getContactsByNullDepartment( final Context context){
        directionDescription= new HashMap<>();
        //getDescriptionDirection();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        contacts = new ArrayList<>();
        List<KeyValuePair> params = new ArrayList<>();

        params.add(new KeyValuePair("number", "200"));
        String url = Constant.API_URL + "/contactsWithNullDepartment";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response){
                try {

                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Contact contact = new Contact(jsonObject);

                        contacts.add(contact);
                        Log.d("APII", "onResponse: " + contact.getName());
                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
                   new RealmManager().saveContacts(contacts);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR API", "onErrorResponse: " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void getPicById(final String id, final Context context, final ImageView imageView, final Contact contact, final String holderId) throws UnsupportedEncodingException {
        imageView.setImageResource(R.drawable.user);
        //substring
        if(turn == null)
            turn = new HashMap<>();
        final String ticket ;
        Log.d("TURN1", "getPicById: " + turn.toString());
        if(!turn.containsKey(holderId)){
            ticket = "1";
            turn.put(holderId, ticket);

        }
        else {
          //  Log.d("TURN", "getPicById: " + turn.get(imageView));

            ticket = (Integer.valueOf(turn.get(holderId)) + 1) + "";
            turn.put(holderId, ticket);

        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        List<KeyValuePair> params = new ArrayList<>();

        params.add(new KeyValuePair("id", URLEncoder.encode(id, "UTF-8")));
        String url = Constant.API_URL + "/getPicById";

        JsonObjectRequest object = new JsonObjectRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("RESPP", "onResponse: " + id + response.getString("picture"));
                    if( response.getString("picture") != null && response.getString("picture")!= "null"){
                        Bitmap bitmap = decodeSampleBitmap(Base64.decode(response.getString("picture"), Base64.DEFAULT), 60, 60);
                       // Log.d("YODA", "onResponse: " + ticket + "  HHH  " + turn.get(imageView + ""));
                       if(ticket.equals(turn.get(holderId))) {
                            Log.d("DKHAL", "onResponse: ");
                            // Glide.with(context).load(bitmap).into(imageView);
                            imageView.setImageBitmap(bitmap);}

                            else {
                           Log.d("DKHALBAR", "onResponse: ");
                       }
                        //}

                   // contact.setPictureC(response.getString("picture"));
                        RealmManager.savePic(contact, response.getString("picture"));
                    }

                    else {
                        Log.d("DKHALE2", "onResponse: ");
                       imageView.setImageResource(R.drawable.user);
                        RealmManager.savePic(contact, "none");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPPERROR", "onResponse: " + error.toString());
            }
        });
        requestQueue.add(object);
    }

    public static void getPicByIdForDetailActivity(final String id, final Context context, final ImageView imageView, final Contact contact) throws UnsupportedEncodingException {
        imageView.setImageResource(R.drawable.user);
        //substring


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        List<KeyValuePair> params = new ArrayList<>();

        params.add(new KeyValuePair("id", URLEncoder.encode(id, "UTF-8")));
        String url = Constant.API_URL + "/getPicById";

        JsonObjectRequest object = new JsonObjectRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("RESPP", "onResponse: " + id + response.getString("picture"));
                    if( response.getString("picture") != null && response.getString("picture")!= "null"){
                        Bitmap bitmap = decodeSampleBitmap(Base64.decode(response.getString("picture"), Base64.DEFAULT), 60, 60);
                        // Log.d("YODA", "onResponse: " + ticket + "  HHH  " + turn.get(imageView + ""));

                            Log.d("DKHAL", "onResponse: ");
                            // Glide.with(context).load(bitmap).into(imageView);
                            imageView.setImageBitmap(bitmap);


                        //}

                        // contact.setPictureC(response.getString("picture"));
                        RealmManager.savePic(contact, response.getString("picture"));
                    }

                    else {
                        Log.d("DKHALE2", "onResponse: ");
                        imageView.setImageResource(R.drawable.user);
                        RealmManager.savePic(contact, "none");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPPERROR", "onResponse: " + error.toString());
            }
        });
        requestQueue.add(object);
    }
    public static void getPicsByIds(final ArrayList<String> id, final Context context, final Handler handler, final int what) throws UnsupportedEncodingException {
       // imageView.setImageResource(R.drawable.user);
        //substring

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        List<KeyValuePair> params = new ArrayList<>();
        Log.d("LOGD", "getPicsByIds: " + getFilterListIds(id)  + " KKK " + URLEncoder.encode(getFilterListIds(id), "UTF-8"));
        params.add(new KeyValuePair("id", URLEncoder.encode(getFilterListIds(id), "UTF-8")));
        String url = Constant.API_URL + "/getPicByIds";

        JsonArrayRequest object = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject response = (JSONObject) jsonArray.get(i);
                    Log.d("RESPP", "onResponse: " + id + response.getString("picture"));
                    if( response.getString("picture") != null && response.getString("picture")!= "null"){
                        Bitmap bitmap = decodeSampleBitmap(Base64.decode(response.getString("picture"), Base64.DEFAULT), 60, 60);
                        // Log.d("YODA", "onResponse: " + ticket + "  HHH  " + turn.get(imageView + ""));
                        Log.d("LOGID", "onResponse: " + response.getString("id"));                        //}

                        // contact.setPictureC(response.getString("picture"));
                        RealmManager.savePicById(response.getString("id"), response.getString("picture"));
                    }

                    else {
                        Log.d("DKHALE2", "onResponse: ");

                        RealmManager.savePicById(response.getString("id"), "none");
                    }
                }

                handler.sendEmptyMessage(what);


            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

            , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPPERROR", "onResponse: " + error.toString());
            }
        });

        object.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(object);
    }

    private static String getFilterListIds(ArrayList<String> id) {
        String result = "(|";
        for(int i = 0; i < id.size(); i++) {
            result = result + "(distinguishedName=" + id.get(i)+ ")";
        }
        result = result + ")";
        return result;
    }


    public static void getPicById(final String id, final Context context, final ImageView imageView) throws UnsupportedEncodingException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final Contact contact2 = RealmManager.getContactbyId(id);
        List<KeyValuePair> params = new ArrayList<>();

        params.add(new KeyValuePair("id", URLEncoder.encode(id, "UTF-8")));
        String url = Constant.API_URL + "/getPicById";

        JsonObjectRequest object = new JsonObjectRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("RESPP", "onResponse: " + id + response.getString("picture"));
                    if( response.getString("picture") != null && response.getString("picture")!= "null"){
                        Bitmap bitmap = decodeSampleBitmap(Base64.decode(response.getString("picture"), Base64.DEFAULT), 60, 60);
                        Glide.with(context).load(bitmap).into(imageView);
                       //  contact2.setPictureC(response.getString("picture"));
                      RealmManager.savePic(contact2, response.getString("picture"));
                    }

                    else {
                        Log.d("DKHALE2", "onResponse: ");
                        imageView.setImageResource(R.drawable.user);
                        RealmManager.savePic(contact2, "none");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPPERROR", "onResponse: " + error.toString());
            }
        });
        requestQueue.add(object);
    }
  /*  private static void getDescriptionDirection(){
        directionDescription.put("APP", "Approvisionnements");
        directionDescription.put("CDF", "Centre De Formation");
        directionDescription.put("CDG", "Contrôle De Gestion");
        directionDescription.put("DAA", "Direction/Département Achats Approvisionnements");
        directionDescription.put("DAC", "Direction Assistance Clientèle");
        directionDescription.put("DAG", "Direction/Département Administration Générale");
        directionDescription.put("DCC", "Direction Commerciale Centre");//
        directionDescription.put("DCE", "Direction/Département Commerce Externe");//
        directionDescription.put("DCG", "Direction/Département Contrôle de gestion");//
        directionDescription.put("DCO", "Direction Commerciale Ouest");//
        directionDescription.put("DFC", "Direction/Département Finance Comptabilité");//
        directionDescription.put("DGR", "Direction Générale");//
        directionDescription.put("DIM", "Direction/Département Importations");//
        directionDescription.put("DMC", "Direction/Département Marketing et Commerciale");//
        directionDescription.put("DQH", "Direction Qualité");//
        directionDescription.put("DQC", "Direction/Département Contrôle Qualité");//
        directionDescription.put("DRE", "Direction/Département Réalisation");//
        directionDescription.put("DRH", "Direction Ressources Humaines");//
        directionDescription.put("DSD", "Direction Stratégie et Développement");//
        directionDescription.put("DSI", "Direction/Département Systèmes d'Information");//
        directionDescription.put("DTQ", "Direction/Département Technique");//
        directionDescription.put("GDS", "Gestion des Stocks");//
        directionDescription.put("GRH", "Gestion des Ressources Humaines");//
        directionDescription.put("HSE", "Hygiène Securité Environnement");//
        directionDescription.put("LAB", "Laboratoire");//
        directionDescription.put("LOG", "Logistique");//
        directionDescription.put("MNT", "Maintenance");//
        directionDescription.put("PRM", "Production et Maintenance");//
        directionDescription.put("PRO", "Production");//
        directionDescription.put("SMQ", "Systèmes Management Qualité");//
        directionDescription.put("CIM", "Cimenterie");//
        directionDescription.put("DPR", "Directeur de Projet");//
        directionDescription.put("TRS", "Trésorerie");
        directionDescription.put("DAF", "Direction/Département Administration & Finances");
        directionDescription.put("SEC", "Sécurité");
        directionDescription.put("SECU", "Sécurité");
        directionDescription.put("ARBO", "Département Arboricole");
        directionDescription.put("DMK", "Direction Marketing");
    }*/

    public static void Syncro(final Context context, final Handler handler, final int what){
        companies = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                        Company company = new Company(jsonObject);
                        Log.d("APCC", "onResponse: " + company.getName());
                        companies.add(company);
                        //String name = jsonObject.getString("name");
                    }
                    RealmManager.saveCompany(companies);
                    handler.sendEmptyMessage(what);
                   /* for(Company company: companies){
                    getCity2(company.getNameAD(), context, company);
                    }
*/
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "onErrorResponse: " + error.toString());
                Toast.makeText(context, "Mode offline !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}
