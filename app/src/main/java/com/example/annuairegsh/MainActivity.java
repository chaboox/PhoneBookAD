package com.example.annuairegsh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.annuairegsh.Manager.UrlGenerator;
import com.example.annuairegsh.Model.KeyValuePair;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
//This is a test class

public class MainActivity extends AppCompatActivity {
    public static String TAG = "TEST_MAIN";
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        Log.d(TAG, "onCreate: ");
        Log.e(TAG, "onCreate: ");
        System.out.print("OUIDDDDD");

       printUsers();

    }

    public void printUsers(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<KeyValuePair> params = new ArrayList<>();
        params.add(new KeyValuePair("name","Adel"));
        params.add(new KeyValuePair("number","3"));
        String url = "http://192.168.137.1:8080/contactsByName";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, UrlGenerator.generateUrl(url, params), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());
                try {
                    JSONObject jsonObject = (JSONObject) response.get(0);
                    String name = jsonObject.getString("name");
                    String picture = jsonObject.getString("picture");
                    byte[] b = picture.getBytes();
                    Bitmap b2 = decodeSampleBitmap(b, 60, 60);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                    byte[] decodedString = Base64.decode(picture,Base64.NO_WRAP);
                    InputStream inputStream  = new ByteArrayInputStream(decodedString);
                    Bitmap bitmap2  = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageResource(R.drawable.ic_launcher_foreground);
                    imageView.setImageBitmap(b2);
                    Log.e(TAG, "onResponse: " + name );
                    Log.e(TAG, "onResponse: " + picture );
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
