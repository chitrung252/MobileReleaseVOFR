package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.CustomViewPager;
import fpt.com.virtualoutfitroom.adapter.PaymentCustomTab;
import fpt.com.virtualoutfitroom.fragments.AddressFragment;
import fpt.com.virtualoutfitroom.fragments.FinishFragment;
import fpt.com.virtualoutfitroom.fragments.MethodFragment;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.presenter.ShoppingCartPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.presenter.orders.CreateOrderPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.CreateOrderView;
import fpt.com.virtualoutfitroom.views.DeleteOrderView;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;

public class PaymentActivity extends BaseActivity implements GetInforAccountView,AddressFragment.FirstFragmentListener, ShoppingCartView, CreateOrderView, DeleteOrderView {
    private CustomViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private FragmentStatePagerItemAdapter mAdapter;
    private LinearLayout mBtnNext;
    private LinearLayout mBtnBack;
    private TextView mTxtNext;
    private Account mAccount;
    private AccountItemEntities mAccountItemEntities;
    private CreateOrderPresenter mCreateOrderPresenter;
    private String mFullname,mEmail,mPhone,mAddress,mDescription;
    private int ischeck =-1 ;
    private List<OrderItemEntities> mOrderItemEntities;
    private ShoppingCartPresenter mShoppingCartPresenter;
    private String token ;
    private InformationAccountPresenter  informationAccountPresenter;
    private int PAGE = 0;
    private OrderHistory order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialView();
        getData();
    }
    private void initialView(){
        mViewPager = findViewById(R.id.view_pager_payment);
        mSmartTabLayout = findViewById(R.id.smart_tab_payment);
        mBtnNext = findViewById(R.id.lnl_next_row);
        mBtnBack = findViewById(R.id.lnl_back_arrow);
        mTxtNext = findViewById(R.id.text_view_next);
        mBtnNext.setBackgroundResource(R.drawable.button_background);
        order = new OrderHistory();
    }
    public void getData(){
        informationAccountPresenter = new InformationAccountPresenter(getApplication(),this);
        informationAccountPresenter.getAccountFromRoom();
    }
    public void setDefaultTab(){
        setTabColor(0,"#3661EE");
        setTabColor(1,"#9B9B9B");
        setTabColor(2,"#9B9B9B");
    }
    public void setTabColor(int position, String color){
       ImageView icon = mSmartTabLayout.getTabAt(position).findViewById(R.id.activity_payment_tab_icon);
       icon.setColorFilter(Color.parseColor(color));
    }
    private void initialData(){
        final PaymentCustomTab mPagerAdapter = new PaymentCustomTab(PaymentActivity.this,mAccount);
        mSmartTabLayout.setCustomTabView(mPagerAdapter);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("", AddressFragment.class));
        pages.add(FragmentPagerItem.of("", MethodFragment.class));
        pages.add(FragmentPagerItem.of("", FinishFragment.class));
        mAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), pages);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //disable swipe
        mViewPager.setPagingEnabled(false);
        mSmartTabLayout.setViewPager(mViewPager);
        setDefaultTab();
        disableTab();
        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
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

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
            mBtnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(PAGE == 2) {
                        payment();
                    }
                    else {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    }
                }
            });

        mBtnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(PAGE == 0){
                    finish();
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1, true);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {
                    if(position == 0){
                        PAGE = 0;
                        mTxtNext.setText("Tiếp theo");
                        mBtnNext.setBackgroundResource(R.drawable.button_background);
                        setTabColor(1,"#9B9B9B");
                    }else if(position == 1){
                        PAGE = 1;
                        mTxtNext.setText("Tiếp theo");
                        mBtnNext.setBackgroundResource(R.drawable.button_background);
                        setTabColor(1,"#3661EE");
                        setTabColor(2,"#9B9B9B");
                    }else if(position == 2){
                        PAGE = 2;
                        mTxtNext.setText("Hoàn thành");
                        mBtnNext.setBackgroundResource(R.drawable.button_ar_background);
                        setTabColor(2,"#3661EE");
                        FinishFragment fragment = (FinishFragment) getSupportFragmentManager().getFragments().get(position);
                        fragment.getMethod();
                        AddressFragment fragment2 = (AddressFragment) getSupportFragmentManager().getFragments().get(0);
                        fragment2.getData();
                    }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void payment(){
        order.setTotal(SharePreferenceUtils.getFloatSharedPreference(PaymentActivity.this, BundleString.TOTAL));
        order.setMethod(SharePreferenceUtils.getIntSharedPreference(PaymentActivity.this,"METHOD"));
        mCreateOrderPresenter = new CreateOrderPresenter(PaymentActivity.this,this);
        mCreateOrderPresenter.createOrder(order,token,mOrderItemEntities);
    }
    private void disableTab()
    {
        ViewGroup vg = (ViewGroup) mSmartTabLayout.getChildAt(0);
        ViewGroup vgTab ;
        if(vg != null){
            for (int i = 0; i < vg.getChildCount(); i++){
                 vgTab = (ViewGroup) vg.getChildAt(i);
                vgTab.setEnabled(false);
            }
        }
    }
    public void changeStateBtnNext(boolean state){
        mBtnNext.setEnabled(state);
        if(!state){
            mBtnNext.setBackgroundResource(R.drawable.button_gray_background);
        }else{
            mBtnNext.setBackgroundResource(R.drawable.button_background);
        }
    }
    @Override
    public void getInforSuccess(Account account) {
    }
    @Override
    public void getInforFail(String message) {
        initialData();
    }
    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {
        mAccountItemEntities =  accountItemEntities;
        mFullname = accountItemEntities.getAccount().getFirstName() + " " + accountItemEntities.getAccount().getLastName();
        mPhone = accountItemEntities.getAccount().getPhoneNumber();
        mEmail = accountItemEntities.getAccount().getEmail();
        mAddress = accountItemEntities.getAccount().getAddress();
        token = SharePreferenceUtils.getStringSharedPreference(PaymentActivity.this,BundleString.TOKEN);
        mShoppingCartPresenter = new ShoppingCartPresenter(PaymentActivity.this,getApplication(), (ShoppingCartView) this);
        mShoppingCartPresenter.getAllOrderItem();
    }
    @Override
    public void sendData(OrderHistory order) {
        FinishFragment fragment = (FinishFragment) getSupportFragmentManager().getFragments().get(2);
        fragment.getData(order);
        this.order = order;
    }
    @Override
    public void showListOrderItem(List<OrderItemEntities> orderItemEntities) {
            mOrderItemEntities = new ArrayList<>();
            mOrderItemEntities = orderItemEntities;
            initialData();
    }
    @Override
    public void showError(String message) {

    }

    @Override
    public void createOrderSuccess(String messgae) {
        mShoppingCartPresenter = new ShoppingCartPresenter(PaymentActivity.this,getApplication(), (DeleteOrderView) this);
        mShoppingCartPresenter.deleteOrder();

    }

    @Override
    public void createOrderFail(String messageFail) {

    }

    @Override
    public void deleteOrderSuccess(String success) {
        SharePreferenceUtils.saveIntSharedPreference(this,BundleString.COUNTSHOPCART,0);
//        HomeActivity.updateUI();
        Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteOrderFail(String message) {

    }
}
