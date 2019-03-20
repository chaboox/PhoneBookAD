package com.example.annuairegsh.ViewCaller;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DebugUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.annuairegsh.Activity.ContactDetailActivity;
import com.example.annuairegsh.Manager.MyPreferences;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;

import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;

public class CallReceiver extends BroadcastReceiver {
    private static Context context;
    private static int i = 0;
    private static String ACTION_1 = "YO";
    @Override
    public void onReceive(final Context context, Intent intent) {
        boolean mybool = MyPreferences.getMyBool(context, "notif", true);
        Log.d("MYBOOL", "onReceive: " + mybool);
        if(mybool){

        Log.d("MON_ISTART", "onReceive: " + i);
        this.context = context;
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            //showToast(context,"Call started...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
           // showToast(context,"Call ended...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            i++;
            Log.d("MON_I", "onReceive: " + i);
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    boolean mybool = MyPreferences.getMyBool(context, "notif", true);
                    if(mybool)
                    {
                    Log.d("MON_I_STATE", "onReceive: " + state + i + "#"+ incomingNumber + "#");
                    super.onCallStateChanged(state, incomingNumber);
                    Log.d("MON_I_STATE", "onReceive: " + state + i);
                    if(state == 1){
                    Bitmap bitmap = null;
                    Log.d("MON_I_STATE", "onReceive: " + state + i + "#"+ incomingNumber + "#");
                    Contact contact = RealmManager.getContactByNumber(incomingNumber);
                        Log.d("IAD", "onCallStateChanged: " + contact);
                    if(contact != null){
                        String pic = contact.getPictureC();
                        if (pic == null) pic = "null";
                        if(pic .equals("none")){
                            // image.setImageResource(R.drawable.user);
                        }
                        else {


                            if (!pic.equals("null")) {
                                bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
                                //image.setImageBitmap(bitmap);
                            } else {
                                Log.d("DKHALE", "onBindViewHolder: " + pic);
                   /* try {
                      //  API_Manager.getPicById(id, getApplicationContext(), image);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
                            }
                        }
                        NotifyUser("Appel de "+ contact.getName(), contact.getDescription() + "\n" + contact.getCompany() + " - " + contact.getCity() + " - " + contact.getDepartment(), contact.getId(), bitmap , incomingNumber);
                        /*final Intent intent = new Intent(context, MyCustomDialog.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("phone_no", contact.getDescription());
                        intent.putExtra("name", contact.getName());
                        intent.putExtra("id", contact.getId());
                        intent.putExtra("picture", contact.getPictureC());
                        intent.putExtra("company", contact.getCompany());
                        intent.putExtra("city", contact.getCity());
                        intent.putExtra("departement", contact.getDepartment());
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                context.startActivity(intent);
                            }
                        },0);*/}}

                }}
            }, PhoneStateListener.LISTEN_CALL_STATE);

            //showToast(context,"Incoming call...");
        }}
    }

    void showToast(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void NotifyUser(String title, String content, String id, Bitmap bitmap, String incomingNumber) {
        Log.d("NOTIFYME", "NotifyUser: ");
       /* Intent action1Intent = new Intent(context, NotificationActionService.class)
                .setAction(ACTION_1);

        PendingIntent action1PendingIntent = PendingIntent.getService(context, 0,
                action1Intent, PendingIntent.FLAG_ONE_SHOT);*/
       // int idNotif = r.nextInt(5000) ;
        int idNotif = Integer.valueOf(incomingNumber);
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,  idNotif/* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri uriSoundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // int ID_NOTIF = idNotif;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name =CHANNEL_ID;// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder;
        if(bitmap != null) {

            mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.call)
                    .setContentTitle(title)
                    .setChannelId(CHANNEL_ID)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentText(content).setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(content))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        }
        else {
            mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.call)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setContentText(content).setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(content))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }

        //Vibration
        mBuilder.setVibrate(new long[] { 0, 1000, 1000, 1000, 1000 });

        //LED
        mBuilder.setLights(Color.RED, 3000, 3000);

        //Ton
        mBuilder.setSound(uriSoundNotif);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(idNotif, mBuilder.build());
    }


    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            Log.d("HOUNA", "onHandleIntent: ");
            //
            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + "0555375358")));
         //   DebugUtils.log("Received notification action: " + action);
            if (ACTION_1.equals(action)) {
                // TODO: handle action 1.
                Log.d("HOUNA", "onHandleIntent: ");
                // If you want to cancel the notification: NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
            }
        }
}
}
