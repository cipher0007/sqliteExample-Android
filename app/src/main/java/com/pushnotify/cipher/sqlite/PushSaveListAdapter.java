package com.pushnotify.cipher.sqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class PushSaveListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<pushsave> pushlist;

    public PushSaveListAdapter(Context context, int layout, ArrayList<pushsave> pushlist) {
        this.context = context;
        this.layout = layout;
        this.pushlist = pushlist;
    }

    @Override
    public int getCount() {
        return pushlist.size();
    }

    @Override
    public Object getItem(int position) {
        return pushlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txttitle, txtmessage;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txttitle = (TextView) row.findViewById(R.id.txttitle);
            holder.txtmessage = (TextView) row.findViewById(R.id.txtmessage);
            holder.imageView = (ImageView) row.findViewById(R.id.imgFood);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        pushsave pushsave = pushlist.get(position);

        holder.txttitle.setText(pushsave.gettitle());
        holder.txtmessage.setText(pushsave.getmessage());

        byte[] foodImage = pushsave.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
