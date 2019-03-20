package com.example.annuairegsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annuairegsh.Activity.ContactDetailActivity;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Manager.RealmManager;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.annuairegsh.Manager.PictureDecodeManager.decodeSampleBitmap;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {
    private Context mContext ;
    private List<Contact> mData ;
    private ArrayList<Contact> saveData ;
    private HashMap<String, ViewHolder> holderHashMap;
    private Handler handler;

    public ContactAdapter(Context mContext, List<Contact> mData) throws UnsupportedEncodingException {
        handler = new HandlerContact();
        holderHashMap = new HashMap<>();
        this.mContext = mContext;
        this.mData = mData;
        this.saveData = new ArrayList<>(mData);
        ArrayList<String> ids = new ArrayList<>();
        for( Contact c : mData){
            String pic =  c.getPictureC();
            if (pic == null) pic = "null";
            if(pic .equals("none")){
              //  holder.imageView.setImageResource(R.drawable.user);
               // Log.d("NONE", "onBindViewHolder: " + mData.get(position).getName() );
            }
            else {


                if (!pic.equals("null")) {
                    //Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
                  //  holder.imageView.setImageBitmap(bitmap);
                } else {
                    ids.add(c.getId());
                }
            }
        }
       // API_Manager.getPicsByIds(ids, mContext, handler, Constant.CONTACT);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holderHashMap.put( holder.toString().substring(11, 18), holder);
        holder.name.setText(mData.get(position).getName());
        String job = mData.get(position).getDescription();
        if(job.equals("null"))
            job ="";
        holder.job.setText(job);
        holder.id = mData.get(position).getId();

        Log.d("HOLDER", "onBindViewHolder: " + holder.toString().substring(11, 18) + holder.toString());
        //holder.imageView.setImageResource(R.drawable.user);

        // byte[] b = s.getBytes();
       // Log.d("PIC", "onBindViewHolder: " + mData.get(position).getName() + "     LL:" + );

        String pic =  mData.get(position).getPictureC();
        if (pic == null) pic = "null";
        if(pic .equals("none")){
            holder.imageView.setImageResource(R.drawable.user);
            Log.d("NONE", "onBindViewHolder: " + mData.get(position).getName() );
        }
        else {


            if (!pic.equals("null")) {
                Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
                holder.imageView.setImageBitmap(bitmap);
            } else {
                Log.d("DKHALE", "onBindViewHolder: " + pic);
                holder.imageView.setImageResource(R.drawable.user);
                holderHashMap.put( holder.toString().substring(11, 18), holder);
                try {
                    API_Manager.getPicById(mData.get(position).getId(), mContext, holder.imageView, (mData.get(position)), holder.toString().substring(11, 18));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContactDetailActivity.class);
                //intent.putExtra("contact", mData.get(position));
                intent.putExtra("id", mData.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        //holder.job.setImageResource(mData.get(position).getImage());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return mData.get(position).getName().substring(0,1);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, job;
        public ImageView imageView;
        public CardView cardView;
        public String id;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text);
            job = itemView.findViewById(R.id.job);
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardview2);
        }
    }
    public void filter(String charText) {

        charText = charText.toLowerCase();
        mData = null;
        mData = new ArrayList<>();
        if (charText.length() == 0) {
            mData = new ArrayList<>(saveData);
        } else {
            for (Contact contact : saveData) {
                if (contact.getName().toLowerCase()
                        .contains(charText)) {
                    mData.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class HandlerContact extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.CONTACT:
                    for(ViewHolder viewHolder: holderHashMap.values()) {

                        String pic = RealmManager.getContactbyId(viewHolder.id).getPictureC();
                        if (pic == null) pic = "null";
                        if(pic .equals("none")){
                            viewHolder.imageView.setImageResource(R.drawable.user);
                        //    Log.d("NONE", "onBindViewHolder: " + mData.get(position).getName() );
                        }
                        else {


                            if (!pic.equals("null")) {
                                Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
                                viewHolder.imageView.setImageBitmap(bitmap);
                            } else {

                /*try {
                    API_Manager.getPicById(mData.get(position).getId(), mContext, holder.imageView, (mData.get(position)), holder.toString().substring(11, 18));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                            }
                        }

                    }
                    break;
                case Constant.DEPARTMENT:

                    break;
            }
        }}

}