package com.gsha.annuairegsh.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.realm.Realm;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gsha.annuairegsh.Manager.API_Manager;
import com.gsha.annuairegsh.Manager.MyPreferences;
import com.gsha.annuairegsh.Manager.NukeSSLCerts;
import com.gsha.annuairegsh.Manager.RealmManager;
import com.gsha.annuairegsh.Manager.UrlGenerator;
import com.gsha.annuairegsh.Model.City;
import com.gsha.annuairegsh.Model.Constant;
import com.gsha.annuairegsh.Model.Contact;
import com.gsha.annuairegsh.Model.KeyValuePair;
import com.gsha.annuairegsh.Model.ListCity;
import com.gsha.annuairegsh.Model.ListContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
//This is a test class

public class MainActivity extends AppCompatActivity {
    public static String TAG = "TEST_MAIN";
    public ImageView imageView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 24){
            NukeSSLCerts.nuke();
        }
        handler = new MainHandler();
        String secret = MyPreferences.getMyString(getApplicationContext(), Constant.SECRET, "0");

        //setContentView(R.layout.activity_main);
        //GotoCityAcitvity();
       // GOtoContactListActivity();
       /* String split = "CN=Imene AZZOUN,OU=Users,OU=SBA,OU=HTAS,DC=groupe-hasnaoui,DC=local";
        String[] sp = split.split(",");

        Log.d(TAG, "onCreate: POPO" +  sp[5]);*/

        Realm.init(getApplicationContext());
        //RealmManager.test();
        //RealmManager.showTest();
        //handler.sendEmptyMessage(Constant.COMPANY);
       // API_Manager.Syncro(getApplicationContext());
       // API_Manager.getAllContacts(getApplicationContext());
      //  Log.d(TAG, "onCreate:PPPL " + new RealmManager.getCityByCompany("GSHA"));

        //API_Manager.getContactsByNullDepartment(getApplicationContext());
        RealmManager.showTest();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
      //  Intent intent = new Intent(this, HomeActivity.class);
        Intent intent;
        if (secret.length() > 1){
            intent = new Intent(this, HomeActivity.class);
        }
        else
            intent = new Intent(this, LoginActivity.class);
       // intent.putExtra("contacts", new ListContact(contacts));
        startActivity(intent);
       finish();
  //      imageView = findViewById(R.id.imageView);
     //   printUsers();

    }

    private void GotoCityAcitvity(){
        Intent intent = new Intent(MainActivity.this, CityActivity.class);
        ArrayList<City> cities = new ArrayList<>();
       /* cities.add(new City("Sidi bel abbes", "SBA"));
        cities.add(new City("Tamanrasset", "TMR"));
        cities.add(new City("Oran", "ORN"));
        cities.add(new City("Tlemcen", "TLM"));*/
        //cities.add(new City("Sidi bel abbes", "SBA"));
        //RealmManager.saveCity(cities);
       // Log.d(TAG, "GotoCityAcitvity:DDD " + RealmManager.showCity());
        RealmManager.showCity();
        intent.putExtra("cities",new ListCity(cities));
        intent.putExtra("company", "GSHA");
        startActivity(intent);
        finish();
    }

    private void GOtoContactListActivity(){
        Intent intent = new Intent(MainActivity.this, ListContactActivity.class);
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("adam debboosere"));
        contacts.add(new Contact("adel achour"));
        contacts.add(new Contact("Roua marouf"));
        contacts.add(new Contact("Souheil hadj habib"));
        contacts.add(new Contact("Asla"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));
        contacts.add(new Contact("adel"));

        intent.putExtra("contacts", new ListContact(contacts));
        //RealmManager.saveContacts(contacts);
        startActivity(intent);
        finish();


    }

    public void printUsers(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("name","Adel"));
        params.add(new KeyValuePair("number","3"));
        String url = Constant.API_URL+ "/contactsByName";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());
                byte[] b4 = null;
                try {
                    JSONObject jsonObject = (JSONObject) response.get(0);
                    String name = jsonObject.getString("name");
                   // String s = (String) jsonObject.get("picture");
                    String s2 = (String) jsonObject.get("pictureC");
                    Log.e(TAG, "onResponse: " + s2);
                   // byte[] base64Decoded = DatatypeConverter.parseBase64Binary(s2);

                    b4 = Base64.decode(s2, Base64.DEFAULT);
                   // byte[] b = s.getBytes();
                    Bitmap b2 = decodeSampleBitmap(b4, 60, 60);

                    imageView.setImageBitmap(b2);

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
    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        byte[] b = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return b;
        }
    }

    public static Bitmap decodeSampleBitmap(byte[] bitmap, int reqHeight, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
                    break;
            }
        }
    }


}
