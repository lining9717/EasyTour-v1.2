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
 * Created by Administrator on 2018/4/1.
 * this is for the Adapter to hold the Message
 */

public class MessageItemAdapter extends ArrayAdapter<Message> {
    private int resoureId;
    private List<Message> objects;
    private Context context;

    public MessageItemAdapter(Context context, int resourceId, List<Message> objects) {
        super(context,resourceId,objects);
        // TODO Auto-generated constructor stub
        this.objects=objects;
        this.context=context;
    }

    private static class ViewHolder
    {
        ImageView mimage;
        TextView mcontent;
        TextView mtitle;
        TextView mtime;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public Message getItem(int position) {
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
        MessageItemAdapter.ViewHolder viewHolder = null;
        if(convertView==null)
        {
            viewHolder=new MessageItemAdapter.ViewHolder();
            LayoutInflater mInflater=LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.message_item, null);
            viewHolder.mimage = (ImageView) convertView.findViewById(R.id.mess_item_image);
            viewHolder.mtitle = (TextView) convertView.findViewById(R.id.mess_item_title);
            viewHolder.mcontent = (TextView) convertView.findViewById(R.id.mess_item_content);
            viewHolder.mtime = (TextView) convertView.findViewById(R.id.mess_item_date);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MessageItemAdapter.ViewHolder) convertView.getTag();
        }
        Message message = objects.get(position);
        if(null!=message)
        {
            viewHolder.mimage.setImageResource(message.getPicture());
            viewHolder.mtitle.setText(message.getTitle());
            viewHolder.mcontent.setText(message.getContent());
            viewHolder.mtime.setText(message.getTime());
        }

        return convertView;
    }
}
