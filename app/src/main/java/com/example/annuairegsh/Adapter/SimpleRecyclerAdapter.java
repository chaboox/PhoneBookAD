package com.example.annuairegsh.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Message;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annuairegsh.Activity.HomeActivity;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {
    private Context mContext ;
    private List<Contact> mData ;

    public SimpleRecyclerAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
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

        // byte[] b = s.getBytes();
       // Log.d("PIC", "onBindViewHolder: " + mData.get(position).getName() + "     LL:" + );

        String pic =  mData.get(position).getPictureC();
        if(!pic.equals("null")) {
            Bitmap bitmap = decodeSampleBitmap(Base64.decode(pic, Base64.DEFAULT), 60, 60);
            holder.imageView.setImageBitmap(bitmap);
        }

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

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text);
            job = itemView.findViewById(R.id.job);
            imageView = itemView.findViewById(R.id.image);
        }
    }
    public static Bitmap decodeSampleBitmap(byte[] bitmap, int reqHeight, int reqWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}