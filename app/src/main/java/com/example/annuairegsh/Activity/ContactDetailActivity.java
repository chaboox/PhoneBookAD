package com.example.annuairegsh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import webphone.webphone;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annuairegsh.Manager.MyClipboardManager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;

import java.util.ArrayList;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;


public class ContactDetailActivity extends AppCompatActivity {
    private Contact contact;
    private TextView name, job, mail, number, location, voip, department, company;
    private ImageView image, close, export;
    private RelativeLayout mailLayout, numberLayout, locationLayout, voipLayout, departmentLayout, companyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        initView();
        Intent intent = getIntent();
        String contactId = intent.getStringExtra("id");
        //contact = (Contact) intent.getSerializableExtra("contact");
        contact = RealmManager.getContactbyId(contactId);
        populateView();

        initListener();

    }

    private void initView() {
       name = findViewById(R.id.name);
       job = findViewById(R.id.job);
       image = findViewById(R.id.image);
       number = findViewById(R.id.number);
       mail = findViewById(R.id.mail);
       company = findViewById(R.id.company);
       department = findViewById(R.id.department);
       voip = findViewById(R.id.fix);
       location = findViewById(R.id.location);
       close = findViewById(R.id.close);
       export = findViewById(R.id.export);



       mailLayout = findViewById(R.id.mail_layout);
       numberLayout = findViewById(R.id.number_layout);
       locationLayout = findViewById(R.id.location_layout);
       voipLayout = findViewById(R.id.voip_layout);
       departmentLayout = findViewById(R.id.department_layout);
       companyLayout = findViewById(R.id.company_layout);
    }

    private void initListener(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               askForPermission();
            }
        });


        mailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent("android.intent.action.SEND");
                email.setType("application/octet-stream");
                email.setData(Uri.parse("mailto:"));
                email.putExtra("android.intent.extra.EMAIL", new String[]{contact.getMail()});
                email.setType("message/rfc822");
                ContactDetailActivity.this.startActivity(Intent.createChooser(email, "Email"));
            }
        });

        mailLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyClipboardManager.copyToClipboard(getApplicationContext(), contact.getMail(), "Email");
                return true;
            }
        });

        numberLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyClipboardManager.copyToClipboard(getApplicationContext(), contact.getNumber(), "Numéro");
                return true;
            }
        });

        numberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        voipLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyClipboardManager.copyToClipboard(getApplicationContext(), contact.getVoip(), "Voip");
                return true;
            }
        });

        voipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callVoip();
            }
        });

    }


    private void exportContact(){
        //askForPermission();

        String DisplayName = contact.getName();
        String MobileNumber = contact.getNumber();
        //String HomeNumber = "1111";
        String WorkNumber = contact.getVoip();
        String emailID = contact.getMail();
        String company = contact.getCompany();
        String jobTitle = contact.getDescription();
        ArrayList <ContentProviderOperation> ops = new ArrayList< ContentProviderOperation >();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (!DisplayName.equals("null")) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (!MobileNumber.equals("null")) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
     

        //------------------------------------------------------ Work Numbers
        if (!WorkNumber.equals("null")) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (!emailID.equals("null")) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!company.equals("") && !jobTitle.equals("")) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getApplicationContext(), "contact enregistré", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(ContactDetailActivity.this,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactDetailActivity.this,
                    Manifest.permission.WRITE_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(ContactDetailActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        12);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ContactDetailActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        12);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            exportContact();
        }
    }

    private void populateView(){

        name.setText(contact.getName());
        if(!contact.getDescription().equals("null")){
            job.setText(contact.getDescription());

        }
        else job.setText("");

        if(!contact.getMail().equals("null")){
            mail.setText(contact.getMail());

        }
        else mailLayout.setVisibility(View.GONE);

        if(!contact.getCompany().equals("null")){
            company.setText(contact.getCompany());

        }
        else companyLayout.setVisibility(View.GONE);

        if(!contact.getDepartment().equals("null")){
            department.setText(contact.getDepartment());

        }
        else departmentLayout.setVisibility(View.GONE);

        if(!contact.getVoip().equals("null")){
           // Log.d("VOIP", "populateView: L" + voi);
            voip.setText(contact.getVoip());

        }
        else voipLayout.setVisibility(View.GONE);

        if(!contact.getNumber().equals("null")){
            number.setText(contact.getNumber());
        }
        else numberLayout.setVisibility(View.GONE);

        if(!contact.getCity().equals("null")){
            location.setText(contact.getCity());
        }
        else locationLayout.setVisibility(View.GONE);


        String pic =  contact.getPictureC();
        if(pic != null)
        if(!pic.equals("null")) {
            Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
            image.setImageBitmap(bitmap);
        }
    }

    public void call() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission("android.permission.CALL_PHONE") == PackageManager.PERMISSION_DENIED || shouldShowRequestPermissionRationale("android.permission.CALL_PHONE"))) {
            //   Toast.makeText(getApplicationContext(), "permission nécessaire", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 95);
            return;
        }
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + contact.getNumber())));
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 95 /*123*/:
                if (grantResults[0] == 0) {
                    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + contact.getNumber())));
                    return;
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                    return;
                }
            default:
                return;
        }
    }

   /* public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this).setMessage((CharSequence) message).setPositiveButton((CharSequence) "OK", okListener).setNegativeButton((CharSequence) "Cancel", null).create().show();
    }*/

   private void callVoip(){
      /* webphone wobj = new webphone();
       wobj.API_SetParameter("serveraddress", "VOIP_SERVER_IP_OR_DOMAIN");
       wobj.API_SetParameter("username", "SIP_USERNAME");
       wobj.API_SetParameter("password", "SIP_PASSWORD");
       wobj.API_SetParameter("loglevel", "5");
       wobj.API_Start();
       wobj.API_Call(-1, "DESTINATION");*/


   }
}
