package com.example.lining.easytour;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyMainArrayAdapter extends ArrayAdapter<Postpaper> {
    private int resoureId;
    private List<Postpaper> objects;
    private Context context;


    public MyMainArrayAdapter(Context context, int resourceId, List<Postpaper> objects) {
        super(context, resourceId, objects);
        // TODO Auto-generated constructor stub
        this.objects = objects;
        this.context = context;
    }

    private static class ViewHolder {
        TextView content;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public Postpaper getItem(int position) {
        // TODO Auto-generated method stub
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.signt, null);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tourist_list_tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Postpaper order = objects.get(position);
        if (null != order) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                viewHolder.content.setBackground(order.getPicture());
//            }
            viewHolder.content.setText(order.getmContent());
        }

        return convertView;
    }
}
