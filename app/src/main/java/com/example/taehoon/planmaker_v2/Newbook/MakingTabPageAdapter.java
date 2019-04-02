package com.example.taehoon.planmaker_v2.Newbook;

//import android.app.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.taehoon.planmaker_v2.MainBase.MyTabFragment01;
import com.example.taehoon.planmaker_v2.MainBase.MyTabFragment02;
import com.example.taehoon.planmaker_v2.MainBase.MyTabFragment03;
//import android.support.v4.view.PagerAdapter;

/**
 * Created by TAEHOON on 2017-03-16.
 */

public class MakingTabPageAdapter extends FragmentStatePagerAdapter {

    private int tabcount;
    private MakingFragment01 makingFragment01;
    private MakingFragment02 makingFragment02;
    private MakingFragment03 makingFragment03;
    private MakingFragment04 makingFragment04;

    public MakingTabPageAdapter(FragmentManager fm, int tabcount){
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
                if(makingFragment01==null) {
                    makingFragment01 = new MakingFragment01();
                    System.out.println("getItem MyTabFragment01------------------------------------------");
                }
                return makingFragment01;
            case 1 :
                makingFragment02 = new MakingFragment02();
                return makingFragment02;
            case 2 :
                makingFragment03 = new MakingFragment03();
                return makingFragment03;
            case 3 :
                makingFragment04 = new MakingFragment04();
                return makingFragment04;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
