package com.example.lining.easytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
/**
 * to put the information of messages into the list view
 *
 */

public class MessageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ListView mLvMessage = findViewById(R.id.message_listview);
        MessageItemAdapter adapter = new MessageItemAdapter(MessageActivity.this,0,getMessageData());
        mLvMessage.setAdapter(adapter);
        mLvMessage.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MessageActivity.this,TalkActivity.class);
        startActivity(intent);
    }

    private List<Message> getMessageData() {
        List<Message> list= new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            Message p = new Message(R.drawable.icon,"杨云开","小杨同学为你服务", "2018/3/" +i);/*changed the information of messages*/
            list.add(p);
        }
        return list;
    }
}
