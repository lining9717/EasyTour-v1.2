package com.example.lining.easytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TouristActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ListView lv_list_postpaper;

    private String[] url = new String[]{"http://blog.sina.com.cn/s/blog_67e205130102vg7g.html?tj=1",
    "http://blog.sina.com.cn/s/blog_6980a1350102vn87.html?tj=1","http://blog.sina.com.cn/s/blog_485c50390102wyv2.html?tj=1"};
    private String[] title = new String[]{"你所不知道的小清新天堂——泰国拜县","维拉蔓豪vilamendhoo岛：我的完美海岛度假","罗维尼：不止有亚德里亚海上最美的夕阳"};
    private int[] images = new int[]{R.drawable.n1, R.drawable.n2, R.drawable.n3};

    private String username;
    private String introduce;
    private String tel;
    private TextView tv_name;
    private TextView tv_intro;
    private TextView tv_tel;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        introduce = intent.getStringExtra("introduce");
        tel = intent.getStringExtra("tel");

        navigationView = findViewById(R.id.nav_view);
        View navigationview = navigationView.getHeaderView(0);
        tv_name = navigationview.findViewById(R.id.tv_guide_name);
        tv_intro = navigationview.findViewById(R.id.tv_intro);
        tv_tel = navigationview.findViewById(R.id.tv_tel);

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
            super.onBackPressed();
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
            intent.setClass(TouristActivity.this, QueryActivity.class);
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
            startActivity(intent);
        } else if (id == R.id.nav_quite) {
            Intent intent = new Intent(TouristActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
