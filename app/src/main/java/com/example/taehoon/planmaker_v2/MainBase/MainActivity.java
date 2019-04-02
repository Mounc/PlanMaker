package com.example.taehoon.planmaker_v2.MainBase;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity;
import com.example.taehoon.planmaker_v2.Newbook.NewPlanActivity;
import com.example.taehoon.planmaker_v2.Others.BackPressCloseHandler;
import com.example.taehoon.planmaker_v2.Others.Broadcast;
import com.example.taehoon.planmaker_v2.Others.LoginScreenActivity;
import com.example.taehoon.planmaker_v2.R;
import com.example.taehoon.planmaker_v2.Socket.Clienton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_CODE_ADDPLAN = 1;
    public static final int RESULT_CODE_LOGIN = 2;
    public static final int RESULT_CODE_FINISH = 3;

    //서버와 통신을 위한 소켓
    public static Clienton client = null;
    //    public static String server_ip = "10.0.2.2"; //"58.126.52.79"
    public static String server_ip = "58.126.52.79"; //"58.126.52.79"
    public static int server_port = 7777;

    //일정, id
    public static MyPlan onPlan = new MyPlan();
    public static Date selectDay = null;
    public static String userid = "empty";

    //주메뉴 뷰페이저-어댑터
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TabPageAdapter pagerAdapter;

    //뒤로가기 종료 방지
    private BackPressCloseHandler bh;

    //사이드메뉴
    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private ListView lvNavList;
    private FrameLayout flContainer;
    private String[] navItems = {"로그인 정보", "로그아웃", "캘린더 보기", "개인 일정 추가", "그룹 일정 추가", "일정 내보내기", "일정 가져오기"};

    //바텀메뉴
    private ImageButton btn_bottom_add;
    private ImageButton btn_bottom_meeting;
    private ImageButton btn_bottom_setting;
    private ImageButton btn_bottom_search;

    public static double latitude = 1, longitude = 1;


    @Override
    public void finish() {
        try {
            notification();
//            client.syncToServer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            client = new Clienton(new Socket(server_ip, server_port), getApplicationContext());
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        login();


        FirebaseInstanceId.getInstance().getToken();
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) Log.d("FCM_Token", token);


        try {
            notification();//알림설정
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bh = new BackPressCloseHandler(this);//뒤로가기 2번 종료
        setViewPager();
        setSideMenu();
        setBottomMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 권한 허가
                        // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    new TedPermission(this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                            .check();
                 } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }


        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void sendPoint() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location l = new Location(LocationManager.GPS_PROVIDER);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
            l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }




//        lm.removeUpdates(locationListener);
        System.out.println("Point : " + latitude + " / " + longitude);
        client.sendPoint(latitude, longitude);
    }

    private void setViewPager() {
        //탭, 뷰페이저
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("First"));
        tabLayout.addTab(tabLayout.newTab().setText("Second"));
        tabLayout.addTab(tabLayout.newTab().setText("Third"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = getViewPager();
        pagerAdapter = new TabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(false);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        tabLayout.setVisibility(View.GONE);
    }

    private void setSideMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //사이드메뉴
        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (FrameLayout) findViewById(R.id.container);
        lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://로그인 정보
                        break;
                    case 1://로그아웃
                        logout();
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                    case 2://캘린더보기
                        viewPager.setCurrentItem(0);
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                    case 3://개인 일정 추가
                        addPlan();
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                    case 4://그룹 일정 추가
                        makePlan();
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                    case 5://일정 내보내기
                        client.syncToServer();
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                    case 6://일정 가져오기
                        client.syncFromServer();
                        dlDrawer.closeDrawer(lvNavList);
                        break;
                }
                pagerAdapter.notifyDataSetChanged();
            }
        });

        dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer,
                toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        dlDrawer.setDrawerListener(dtToggle);
    }

    private void logout() {
        SharedPreferences pref_id = getSharedPreferences("PrefId", MODE_PRIVATE);
        SharedPreferences pref_pass = getSharedPreferences("PrefPassword", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref_id.edit();
        SharedPreferences.Editor editor2 = pref_pass.edit();

        editor.putString("my_preference", "logout");
        editor2.putString("my_preference", "logout");
        editor.commit();
        editor2.commit();
        login();
    }

    private void setBottomMenu() {
        //바텀메뉴
        btn_bottom_add = (ImageButton) findViewById(R.id.btn_bottom_add);
        btn_bottom_meeting = (ImageButton) findViewById(R.id.btn_bottom_meeting);
        btn_bottom_setting = (ImageButton) findViewById(R.id.btn_bottom_setting);
        btn_bottom_search = (ImageButton) findViewById(R.id.btn_bottom_search);
        btn_bottom_add.setOnClickListener(this);
        btn_bottom_meeting.setOnClickListener(this);
        btn_bottom_setting.setOnClickListener(this);
        btn_bottom_search.setOnClickListener(this);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public CustomViewPager getViewPager() {
        if (null == viewPager) {
            viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        }
        return viewPager;
//        In Fragment: 원하는 탭(프래그먼트)로 이동
//        ((YourActivitName)getActivity()).getViewPager().setCurrentItem(index);
    }

    public void notification() throws ParseException {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MYPLAN.db", null, 1);

        ArrayList<MyPlan> items = dbHelper.selectAllPlan();
        ArrayList<AlarmManager> amList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNoticeTime() != 0) {
                Date time = items.get(i).getStartTime();
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent intent = new Intent(MainActivity.this, Broadcast.class);
                PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(time.getYear(), time.getMonth(), time.getDate(), time.getHours(), time.getMinutes());
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

                amList.add(am);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            bh.onBackPressed();
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_CODE_ADDPLAN:
                break;
            case RESULT_CODE_LOGIN:
                onResume();
                userid = data.getStringExtra("id");
                navItems[0] = "로그인 ID : " + userid;
                sendPoint();
//                client.syncFromServer();
                break;
            case RESULT_CODE_FINISH:
                Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
                finish();
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bottom_add:
                addPlan();
                break;
            case R.id.btn_bottom_meeting:
                makePlan();
                break;
            case R.id.btn_bottom_setting:
                viewPager.setCurrentItem(0, true);
                setCalendarModeMenu(v);
                break;
            case R.id.btn_bottom_search:
                viewPager.setCurrentItem(2, true);
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivityForResult(intent, RESULT_CODE_LOGIN);
    }

    private void addPlan() {
        Intent intent = new Intent(this, NewPlanActivity.class);
        intent.putExtra("isEdit", false);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void makePlan() {
        Intent intent = new Intent(this, MakePlanActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void setCalendarModeMenu(View v) {

    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            System.out.println("LocationListener On");

            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


}
