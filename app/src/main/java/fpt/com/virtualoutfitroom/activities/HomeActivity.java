package fpt.com.virtualoutfitroom.activities;

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
    private SpaceTabLayout mSpaceTabLayout;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialView(savedInstanceState);
    }
    private void initialView(Bundle savedInstanceState){
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(CategoryFragment.newInstance());
        fragmentList.add(AccountFragment.newInstance());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerHome);
        mSpaceTabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        mSpaceTabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSpaceTabLayout.saveState(outState);
    }
}
