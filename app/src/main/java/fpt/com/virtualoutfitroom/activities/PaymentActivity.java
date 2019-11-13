package fpt.com.virtualoutfitroom.activities;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.PaymentCustomTab;
import fpt.com.virtualoutfitroom.fragments.AddressFragment;
import fpt.com.virtualoutfitroom.fragments.FinishFragment;
import fpt.com.virtualoutfitroom.fragments.MethodFragment;

public class PaymentActivity extends BaseActivity {
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private FragmentStatePagerItemAdapter mAdapter;
    private LinearLayout mBtnNext;
    private LinearLayout mBtnBack;
    private TextView mTxtNext;
    int PAGE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialView();
        initialData();
    }

    private void initialView(){
        mViewPager = findViewById(R.id.view_pager_payment);
        mSmartTabLayout = findViewById(R.id.smart_tab_payment);
        mBtnNext = findViewById(R.id.lnl_next_row);
        mBtnBack = findViewById(R.id.lnl_back_arrow);
        mTxtNext = findViewById(R.id.text_view_next);

    }

    private void initialData(){
        final PaymentCustomTab mPagerAdapter = new PaymentCustomTab(PaymentActivity.this);
        mSmartTabLayout.setCustomTabView(mPagerAdapter);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("", AddressFragment.class));
        pages.add(FragmentPagerItem.of("", MethodFragment.class));
        pages.add(FragmentPagerItem.of("", FinishFragment.class));
        mAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mSmartTabLayout.setViewPager(mViewPager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        ImageView iconDefault = mSmartTabLayout.getTabAt(0).findViewById(R.id.activity_payment_tab_icon);
        iconDefault.setColorFilter(Color.parseColor("#3661EE"));
        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                ImageView icon = mSmartTabLayout.getTabAt(0).findViewById(R.id.activity_payment_tab_icon);
//                ImageView icon1 = mSmartTabLayout.getTabAt(1).findViewById(R.id.activity_payment_tab_icon);
//                ImageView icon2 = mSmartTabLayout.getTabAt(2).findViewById(R.id.activity_payment_tab_icon);
//                if (position == 0) {
//                    icon.setColorFilter(Color.parseColor("#3661EE"));
//                    icon1.setColorFilter(Color.parseColor("#9B9B9B"));
//                    icon2.setColorFilter(Color.parseColor("#9B9B9B"));
//                } else if (position == 1) {
//                    icon.setColorFilter(Color.parseColor("#9B9B9B"));
//                    icon1.setColorFilter(Color.parseColor("#3661EE"));
//                    icon2.setColorFilter(Color.parseColor("#9B9B9B"));
//                } else if (position == 2) {
//                    icon.setColorFilter(Color.parseColor("#9B9B9B"));
//                    icon1.setColorFilter(Color.parseColor("#9B9B9B"));
//                    icon2.setColorFilter(Color.parseColor("#3661EE"));
//                }
                disableTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1, true);
            }
        });
        mBtnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1, true);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }
            @Override
            public void onPageSelected(int position) {
                if(position==0) {
                    mTxtNext.setText("Tiếp theo");
                }else  {
                    mBtnBack.setVisibility(View.VISIBLE);
                }
                if(position < mViewPager.getAdapter().getCount()-1 ) {
                    mBtnNext.setVisibility(View.VISIBLE);
                    mTxtNext.setText("Tiếp theo");
                }else  {
                    mBtnNext.setVisibility(View.VISIBLE);
                    mTxtNext.setText("Hoàn thành");
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void disableTab(int tabNumber)
    {
        ViewGroup vg = (ViewGroup) mSmartTabLayout.getChildAt(0);
        ViewGroup vgTab = (ViewGroup) vg.getChildAt(tabNumber);
        vgTab.setEnabled(false);
    }
}
