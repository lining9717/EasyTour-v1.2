package com.example.lining.easytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24.
 */

public class MyArrayAdapter extends ArrayAdapter<Order> {
    private int resoureId;
    private List<Order> objects;
    private Context context;


    public MyArrayAdapter(Context context, int resourceId, List<Order> objects) {
        super(context, resourceId, objects);
        // TODO Auto-generated constructor stub
        this.objects=objects;
        this.context=context;
    }

    private static class ViewHolder
    {
        ImageView imageView;
        TextView title;
        TextView content;
        TextView date;
        TextView time;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public Order getItem(int position) {
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
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater mInflater=LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.order_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.order_item_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.order_item_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.order_item_content);
            viewHolder.date = (TextView) convertView.findViewById(R.id.order_item_date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.order_item_days);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = objects.get(position);
        if(null!=order)
        {
            viewHolder.imageView.setBackgroundResource(order.getPicture());
            viewHolder.title.setText(order.getTitle());
            viewHolder.content.setText(order.getContent());
            viewHolder.time.setText(order.getTime());
            viewHolder.date.setText(order.getDate());
        }

        return convertView;
    }

}
