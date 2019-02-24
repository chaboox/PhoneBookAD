package com.example.annuairegsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annuairegsh.Activity.ContactDetailActivity;
import com.example.annuairegsh.Manager.API_Manager;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

    public ContactAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.saveData = new ArrayList<>(mData);
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

        holder.name.setText(mData.get(position).getName());
        holder.job.setText(mData.get(position).getDescription());
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
                try {
                    API_Manager.getPicById(mData.get(position).getId(), mContext, holder.imageView, (mData.get(position)));
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

}