package com.example.lining.easytour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lining.easytour.R;
import com.example.lining.easytour.orders.Order;
import com.example.lining.easytour.orders.OrderHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24.
 */

public class QueryArrayAdapter extends ArrayAdapter<Order> {
    private List<Order> objects;
    private Context context;
    private int resource;

    public QueryArrayAdapter(Context context, int resource, List<Order> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }


    public void setObjects(List<Order> objects) {
        this.objects = objects;
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
        OrderHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new OrderHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(resource, null);
            viewHolder.setImageView((ImageView) convertView.findViewById(R.id.order_item_image));
            viewHolder.setTitle((TextView) convertView.findViewById(R.id.order_item_title));
            viewHolder.setContent((TextView) convertView.findViewById(R.id.order_item_content));
            viewHolder.setDate((TextView) convertView.findViewById(R.id.order_item_date));
            viewHolder.setTime((TextView) convertView.findViewById(R.id.order_item_days));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderHolder) convertView.getTag();
        }

        Order order = objects.get(position);
        if (null != order) {
            viewHolder.getImageView().setBackgroundResource(order.getmImage());
            viewHolder.getTitle().setText(order.getmPlace());
            viewHolder.getContent().setText(order.getmPlaceDescrible());
            viewHolder.getTime().setText(order.getmDays());
            viewHolder.getDate().setText(order.getmDate());
        }

        return convertView;
    }

}
