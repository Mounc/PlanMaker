package com.example.taehoon.planmaker_v2.MainBase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.Newbook.NewPlanActivity;
import com.example.taehoon.planmaker_v2.Others.TodayListViewAdapter;
import com.example.taehoon.planmaker_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.onPlan;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.selectDay;

/**
 * Created by TAEHOON on 2017-03-16.
 */

public class MyTabFragment01 extends Fragment {
    private ViewGroup view;
    private CalendarView calendarView;
    private ListView planListView;
    private ArrayList<MyPlan> todayPlans = new ArrayList<>();
    private ArrayList<String> items;
    private TodayListViewAdapter adapter;
    private TextView textToday;
    private ImageView imageView;
    public static final int REQUEST_CODE_NEWBOOK = 10 ;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = (ViewGroup)inflater.inflate(R.layout.mytabfragment01, container,false);

        //날짜 받아오기
        final SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy. MM. dd. ", Locale.KOREA );
        final Date currentTime = new Date ( );
        final String dTime = formatter.format ( currentTime );

        //데이터 준비
        items = new ArrayList<>();

        //어댑터 준비
        adapter = new TodayListViewAdapter();

        //어댑터 연결
        planListView = (ListView)view.findViewById(R.id.listView);
        planListView.setAdapter(adapter);

        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        calendarView.setDate(selectDay.getTime());
        calendarView.setOnDateChangeListener(calendarViewChangeListener);

        planListView.setOnItemClickListener(listViewItemClickListener);

        try {
            checkOnToday(calendarView, selectDay.getYear(), selectDay.getMonth(), selectDay.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    //리스트뷰의 아이템을 클릭했을 때 반응하는 리스너
    private ListView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            items.clear();
            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);

                onPlan = todayPlans.get(position);


            adapter.notifyDataSetChanged();
            ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
        }
    };

    //캘린더뷰에서 날짜가 변경되었을때 반응하는 리스너
    private CalendarView.OnDateChangeListener calendarViewChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            try {
                checkOnToday(view, year-1900, month, dayOfMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private void checkOnToday(CalendarView view, int year, int month, int dayOfMonth) throws ParseException {
        selectDay = new Date(year, month, dayOfMonth);
        todayPlans.clear();
        adapter.clear();

        String today = new SimpleDateFormat("yyyy-MM-dd").format(selectDay);
        final DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);
        todayPlans = (ArrayList)dbHelper.selectOnToday(today).clone();
        for(int i=0; i < todayPlans.size(); i++){
            adapter.addItem(todayPlans.get(i));
        }
        adapter.notifyDataSetChanged();
        dbHelper.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
//            textToday = (TextView) view.findViewById(R.id.textToday);
            adapter.notifyDataSetChanged();
            onResume();
        }
        System.out.println("onActivityResult");
    }
    @Override
    public void onResume(){
        System.out.println("On Resume");
        items.clear();
        try {
            checkOnToday(calendarView, selectDay.getYear(), selectDay.getMonth(), selectDay.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        setUserVisibleHint(true);
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(selectDay==null){
                selectDay = new Date();
            }
        }
    }

//    public void addPlan(){
//        Intent intent = new Intent(getActivity() , NewPlanActivity.class);
//        intent.putExtra("isEdit" , false);
////        MyTabFragment01.this.startActivity(intent);
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//    }

}
