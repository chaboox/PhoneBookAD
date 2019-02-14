package com.example.annuairegsh.Activity;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.annuairegsh.Adapter.SimpleRecyclerAdapter;
import com.example.annuairegsh.Model.ListContact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ListContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_alpha);
        ListContact listContact = (ListContact) getIntent().getSerializableExtra("contacts");
        FastScrollRecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(new SimpleRecyclerAdapter(getApplicationContext(), listContact.getContacts()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecoration);

    }
}


