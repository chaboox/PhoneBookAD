package com.example.annuairegsh.Activity;

import android.content.Intent;
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
import com.example.annuairegsh.Activity.Home;
import com.example.annuairegsh.Activity.HomeActivity;
import com.example.annuairegsh.Manager.UrlGenerator;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.KeyValuePair;
import com.example.annuairegsh.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.codec.DecoderException;
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
        //setContentView(R.layout.activity_main);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        imageView = findViewById(R.id.imageView);
     //   printUsers();

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
                    String s = (String) jsonObject.get("picture");
                    String s2 = (String) jsonObject.get("pictureC");
                    Log.e(TAG, "onResponse: " + s2);
                   // byte[] base64Decoded = DatatypeConverter.parseBase64Binary(s2);

                    b4 = Base64.decode(s2, Base64.DEFAULT);
                    byte[] b = s.getBytes();
                    Bitmap b2 = decodeSampleBitmap(b4, 60, 60);
                   // Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                 //   byte[] decodedString = Base64.decode(picture,Base64.NO_WRAP);
                   // InputStream inputStream  = new ByteArrayInputStream(decodedString);
                   // Bitmap bitmap2  = BitmapFactory.decodeStream(inputStream);
                   // imageView.setImageResource(R.drawable.ic_launcher_foreground);
                    imageView.setImageBitmap(b2);
                    Log.e(TAG, "onResponse: DD" + b.toString());
                    //Log.e(TAG, "onResponse: " + picture );
                 //   byte[] b3 = convertBitmapToByteArray(decodeSampleBitmap(b, 60, 60));
                    //byte[] b = ConnectionServer.convertBitmapToByteArray(ConnectionServer.decodeSampleBitmap(this.detailInfosCollaboratorObj.getPhoto(), 60, 60));
                   // imageView.setImageBitmap((BitmapFactory.decodeByteArray(b, 0, b.length)));
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
