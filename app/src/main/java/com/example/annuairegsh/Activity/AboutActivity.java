package com.example.annuairegsh.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.annuairegsh.R;

public class AboutActivity extends AppCompatActivity {
    private ImageView facebook, url, twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        facebook = findViewById(R.id.facebook);
        url = findViewById(R.id.web);
        twitter = findViewById(R.id.twitter);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getApplicationContext(), "https://www.facebook.com/GroupeHasnaoui");
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkedinIntent = new Intent(Intent.ACTION_VIEW);
                String linkedUrl = "https://twitter.com/GSHspa";
                linkedinIntent.setData(Uri.parse(linkedUrl));
                startActivity(linkedinIntent);
            }
        });

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkedinIntent = new Intent(Intent.ACTION_VIEW);
                String linkedUrl ="https://www.groupe-hasnaoui.com/";
                linkedinIntent.setData(Uri.parse(linkedUrl));
                startActivity(linkedinIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AboutActivity.this, SettingsActivity.class));
        finish();

    }

    public String getFacebookPageURL(Context context, String FACEBOOK_URL) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
