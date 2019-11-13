package fpt.com.virtualoutfitroom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import fpt.com.virtualoutfitroom.activities.BaseActivity;
import fpt.com.virtualoutfitroom.fragments.AccountFragment;
import fpt.com.virtualoutfitroom.fragments.FragmentDrawer;
import fpt.com.virtualoutfitroom.fragments.HomeFragment;
import fpt.com.virtualoutfitroom.fragments.MessagesFragment;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private FragmentStatePagerItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        mToolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mViewPager = findViewById(R.id.view_pager);
        mSmartTabLayout = findViewById(R.id.tabs);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        //displayView(0);

        initialViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Setting clicked!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Search clicked!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new AccountFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            fragmentTransaction.commit();
//
//            // set the toolbar title
//            getSupportActionBar().setTitle(title);
//        }
    }

    private void initialViewPager(){
//        final MainCustomTab mPagerAdapter = new MainCustomTab(MainActivity.this);
//        mSmartTabLayout.setCustomTabView(mPagerAdapter);
//
//        FragmentPagerItems pages = new FragmentPagerItems(this);
//        pages.add(FragmentPagerItem.of(this.getResources().getString(R.string.nav_item_home), HomeFragment.class));
//        pages.add(FragmentPagerItem.of(this.getResources().getString(R.string.nav_item_friends), FriendsFragment.class));
//        pages.add(FragmentPagerItem.of(this.getResources().getString(R.string.nav_item_home), MessagesFragment.class));
//
//        mAdapter = new FragmentStatePagerItemAdapter(
//                getSupportFragmentManager(), pages);
//        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOffscreenPageLimit(3);
//
//        mSmartTabLayout.setViewPager(mViewPager);
//        setTabInitial();
//        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                setTabSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
    }

    private void setTabInitial() {
        ImageView icon = mSmartTabLayout.getTabAt(0).findViewById(R.id.activity_main_tab_icon);
        ImageView icon1 = mSmartTabLayout.getTabAt(1).findViewById(R.id.activity_main_tab_icon);
        ImageView icon2 = mSmartTabLayout.getTabAt(2).findViewById(R.id.activity_main_tab_icon);
        TextView txtTabName = mSmartTabLayout.getTabAt(0).findViewById(R.id.name_tab);
        TextView txtTabName1 = mSmartTabLayout.getTabAt(1).findViewById(R.id.name_tab);
        TextView txtTabName2 = mSmartTabLayout.getTabAt(2).findViewById(R.id.name_tab);
        icon.setColorFilter(Color.parseColor("#000000"));
        icon1.setColorFilter(Color.parseColor("#BBBBBB"));
        icon2.setColorFilter(Color.parseColor("#BBBBBB"));
        txtTabName.setTextColor(Color.parseColor("#000000"));
        txtTabName1.setTextColor(Color.parseColor("#BBBBBB"));
        txtTabName2.setTextColor(Color.parseColor("#BBBBBB"));
    }

    private void setTabSelected(int position) {
        ImageView icon = mSmartTabLayout.getTabAt(0).findViewById(R.id.activity_main_tab_icon);
        ImageView icon1 = mSmartTabLayout.getTabAt(1).findViewById(R.id.activity_main_tab_icon);
        ImageView icon2 = mSmartTabLayout.getTabAt(2).findViewById(R.id.activity_main_tab_icon);
        TextView txtTabName = mSmartTabLayout.getTabAt(0).findViewById(R.id.name_tab);
        TextView txtTabName1 = mSmartTabLayout.getTabAt(1).findViewById(R.id.name_tab);
        TextView txtTabName2 = mSmartTabLayout.getTabAt(2).findViewById(R.id.name_tab);
        if (position == 0) {
            icon.setColorFilter(Color.parseColor("#000000"));
            icon1.setColorFilter(Color.parseColor("#BBBBBB"));
            icon2.setColorFilter(Color.parseColor("#BBBBBB"));
            txtTabName.setTextColor(Color.parseColor("#000000"));
            txtTabName1.setTextColor(Color.parseColor("#BBBBBB"));
            txtTabName2.setTextColor(Color.parseColor("#BBBBBB"));
        } else if (position == 1) {
            icon.setColorFilter(Color.parseColor("#BBBBBB"));
            icon1.setColorFilter(Color.parseColor("#000000"));
            icon2.setColorFilter(Color.parseColor("#BBBBBB"));
            txtTabName.setTextColor(Color.parseColor("#BBBBBB"));
            txtTabName1.setTextColor(Color.parseColor("#000000"));
            txtTabName2.setTextColor(Color.parseColor("#BBBBBB"));
        } else if (position == 2) {
            icon.setColorFilter(Color.parseColor("#BBBBBB"));
            icon1.setColorFilter(Color.parseColor("#BBBBBB"));
            icon2.setColorFilter(Color.parseColor("#000000"));
            txtTabName.setTextColor(Color.parseColor("#BBBBBB"));
            txtTabName1.setTextColor(Color.parseColor("#BBBBBB"));
            txtTabName2.setTextColor(Color.parseColor("#000000"));
        }
    }
}


