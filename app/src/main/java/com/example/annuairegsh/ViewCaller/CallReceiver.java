package com.example.annuairegsh.ViewCaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {


        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            //showToast(context,"Call started...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
           // showToast(context,"Call ended...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);

                    Contact contact = RealmManager.getContactByNumber(incomingNumber);

                    if(contact != null){
                        final Intent intent = new Intent(context, MyCustomDialog.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("phone_no", contact.getDescription());
                        intent.putExtra("name", contact.getName());
                        intent.putExtra("id", contact.getId());
                        intent.putExtra("picture", contact.getPictureC());
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                context.startActivity(intent);
                            }
                        },0);}

                }
            }, PhoneStateListener.LISTEN_CALL_STATE);

            //showToast(context,"Incoming call...");
        }
    }

    void showToast(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
