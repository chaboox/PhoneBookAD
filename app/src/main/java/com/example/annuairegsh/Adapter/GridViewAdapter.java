package com.example.annuairegsh.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.annuairegsh.Activity.CityActivity;
import com.example.annuairegsh.Activity.HomeActivity;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.R;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.MyViewHolder> {

    private Context mContext ;
    private Activity activity;
    private List<Company> mData ;
    public static int cpt =0;


    public GridViewAdapter(Context mContext, List<Company> mData, Activity activity) {
        this.mContext = mContext;
        this.mData = mData;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.company_cardview,parent,false);
        return new MyViewHolder(view);
    }

    //Added to try solve the lag
    @Override public long getItemId(int position) { return position; }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(mData.get(position).getNameAD());
        holder.imageC.setImageResource(mData.get(position).getImage());

        int picId = mContext.getResources().getIdentifier(mData.get(position).getNameAD().toLowerCase().replace('-', '_').replace(' ', '_'), "drawable", mContext.getPackageName());

        if (picId != 0) {
            holder.imageC.setImageResource(picId);
            //Glide.with(mContext).load(picId).into(holder.imageC);
            Log.d("CPT", "onBindViewHolder: " + cpt++);
        } else {
            //holder.imageC.setImageResource(C0267R.drawable.ic_def_image_factory);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Message message = new Message();
                message.what = Constant.GET_CITY;
                message.obj = mData.get(position).getNameAD();
                Log.d("ADD", "onClick: " + (String)  message.obj);*/

                Intent intent = new Intent(mContext, CityActivity.class);
                intent.putExtra("company",mData.get(position).getNameAD());
               // mContext.startActivity(intent);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);

               // HomeActivity.handler.sendMessage(message);

              /*  Intent intent = new Intent(mContext,Book_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);*/

            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageC;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title) ;
            imageC = (ImageView) itemView.findViewById(R.id.image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
