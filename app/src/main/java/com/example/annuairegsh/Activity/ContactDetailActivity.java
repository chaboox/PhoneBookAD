package com.example.annuairegsh.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;

public class ContactDetailActivity extends AppCompatActivity {
    private Contact contact;
    private TextView name, job, mail, number, location, voip, department, company;
    private ImageView image;

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
    }

    private void populateView(){
        name.setText(contact.getName());
        job.setText(contact.getDescription());
        mail.setText(contact.getMail());
        company.setText(contact.getCompany());
        department.setText(contact.getDepartment());
        voip.setText(contact.getVoip());
        number.setText(contact.getNumber());
        location.setText(contact.getCity());

        String pic =  contact.getPictureC();
        if(pic != null)
        if(!pic.equals("null")) {
            Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
            image.setImageBitmap(bitmap);
        }
    }
}
