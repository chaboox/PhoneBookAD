package com.example.annuairegsh.ViewCaller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.annuairegsh.Activity.ContactDetailActivity;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.R;

import java.io.UnsupportedEncodingException;

import androidx.cardview.widget.CardView;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;


public class MyCustomDialog extends Activity
{
    TextView tv_client, code;
    String phone_no, codes, id;
    RelativeLayout layout;
    Button dialog_ok;
    ImageView image, close;
    String picturec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setFinishOnTouchOutside(false);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog);
            initializeContent();

            /*WindowManager.LayoutParams params = getWindow().getAttributes();
            params.x = -100;
            params.height = 70;
            params.width = 1000;
            params.y = -50;

            this.getWindow().setAttributes(params);*/

            phone_no    =   getIntent().getExtras().getString("phone_no");
            codes    =   getIntent().getExtras().getString("name");
            id    =   getIntent().getExtras().getString("id");
            tv_client.setText(""+phone_no );
            code.setText(codes);

            String pic = getIntent().getExtras().getString("picture");
            if (pic == null) pic = "null";
            if(pic .equals("none")){
                image.setImageResource(R.drawable.user);
            }
            else {


                if (!pic.equals("null")) {
                    Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
                   image.setImageBitmap(bitmap);
                } else {
                    Log.d("DKHALE", "onBindViewHolder: " + pic);
                    try {
                        API_Manager.getPicById(id, getApplicationContext(), image);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyCustomDialog.this.finish();
                }
            });

            layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   /* MyCustomDialog.this.finish();
//                    this.setFinishOnTouchOutside(false);
                    System.exit(0);*/

                    Intent intent = new Intent(MyCustomDialog.this, ContactDetailActivity.class);
                    intent.putExtra("id", id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Exception", e.toString());
            e.printStackTrace();
        }
    }

    private void initializeContent()
    {
        image = findViewById(R.id.image);
        layout = findViewById(R.id.layout);
        tv_client   = (TextView) findViewById(R.id.name);
        code = findViewById(R.id.code);
        close = findViewById(R.id.close);
       // dialog_ok   = (Button) findViewById(R.id.dialog_ok);
    }
}