package com.example.taehoon.planmaker_v2.MainBase;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.PagerAdapter;

/**
 * Created by TAEHOON on 2017-03-16.
 */

public class TabPageAdapter extends FragmentStatePagerAdapter {

    private int tabcount;
    private MyTabFragment01 myTabFragment01;
    private MyTabFragment02 myTabFragment02;
    private MyTabFragment03 myTabFragment03;

    public TabPageAdapter(FragmentManager fm, int tabcount){
        super(fm);
        this.tabcount = tabcount;
    }

    @Override
    public int getCount() {
        return tabcount;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0 :
                if(myTabFragment01==null) {
                    myTabFragment01 = new MyTabFragment01();
                    System.out.println("getItem MyTabFragment01------------------------------------------");
                }
                return myTabFragment01;
            case 1 :
                myTabFragment02 = new MyTabFragment02();
                return myTabFragment02;
            case 2 :
                myTabFragment03 = new MyTabFragment03();
                return myTabFragment03;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
