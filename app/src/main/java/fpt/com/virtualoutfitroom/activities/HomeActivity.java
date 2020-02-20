package fpt.com.virtualoutfitroom.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;
import eu.long1.spacetablayout.SpaceTabLayout;
import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.fragments.AccountFragment;
import fpt.com.virtualoutfitroom.fragments.CategoryFragment;
import fpt.com.virtualoutfitroom.fragments.HomeFragment;
import fpt.com.virtualoutfitroom.fragments.MessagesFragment;

public class HomeActivity extends BaseActivity {
    public SpaceTabLayout mSpaceTabLayout;
    ViewPager viewPager;
    static List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialView(savedInstanceState);
    }
    private void initialView(Bundle savedInstanceState){
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(CategoryFragment.newInstance());
        fragmentList.add(AccountFragment.newInstance());
        viewPager = (ViewPager) findViewById(R.id.viewPagerHome);
        mSpaceTabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        mSpaceTabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSpaceTabLayout.saveState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1001 && resultCode== RESULT_OK){
            if(data!= null){
                boolean success = data.getBooleanExtra("success", false);
                if(success){
                    //doan code duoi sai vi no se tao ra 3 instance moi khong attach voi actitity nen
                    // khi getActivity tu fragment se bi null
//                    fragmentList.clear();
//                    fragmentList.add(HomeFragment.newInstance());
//                    fragmentList.add(CategoryFragment.newInstance());
//                    fragmentList.add(AccountFragment.newInstance());
                    mSpaceTabLayout.initialize(viewPager, getSupportFragmentManager(),
                            fragmentList, new Bundle());
                }
            }
        }
    }

//    public static void updateUI(){
//        HomeFragment homeFragment = (HomeFragment) fragmentList.get(0);
//        homeFragment.setCountShopCart();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HomeFragment homeFragment = (HomeFragment) fragmentList.get(0);
        homeFragment.setCountShopCart();
    }
}
