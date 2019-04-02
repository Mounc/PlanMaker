package com.example.taehoon.planmaker_v2.Newbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.taehoon.planmaker_v2.R;

import java.util.ArrayList;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.client;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.userid;
import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.selected_item;

/**
 * Created by TAEHOON on 2017-05-26.
 */

public class MakingFragment01 extends Fragment {
    private class Data{
        public String id;
        public boolean check;

        public Data(String id, boolean check) {
            this.id = id;
            this.check = check;
        }
    }

    private ViewGroup view;

    private EditText search_user;
    private ListView selected_list;
    private ListView search_list;

    public static ArrayList<String> selected_item2 = new ArrayList<>();
    private ArrayList<String> search_item = new ArrayList<>();
    private ArrayAdapter<String> selected_adapter;
    private ArrayAdapter<String> search_adapter;

    private ArrayList<String> user_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup)inflater.inflate(R.layout.makingplanfragment01, container,false);

        search_user = (EditText)view.findViewById(R.id.make_search_user);
        selected_list = (ListView)view.findViewById(R.id.make_selected_list);
        search_list = (ListView)view.findViewById(R.id.make_search_list);
        search_item = new ArrayList<>();
        selected_item2 = new ArrayList<>();

        selected_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, selected_item2);
        search_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, search_item);

        selected_list.setAdapter(selected_adapter);
        search_list.setAdapter(search_adapter);

        search_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                search_item.clear();
                for(int i=0; i<user_list.size(); i++){
                    if(user_list.get(i).matches(".*"+search_user.getText().toString()+".*")) {
                        search_item.add(user_list.get(i));
                    }
                }
                search_adapter.notifyDataSetChanged();
                selected_adapter.notifyDataSetChanged();
            }
        });
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean check = false;
                for(int i=0; i<selected_item2.size(); i++){
                    if(selected_item2.get(i).equals(search_item.get(position))){
                        check = true;
                    }
                }
                if(!check) {
                    selected_item2.add(search_item.get(position));
                }
                selected_adapter.notifyDataSetChanged();
                search_adapter.notifyDataSetChanged();
            }
        });
        selected_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!userid.equals(selected_item2.get(position))) {
                    selected_item2.remove(position);
                }

                selected_adapter.notifyDataSetChanged();
                search_adapter.notifyDataSetChanged();
            }
        });

        loadUserList();
        selected_item2.add(userid);

        return view;
    }

    private void loadUserList(){
        user_list = client.loadUserList();
    }

    public static ArrayList<String> getUsers(){
        ArrayList<String> item = selected_item;
        return item;
    }
    public static void setUsers(){
        selected_item = selected_item2;
    }
}
