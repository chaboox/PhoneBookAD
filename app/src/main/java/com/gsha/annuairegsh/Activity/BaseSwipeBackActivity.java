package com.gsha.annuairegsh.Activity;

import android.os.Bundle;

import android.widget.RadioButton;

import com.gsha.annuairegsh.R;
import com.gw.swipeback.SwipeBackLayout;

import androidx.annotation.Nullable;


/**
 * Created by GongWen on 17/8/25.
 */

public abstract class BaseSwipeBackActivity extends BaseToolBarActivity {
    //implements CompoundButton.OnCheckedChangeListener {
    protected SwipeBackLayout mSwipeBackLayout;
    protected RadioButton fromLeftRb;
    protected RadioButton fromTopRb;
    protected RadioButton fromRightRb;
    protected RadioButton fromBottomRb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipeBackLayout);

        //mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
       /* fromLeftRb = (RadioButton) findViewById(R.id.fromLeftRb);
        fromLeftRb.setOnCheckedChangeListener(this);
        fromTopRb = (RadioButton) findViewById(R.id.fromTopRb);
        fromTopRb.setOnCheckedChangeListener(this);
        fromRightRb = (RadioButton) findViewById(R.id.fromRightRb);
        fromRightRb.setOnCheckedChangeListener(this);
        fromBottomRb = (RadioButton) findViewById(R.id.fromBottomRb);
        fromBottomRb.setOnCheckedChangeListener(this);*/
    }

  /*  @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.fromLeftRb:
                    mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
                    break;
                case R.id.fromTopRb:
                    mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_TOP);
                    break;
                case R.id.fromRightRb:
                    mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_RIGHT);
                    break;
                case R.id.fromBottomRb:
                    mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_BOTTOM);
                    break;
            }
        }
    }*/
}

