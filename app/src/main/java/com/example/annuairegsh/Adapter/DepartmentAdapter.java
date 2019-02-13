package com.example.annuairegsh.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.R;

import java.util.ArrayList;

public class DepartmentAdapter extends ArrayAdapter<Department> implements View.OnClickListener{

    private ArrayList<Department> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView code, description;
        ImageView image;
    }



    public DepartmentAdapter(ArrayList<Department> data, Context context) {
        super(context, R.layout.item_department, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Department dataModelBarcode =(Department)object;




        switch (v.getId()) {

         /*   case R.id.item_info:
                /*               MainActivity.barcodeDisplay.remove(MainActivity.barcodeDisplay.indexOf(dataModelBarcode.code));*/
            /*   Snackbar.make(v, " " + dataModelBarcode.code+ " deleted ", Snackbar.LENGTH_LONG)
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
        Department userModel = getItem(position);
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

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;


        viewHolder.code.setText(userModel.getCode());
        viewHolder.description.setText(userModel.getDescription());
       // Glide.with(getContext()).load(userModel.getImageUrl()).into(viewHolder.image);
        int picId = mContext.getResources().getIdentifier(userModel.getCode().toLowerCase(), "drawable", mContext.getPackageName());

        if (picId != 0) {
            viewHolder.image.setImageResource(picId);
            //Glide.with(mContext).load(picId).into(holder.imageC);

        } else {
            //holder.imageC.setImageResource(C0267R.drawable.ic_def_image_factory);
        }


        //  viewHolder.info.setOnClickListener(this);
        // viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}
