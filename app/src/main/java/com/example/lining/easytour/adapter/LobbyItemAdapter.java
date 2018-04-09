package com.example.lining.easytour.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lining.easytour.R;

import java.util.List;

/**
 * Created by lining on 2018/3/24.
 */

public class LobbyItemAdapter extends ArrayAdapter<LobbyItem> {
    private int resource;
    public LobbyItemAdapter(@NonNull Context context, int resource, @NonNull List<LobbyItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LobbyItem lobby_item = getItem(position);
        View view;
        LobbyViewHolder view_holder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
            view_holder = new LobbyViewHolder();
            view_holder.title = view.findViewById(R.id.order_item_title);
            view_holder.content = view.findViewById(R.id.order_item_content);
            view_holder.date = view.findViewById(R.id.order_item_date);
            view_holder.day = view.findViewById(R.id.order_item_days);
            view_holder.icon = view.findViewById(R.id.order_item_image);
            view.setTag(view_holder);
        }else{
            view = convertView;
            view_holder = (LobbyViewHolder)view.getTag();
        }
        view_holder.title.setText(lobby_item.getTitle());
        view_holder.content.setText(lobby_item.getContent());
        view_holder.day.setText(lobby_item.getDay()+"天");
        view_holder.date.setText(lobby_item.getDate());
        view_holder.icon.setImageResource(R.drawable.logotemp);
        return view;
    }
}
