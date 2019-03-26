package com.example.annuairegsh.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import androidx.cardview.widget.CardView;
import io.realm.RealmList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annuairegsh.Activity.DepartmentActivity;
import com.example.annuairegsh.Activity.ListContactActivity;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class DepartmentAdapter extends ArrayAdapter<Department> implements View.OnClickListener{

    private RealmList<Department> dataSet;
    private Context mContext;
    private Activity activity;

    // View lookup cache
    private static class ViewHolder {
        TextView code, description;
        ImageView image;
        CardView cardView;
    }



    public DepartmentAdapter(RealmList<Department> data, Context context, Activity activity) {
        super(context, R.layout.item_department, data);
        this.dataSet = data;
        this.mContext=context;
        this.activity = activity;

    }


    @Override
    public void onClick(View v) {

       /* int position=(Integer) v.getTag();
        Object object= getItem(position);
        Department dataModelBarcode =(Department)object;




        switch (v.getId()) {

            case R.id.item_info:
                /*               MainActivity.barcodeDisplay.remove(MainActivity.barcodeDisplay.indexOf(dataModelBarcode.code));*/
            /*   Snackbar.make(v, " " + dataModelBarcode.code+ " deleted ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }
        }*/

    }

    private int lastPosition = -1;

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Department getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Department userModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_department, parent, false);
            viewHolder.code = (TextView) convertView.findViewById(R.id.code);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.cardView = convertView.findViewById(R.id.cardview2);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;


        viewHolder.code.setText(userModel.getCode().toUpperCase());
        viewHolder.description.setText(userModel.getDescription());
       // Glide.with(getContext()).load(userModel.getImageUrl()).into(viewHolder.image);
        int picId = mContext.getResources().getIdentifier(userModel.getCode().toLowerCase(), "drawable", mContext.getPackageName());

        if (picId != 0) {
            viewHolder.image.setImageResource(picId);
            //Glide.with(mContext).load(picId).into(holder.imageC);

        } else {
            //holder.imageC.setImageResource(C0267R.drawable.ic_def_image_factory);
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Message message = new Message();
                message.what = Constant.CONTACT_FETCH;
                message.obj = userModel.getCode();
                DepartmentActivity.handler.sendMessage(message);*/

                Intent intent = new Intent(mContext, ListContactActivity.class);
                intent.putExtra("department", userModel.getCode());
                intent.putExtra("company", DepartmentActivity.company);
                intent.putExtra("city", DepartmentActivity.city.getCode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //mContext.startActivity(intent);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
            }
        });


        //  viewHolder.info.setOnClickListener(this);
        // viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}

