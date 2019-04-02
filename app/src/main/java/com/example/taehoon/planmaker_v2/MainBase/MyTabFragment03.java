package com.example.taehoon.planmaker_v2.MainBase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.Others.SearchListViewAdapter;
import com.example.taehoon.planmaker_v2.R;

import java.text.ParseException;
import java.util.ArrayList;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.onPlan;

/**
 * Created by TAEHOON on 2017-03-16.
 */


public class MyTabFragment03 extends Fragment {

    private EditText edit_search_plan;
    private ListView listview;
    private SearchListViewAdapter adapter;
    private ArrayList<MyPlan> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.mytabfragment03, container, false);

        adapter = new SearchListViewAdapter();

        listview = (ListView)view.findViewById(R.id.listview_search_plan);
        listview.setAdapter(adapter);

        edit_search_plan = (EditText)view.findViewById(R.id.edit_search_plan);
        edit_search_plan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_plan(s.toString());
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPlan = (MyPlan) adapter.getItem(position);
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
            }
        });

        search_plan("");

        return view;
    }

    private void search_plan(String title){
        DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);
        adapter.clear();

        try {
            list = dbHelper.selectAllPlanOnSearch(title);
            for (int i=0; i<list.size(); i++){
                MyPlan item = list.get(i);
                adapter.addItem(item);
            }

            adapter.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
