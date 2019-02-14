package com.example.annuairegsh.Adapter;

import android.content.Context;
import android.os.Message;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.annuairegsh.Activity.CityActivity;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<City> implements View.OnClickListener{

    private ArrayList<City> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView name, description;
        ImageView image;
        CardView cardView;
        LinearLayout linearLayout;
    }



    public CityAdapter(ArrayList<City> data, Context context) {
        super(context, R.layout.item_department, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        City dataModelBarname =(City)object;




        switch (v.getId()) {

         /*   case R.id.item_info:
                /*               MainActivity.barnameDisplay.remove(MainActivity.barnameDisplay.indexOf(dataModelBarname.name));*/
            /*   Snackbar.make(v, " " + dataModelBarname.name+ " deleted ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }*/
        }

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
    public City getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final City userModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CityAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;


        if (convertView == null) {


            viewHolder = new CityAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.city_item, parent, false);
           viewHolder.name = (TextView) convertView.findViewById(R.id.name_city);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.cardView = convertView.findViewById(R.id.cardview);
            viewHolder.linearLayout = convertView.findViewById(R.id.linear);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CityAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
     //
        //  Log.d("VVIEW", "getView: " + CityActivity.listView.getHeight());
        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

       // viewHolder.cardView.setMinimumHeight(CityActivity.listView.getHeight()/CityActivity.cities.getCities().size());



       // viewHolder.linearLayout.setMinimumHeight(CityActivity.listView.getHeight()/CityActivity.cities.getCities().size());

        viewHolder.name.setText(userModel.getName());
        // Glide.with(getContext()).load(userModel.getImageUrl()).into(viewHolder.image);
       // viewHolder.cardView.setBackgroundResource(R.drawable.sba);
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
                Message message = new Message();
                message.what = Constant.GET_CITY;
                message.obj = userModel.getCode();
                CityActivity.handler.sendMessage(message);
            }
        });

        //  viewHolder.info.setOnClickListener(this);
        // viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}


