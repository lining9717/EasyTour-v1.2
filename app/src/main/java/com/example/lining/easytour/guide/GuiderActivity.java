package com.example.lining.easytour.guide;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.lining.easytour.adapter.QueryArrayAdapter;
import com.example.lining.easytour.orders.Order;
import com.example.lining.easytour.util.BoardActivity;
import com.example.lining.easytour.chat.MessageActivity;
import com.example.lining.easytour.orders.QueryActivity;
import com.example.lining.easytour.R;
import com.example.lining.easytour.adapter.SpinnerAdapter;
import com.example.lining.easytour.login.Login;
import com.example.lining.easytour.orders.OrderActivity;
import com.example.lining.easytour.util.SerializableHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lining.easytour.util.ToolUtil.daysBetween;

public class GuiderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener, GestureDetector.OnGestureListener, View.OnTouchListener {
    private ListView orders_list_view;
    private float startX;
    private ViewFlipper viewFlipper;
    private int order;
    private float endX;
    private String[] url = new String[]{"http://blog.sina.com.cn/s/blog_16168caaf0102wc09.html",
            "http://blog.sina.com.cn/s/blog_73be7ad10102xbcv.html",
            "http://blog.sina.com.cn/s/blog_66a5e8990100i9as.html"};
    private int[] image = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3};
    private String[] title = new String[]{"苏州旅游注意事项，老游客总结的经验！", "急救知识学习", "导游服务规范"};
    private ViewHolder viewHolder;
    private NavigationView navigationView;
    private TextView tv_name;
    private TextView tv_intro;
    private TextView tv_tel;
    private TextView tv_place;
    private String name;
    private String intro;
    private String tel;
    private String place;
    private GestureDetector detector;
    private List<Map<String,String>> orderResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Intent intent = getIntent();
        name = intent.getStringExtra("guidername");
        intro = intent.getStringExtra("introduce");
        tel = intent.getStringExtra("tel");
        place = intent.getStringExtra("place");
        if(place.equals("")){
            place = " ";
        }
        navigationView = findViewById(R.id.nav_view);
        View navigationview = navigationView.getHeaderView(0);
        tv_name = navigationview.findViewById(R.id.tv_guide_name);
        tv_intro = navigationview.findViewById(R.id.tv_intro);
        tv_tel = navigationview.findViewById(R.id.tv_tel);
        tv_place = navigationview.findViewById(R.id.tv_addr);
        tv_name.setText(name+"");
        tv_intro.setText(intro+"");
        tv_tel.setText(tel+"");
        tv_place.setText(place+"");
        orderResult = new ArrayList<>();
        new GetOrders().execute();
        detector = new GestureDetector(this);

    }



    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        orders_list_view = findViewById(R.id.guider_listView);
        QueryArrayAdapter orders_adapter = new QueryArrayAdapter(GuiderActivity.this,R.layout.order_item, generateListContent());
        orders_list_view.setAdapter(orders_adapter);
        viewFlipper = (ViewFlipper) findViewById(R.id.guider_vf_lobby);
        setViewFlipper(viewFlipper);
        viewFlipper.setOnTouchListener(this);
        orders_list_view.setOnItemClickListener(this);
    }




    //点击订单，跳转到详情页面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(GuiderActivity.this, OrderActivity.class);
        Map<String,String> map = orderResult.get(position);
        SerializableHashMap serializableHashMap = new SerializableHashMap();
        serializableHashMap.setMap((HashMap<String, String>)map);
        Bundle bundle = new Bundle();
        bundle.putSerializable("message",serializableHashMap);
        bundle.putString("guidername",name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //滑动广告
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startX = event.getX();

                break;
            case MotionEvent.ACTION_UP:

                endX = event.getX();
                order = viewFlipper.getDisplayedChild();

                if(order > 0 && endX > startX){// 查看前一页的广告

                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_right_out));
                    viewFlipper.showPrevious();
                }

                else if(order < url.length && endX < startX){// 查看后一页的广告

                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_left_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_right_out));
                viewFlipper.showNext();
                }
                break;
        }
        return true;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        viewFlipper.setAutoStart(false);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startX = event.getX();

                break;
            case MotionEvent.ACTION_UP:

                endX = event.getX();
                order = viewFlipper.getDisplayedChild();

                if(order > 0 && endX > startX){// 查看前一页的广告

                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_left_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_right_out));
                    viewFlipper.showPrevious();

                }

                else if(order < url.length && endX < startX){// 查看后一页的广告

                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_right_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                            R.anim.anim_left_out));
                    viewFlipper.showNext();
                }
                viewFlipper.setAutoStart(true);
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_right_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.anim_left_out));
                break;
        }
        return true;
    }


    private static class ViewHolder {
        TextView content;
    }

    private void setViewFlipper(ViewFlipper viewFlipper) {
        for (int i = 0; i < url.length; i++) {
            viewHolder = new ViewHolder();
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sight_item, null);
            viewHolder.content = (TextView) view.findViewById(R.id.tourist_list_tv_content);
            view.setTag(viewHolder);
            viewHolder.content.setText(title[i]);
            viewHolder.content.setBackgroundResource(image[i]);
            viewHolder.content.setHint(url[i]);
            viewFlipper.addView(view);
        }
    }

    public void initSpinner(Spinner spinner, List<String> data) {
        final List<String> datas = data;
        SpinnerAdapter spinner_adapter = new SpinnerAdapter(this);
        spinner.setAdapter(spinner_adapter);

        spinner_adapter.setDatas(datas);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar order_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view order_item clicks here.
        int id = item.getItemId();

        if (id == R.id.lobby) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else if (id == R.id.order) {
            Intent intent = new Intent(GuiderActivity.this, QueryActivity.class);
            intent.putExtra("guidename",name);
            startActivity(intent);
        }
        else if (id == R.id.message) {
            Intent intent = new Intent(GuiderActivity.this, MessageActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.setting) {
            Intent intent = new Intent(GuiderActivity.this, GuiderSettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.quite) {
            Intent intent = new Intent(GuiderActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void btnClickViewFlipper(View view) {
        order = viewFlipper.getDisplayedChild();
        Intent intent = new Intent(GuiderActivity.this, BoardActivity.class);
        intent.putExtra("url", url[order]);
        startActivity(intent);
    }


    private List<Order> generateListContent() {
        List<Order> list = new ArrayList<>();
        for (int i=0;i<orderResult.size();i++){
            Map<String,String> map = orderResult.get(i);
            String days = daysBetween(map.get("begin_day"),map.get("end_day"));
            Order order = new Order(R.drawable.logotemp,map.get("place"),map.get("place_descript"),map.get("begin_day"),days+"days");
            list.add(order);
        }
        return list;
    }

    private class GetOrders extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String uri = "http://118.89.18.136/EasyTour-bk/getorders.php/";
            StringBuilder result = new StringBuilder();
            HttpPost httpRequest = new HttpPost(uri);
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    for(String s=bufferedReader.readLine();s!=null;s=bufferedReader.readLine()){
                        result.append(s);
                    }
                }
                Log.i("GetOrders","result----------->"+result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            try {
                JSONObject jsonObject = new JSONObject(strings);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject itemObject = jsonArray.getJSONObject(i);
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("orderID",itemObject.getString("orderID"));
                    map.put("username",itemObject.getString("username"));
                    map.put("guidername",itemObject.getString("guidername"));
                    map.put("begin_day",itemObject.getString("begin_day"));
                    map.put("end_day",itemObject.getString("end_day"));
                    map.put("place",itemObject.getString("place"));
                    map.put("place_descript",itemObject.getString("place_descript"));
                    map.put("time_descript",itemObject.getString("time_descript"));
                    map.put("num_of_people",itemObject.getString("num_of_people"));
                    map.put("isDone",itemObject.getString("isDone"));
                    orderResult.add(map);
                }
                init();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
