package com.example.taehoon.planmaker_v2.Newbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taehoon.planmaker_v2.Data.Response;
import com.example.taehoon.planmaker_v2.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.client;
import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.getPlace;
import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.response;
import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.responseList;

/**
 * Created by TAEHOON on 2017-05-26.
 */

public class MakingFragment03 extends Fragment {
    private ViewGroup view;

    private ArrayList<String> response_item;
    public static ArrayAdapter<String> response_adapter;
    private ListView response_listview;

    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup)inflater.inflate(R.layout.makingplanfragment03, container,false);

        response_listview = (ListView)view.findViewById(R.id.response_listview);
        response_item = new ArrayList<>();
        response_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, response_item);
        response_listview.setAdapter(response_adapter);


        for(int i=0; i<responseList.size(); i++){
            Response item = responseList.get(i);
            response_item.add(getDatetoString(item.getStartTime()) + "  -  " + getDatetoString(item.getEndTime()));

            response_adapter.notifyDataSetChanged();
        }
//        tv = (TextView)view.findViewById(R.id.textView11);
//        tv.setText(getPlace());

        response_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                response = responseList.get(position);
            }
        });


        return view;
    }

    private String getDatetoString(Date date){
        return new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(date);
    }

    @Override
    public void onResume() {
        super.onResume();
        response_adapter.notifyDataSetChanged();
    }
}
