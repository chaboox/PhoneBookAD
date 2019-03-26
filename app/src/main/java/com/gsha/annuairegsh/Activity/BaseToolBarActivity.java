package com.gsha.annuairegsh.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


/**
 * Created by GongWen on 17/8/24.
 */

public abstract class BaseToolBarActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setTitle(TAG);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    protected abstract int getLayoutId();

}
