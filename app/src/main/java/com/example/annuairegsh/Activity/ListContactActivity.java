package com.example.annuairegsh.Activity;



import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairegsh.Adapter.ContactAdapter;
import com.example.annuairegsh.Manager.ContactManager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.realm.RealmList;
import io.realm.RealmResults;


public class ListContactActivity extends AppCompatActivity {
    private ContactAdapter adapter;
    private RealmResults<Contact> contacts;
    private ImageView back, export ;

    private TextView dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_alpha);
       // ListContact listContact = (ListContact) getIntent().getSerializableExtra("contacts");
        EditText editText = findViewById(R.id.search);
        dep= findViewById(R.id.dep);
        back = findViewById(R.id.back);
        export = findViewById(R.id.export);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // for(Contact contact: contacts){
                    askForPermissionOrExportContact(contacts);
               // }

                //Toast.makeText(getApplicationContext(), "contacts enregistrés", Toast.LENGTH_SHORT).show();
            }
        });
        String company = getIntent().getStringExtra("company");
        String city= getIntent().getStringExtra("city");
        String department = getIntent().getStringExtra("department");
        dep.setText(department);

        Log.d("RESULTT", "onCreate: " + company + "   " +  city + "  " + department );
        contacts = RealmManager.getContactByDeparmtment(department, company, city);
        FastScrollRecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        try {
            adapter = new ContactAdapter(getApplicationContext(), contacts);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecoration);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    public  void askForPermissionOrExportContact(final RealmResults<Contact> contacts) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListContactActivity.this,
                    Manifest.permission.WRITE_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(ListContactActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        12);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ListContactActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        12);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                          for(Contact contact: contacts)  ContactManager.exportContact(contact, getApplicationContext());
                            Toast.makeText(getApplicationContext(), "contacts enregistrés", Toast.LENGTH_SHORT).show();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ListContactActivity.this);
            builder.setMessage("Exporter ce departement dans votre liste de contact?").setPositiveButton("Oui", dialogClickListener)
                    .setNegativeButton("Non", dialogClickListener).show();
            // exportContact(contact, context);
        }
    }

}





