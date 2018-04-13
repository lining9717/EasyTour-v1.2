package com.example.lining.easytour.tourist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lining.easytour.Refresh.GuideMainRefreshableView;
import com.example.lining.easytour.guide.GuideActivity;
import com.example.lining.easytour.util.BoardActivity;
import com.example.lining.easytour.util.Postpaper;
import com.example.lining.easytour.R;
import com.example.lining.easytour.adapter.TouristArrayAdapter;
import com.example.lining.easytour.chat.MessageActivity;
import com.example.lining.easytour.login.Login;
import com.example.lining.easytour.guide.GuideQueryActivity;
import com.example.lining.easytour.orders.SendOrderActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TouristActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener, GestureDetector.OnGestureListener {
    private DrawerLayout drawerLayout;
    private ListView lv_list_postpaper;

    private String[] url = new String[]{"http://blog.sina.com.cn/s/blog_67e205130102vg7g.html?tj=1",
    "http://blog.sina.com.cn/s/blog_6980a1350102vn87.html?tj=1","http://blog.sina.com.cn/s/blog_485c50390102wyv2.html?tj=1"};
    private String[] title = new String[]{"你所不知道的小清新天堂——泰国拜县","维拉蔓豪vilamendhoo岛：我的完美海岛度假","罗维尼：不止有亚德里亚海上最美的夕阳"};
    private int[] images = new int[]{R.drawable.n1, R.drawable.n2, R.drawable.n3};

    private String username;
    private String introduce;
    private String tel;
    private String photo;
    private TextView tv_name;
    private TextView tv_intro;
    private TextView tv_tel;
    private ImageView iv_headPhoto;
    private NavigationView navigationView;
    private final static int REQUESTCODE_FOR_UPDATE = 1; // 返回的结果码
    private GuideMainRefreshableView guideMainRefreshableView;
    private GestureDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        introduce = intent.getStringExtra("introduce");
        tel = intent.getStringExtra("tel");
        photo = intent.getStringExtra("photo");

        navigationView = findViewById(R.id.nav_view);
        View navigationview = navigationView.getHeaderView(0);
        iv_headPhoto = navigationview.findViewById(R.id.tv_photo);
        tv_name = navigationview.findViewById(R.id.tv_guide_name);
        tv_intro = navigationview.findViewById(R.id.tv_intro);
        tv_tel = navigationview.findViewById(R.id.tv_tel);

        Picasso.with(TouristActivity.this).load(photo).into(iv_headPhoto);   //获取头像
        tv_name.setText(username+"");
        tv_intro.setText(introduce+"");
        tv_tel.setText(tel+"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv_list_postpaper = (ListView) findViewById(R.id.tourist_listview);/*changed the name of tourist list view*/
        TouristArrayAdapter adapter = new TouristArrayAdapter(TouristActivity.this, 0, getPostPaperData());
        lv_list_postpaper.setAdapter(adapter);
        lv_list_postpaper.setOnItemClickListener(this);

        //下拉刷新
        guideMainRefreshableView = (GuideMainRefreshableView) findViewById(R.id.tourist_content_rf);
        guideMainRefreshableView.listView = (ListView) findViewById(R.id.tourist_listview);
        guideMainRefreshableView.setOnRefreshListener(new GuideMainRefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    ListViewDataUpdate();/*更新列表数据*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                guideMainRefreshableView.finishRefreshing();
            }
        }, 0);

        detector = new GestureDetector(this);

    }
    /*
     * 点击后退提示是否退出窗口
     * */
    private void showExitDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Promoting")
                .setMessage("Are you sure to exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TouristActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void ListViewDataUpdate() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TouristActivity.this, BoardActivity.class);
        intent.putExtra("url",url[position]);
        startActivity(intent);

    }

    private List<Postpaper> getPostPaperData() {
        List<Postpaper> list = new ArrayList<Postpaper>();

        for (int i = 0; i < url.length; i++) {
            Postpaper p = new Postpaper(images[i], title[i], url[i]);
            list.add(p);
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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

        if (id == R.id.nav_qurryorder) {
            Intent intent = new Intent();
            intent.setClass(TouristActivity.this, TouristQueryActivity.class);
            intent.putExtra("touristname",username);
            startActivity(intent);
        } else if (id == R.id.nav_sendorder) {
            Intent intent = new Intent();
            intent.setClass(TouristActivity.this,SendOrderActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else if (id == R.id.nav_message) {
            Intent intent = new Intent();
            intent.setClass(TouristActivity.this, MessageActivity.class);
            startActivity(intent);
        }
            else if (id == R.id.nav_setting) {
            Intent intent = new Intent();
            intent.setClass(TouristActivity.this, TouristSettingActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("intro",introduce);
            intent.putExtra("mPath",photo);
            intent.putExtra("tel",tel);
            startActivityForResult(intent,REQUESTCODE_FOR_UPDATE);
        } else if (id == R.id.nav_quite) {
            Intent intent = new Intent(TouristActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0)
            return;
        if (resultCode == 1) {
            if (requestCode == REQUESTCODE_FOR_UPDATE) {
                String newname = data.getStringExtra("newname");
                String newtel = data.getStringExtra("newtel");
                String newintro = data.getStringExtra("newintro");
                String newPhotopath = data.getStringExtra("newServerPath");
                username = newname;
                tel = newtel;
                introduce = newintro;
                photo = newPhotopath;
                tv_name.setText(newname + "");
                tv_intro.setText(newintro + "");
                tv_tel.setText(newtel + "");
                Picasso.with(TouristActivity.this).load(photo).into(iv_headPhoto);   //获取头像
            }
        }
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE_FOR_UPDATE) {
                String newname = data.getStringExtra("newname");
                String newtel = data.getStringExtra("newtel");
                String newintro = data.getStringExtra("newintro");
                username = newname;
                tel = newtel;
                introduce = newintro;
                tv_name.setText(newname + "");
                tv_intro.setText(newtel + "");
                tv_tel.setText(newintro + "");
            }
        }
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
}
