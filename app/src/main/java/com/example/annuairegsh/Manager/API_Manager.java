package com.example.annuairegsh.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.KeyValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class API_Manager {
    private static ArrayList<City> cities;
    private static ArrayList<Department> departments;
    private static ArrayList<Contact> contacts;
    public static HashMap<String, String> directionDescription;

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
                        //String name = jsonObject.getString("name");
                    }
                    //CSVManager.CreateRootFolder();
                    //CSVManager.saveInCSV(companies);
                    // myrv.getRecycledViewPool().setMaxRecycledViews(R.id.cardview_id,0);
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

    public static void getDepartement(String company, String city, Context context,  final  Handler handler){
        directionDescription= new HashMap<>();
        getDescriptionDirection();
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

    public static void getContactsByDepartment(String company, String city, String department, Context context,  final  Handler handler){
        directionDescription= new HashMap<>();
        getDescriptionDirection();
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

    private static void getDescriptionDirection(){
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
    }


}
