package com.example.annuairegsh.ViewCaller;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.annuairegsh.Activity.ContactDetailActivity;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.R;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
    {  // NotifyUser("YO", "Message Bla bla bla", 32);
        KeyguardManager kgm = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        boolean isKeyguardUp = kgm.inKeyguardRestrictedInputMode();
        KeyguardManager.KeyguardLock kgl = kgm.newKeyguardLock("MyCustomDialog");

        if(isKeyguardUp){
            kgl.disableKeyguard();
            isKeyguardUp = false;
        }

     //   wl.acquire();
        Log.d("WORKING", "onCreate: ");
        try
        {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setFinishOnTouchOutside(false);
            super.onCreate(savedInstanceState);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setContentView(R.layout.dialog);
            initializeContent();

         /*   WindowManager.LayoutParams params = getWindow().getAttributes();
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

    private void NotifyUser(String title, String content, int idNotif) {
        Uri uriSoundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Random random = new Random();
        int ID_NOTIF = idNotif;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name =CHANNEL_ID;// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_close_black_24dp)
                .setContentTitle(title)
                .setChannelId(CHANNEL_ID)
                .setContentText(content).setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Vibration
        mBuilder.setVibrate(new long[] { 0, 1000, 1000, 1000, 1000 });

        //LED
        mBuilder.setLights(Color.RED, 3000, 3000);

        //Ton
        mBuilder.setSound(uriSoundNotif);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(ID_NOTIF, mBuilder.build());
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