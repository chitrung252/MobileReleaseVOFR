package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.fragments.PageFragment;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.presenter.category.CategoryPresenter;
import fpt.com.virtualoutfitroom.views.CategoryView;

public class ProductCategoryActivity extends BaseActivity implements CategoryView,View.OnClickListener {
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    CategoryPresenter mCategoryPrsenter;
    List<Category> mListCate;
    private FragmentPagerItemAdapter mAdapter;
    ImageView mImgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        initialView();
        initialData();
    }
    public void initialView(){
        mViewPager = findViewById(R.id.view_pager_category);
        mSmartTabLayout = findViewById(R.id.smart_tab_category);
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
    }
    public void initialData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        Category category = (Category) bundle.getSerializable("CATEGORY");
        mCategoryPrsenter = new CategoryPresenter(this,this);
        mCategoryPrsenter.getListSubCategory(category.getCategoryId());
    }
    private void getCategoryData(List<Category> categoryList) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getApplicationContext());
        for (int i = 0; i < categoryList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("SUBCATEGORY", categoryList.get(i));
            creator.add(categoryList.get(i).getCategoryName(), PageFragment.class, bundle);
        }
        mAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
        mViewPager.setOffscreenPageLimit(categoryList.size());
        mViewPager.setAdapter(mAdapter);
        //set viewPager for SmartTabLayout
        mSmartTabLayout.setViewPager(mViewPager);
        setColorTabFirstRun();
        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setColorForTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //set defaul color first run
    private void setColorTabFirstRun(){
        int count = mAdapter.getCount();
        TextView view = null;
        for (int i = 0; i < count; i++) {
            view = (TextView) mSmartTabLayout.getTabAt(i);
            view.setBackground(getResources().getDrawable(R.color.color_white));
            if(i == 0){
                view.setTextColor(getResources().getColor(R.color.color_cus_blue));
                view.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp5));
            }else{
                view.setTextColor(getResources().getColor(R.color.color_cus_gray));
                view.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp3));
            }
        }

    }
    //set color for tab
    private void setColorForTab(int position) {
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) mSmartTabLayout.getTabAt(i);
            view.setBackground(getResources().getDrawable(R.color.color_white));
            view.setTextColor(getResources().getColor(R.color.color_cus_gray));
            view.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp3));
        }
        TextView view = (TextView) mSmartTabLayout.getTabAt(position);
        view.setBackground(getResources().getDrawable(R.color.color_white));
        view.setTextColor(getResources().getColor(R.color.color_cus_blue));
        view.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp5));
    }
    @Override
    public void showListCategory(List<Category> categoryList) {
        if(categoryList != null){
            mListCate = categoryList;
            getCategoryData(mListCate);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_to_previous: finish();
            break;
        }
    }
}
