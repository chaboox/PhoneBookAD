package com.example.annuairegsh.Activity;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairegsh.Adapter.ContactAdapter;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.realm.RealmList;
import io.realm.RealmResults;


public class ListContactActivity extends AppCompatActivity {
    private ContactAdapter adapter;
    private RealmResults<Contact> contacts;
    private ImageView back;

    private TextView dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_alpha);
       // ListContact listContact = (ListContact) getIntent().getSerializableExtra("contacts");
        EditText editText = findViewById(R.id.search);
        dep= findViewById(R.id.dep);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        adapter = new ContactAdapter(getApplicationContext(), contacts);
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
}


