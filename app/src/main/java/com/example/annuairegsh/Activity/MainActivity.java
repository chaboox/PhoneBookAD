package com.example.annuairegsh.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Manager.UrlGenerator;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.KeyValuePair;
import com.example.annuairegsh.Model.ListCity;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //GotoCityAcitvity();
       // GOtoContactListActivity();
        Realm.init(getApplicationContext());
        //RealmManager.test();
        //RealmManager.showTest();

       // API_Manager.Syncro(getApplicationContext());

        RealmManager.showTest();

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
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("contacts", new ListContact(contacts));
        startActivity(intent);
        finish();
        imageView = findViewById(R.id.imageView);
     //   printUsers();

    }

    private void GotoCityAcitvity(){
        Intent intent = new Intent(MainActivity.this, CityActivity.class);
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Sidi bel abbes", "SBA"));
        cities.add(new City("Tamanrasset", "TMR"));
        cities.add(new City("Oran", "ORN"));
        cities.add(new City("Tlemcen", "TLM"));
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
}
