package com.example.taehoon.planmaker_v2.Newbook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Data.Request;
import com.example.taehoon.planmaker_v2.Data.Response;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.MainBase.CustomViewPager;
import com.example.taehoon.planmaker_v2.R;

import java.text.ParseException;
import java.util.ArrayList;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.client;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment01.setUsers;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment02.getRequestData;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment02.r_toptime;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment03.response_adapter;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment04.adapter;

/**
 * Created by TAEHOON on 2017-05-18.
 */

public class MakePlanActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private MakingTabPageAdapter pagerAdapter;

    private ImageButton back, next;

    public static ArrayList<String> selected_item = new ArrayList<>();


    public static ArrayList<Response> responseList = new ArrayList<>();
    public static Response response = new Response();

    public static String title;
    public static String place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeplan);

        setViewPager();

        back = (ImageButton) findViewById(R.id.makeButtonBack);
        next = (ImageButton) findViewById(R.id.makeButton);
        back.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int position = tabLayout.getSelectedTabPosition();
        switch (v.getId()) {
            case R.id.makeButton:
                if(position==0){
                    setUsers();
                }
                if (position == 1) {
                    if(r_toptime.getText().toString().equals("시간을 선택하세요.")){
                        Toast.makeText(getApplicationContext(), " 시간을 선택하세요. ", Toast.LENGTH_SHORT).show();
                    }else {

                        sendRequest();
                        pagerAdapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(position + 1);
                    }
                } else if (position != 3) {
                    if(position==2){
                        pagerAdapter.notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                    viewPager.setCurrentItem(position + 1);
                } else {
                    makePlan();
                    finish();
                }
                break;
            case R.id.makeButtonBack:
                if (position != 0) {
                    viewPager.setCurrentItem(position - 1);
                } else {
                    finish();
                }
                break;
        }
    }

    private void sendRequest() {
        Request r = getRequestData();
        responseList = client.getResponseList(r);
        place = client.getPlace();
    }

    private void setViewPager() {
        //탭, 뷰페이저
        tabLayout = (TabLayout) findViewById(R.id.makingtab);
        tabLayout.addTab(tabLayout.newTab().setText("그룹 생성"));
        tabLayout.addTab(tabLayout.newTab().setText("조건"));
        tabLayout.addTab(tabLayout.newTab().setText("추천"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = getViewPager();
        pagerAdapter = new MakingTabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(true);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

    }

    public CustomViewPager getViewPager() {
        if (null == viewPager) {
            viewPager = (CustomViewPager) findViewById(R.id.makingviewpager);
        }
        return viewPager;
//        In Fragment: 원하는 탭(프래그먼트)로 이동
//        ((YourActivitName)getActivity()).getViewPager().setCurrentItem(index);
    }



    public static String getPlace(){
        return place;
    }

    public void makePlan() {
        MyPlan myPlan = null;
        try {
            final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MYPLAN.db", null, 1);
            myPlan = new MyPlan(dbHelper.getPrimaryKey(), title, 0, response.getStartTime(), response.getEndTime(), 0, response.getEndTime(), place, 0, "", 3);

            dbHelper.insertRepeat(myPlan);
        } catch (ParseException | CloneNotSupportedException e) {
            e.printStackTrace();
        }

        client.makePlan(myPlan, selected_item);


    }
}
