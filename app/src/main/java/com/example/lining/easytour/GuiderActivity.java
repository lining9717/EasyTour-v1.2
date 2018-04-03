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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GuiderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ListView lv_list_postpaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        MyMainArrayAdapter adapter = new MyMainArrayAdapter(GuiderActivity.this,0,getPostPaperData());
        lv_list_postpaper.setAdapter(adapter);
    }

    private List<Postpaper> getPostPaperData() {
        List<Postpaper> list= new ArrayList<Postpaper>();
        int[] images = new int[]{R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5};
        for(int i=0;i<5;i++)
        {
            Postpaper p = new Postpaper(images[i],"Description"+Integer.toString(i));
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view order_item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_qurryorder) {
            Intent intent = new Intent();
            intent.setClass(GuiderActivity.this,QurryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sendorder) {
            Intent intent = new Intent();
            intent.setClass(GuiderActivity.this,SendOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_message) {
            Intent intent = new Intent();
            intent.setClass(GuiderActivity.this,MessageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent();
            intent.setClass(GuiderActivity.this,TouristSettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
