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

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.PaymentCustomTab;
import fpt.com.virtualoutfitroom.fragments.AddressFragment;
import fpt.com.virtualoutfitroom.fragments.FinishFragment;
import fpt.com.virtualoutfitroom.fragments.MethodFragment;
import fpt.com.virtualoutfitroom.model.Account;
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

public class PaymentActivity extends BaseActivity implements GetInforAccountView,AddressFragment.FirstFragmentListener, MethodFragment.SecordFragmentListener, ShoppingCartView, CreateOrderView, DeleteOrderView {
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private FragmentStatePagerItemAdapter mAdapter;
    private LinearLayout mBtnNext;
    private LinearLayout mBtnBack;
    private TextView mTxtNext;
    private Account mAccount;
    private AccountItemEntities mAccountItemEntities;
    private CreateOrderPresenter mCreateOrderPresenter;
    private String mFullname,mEmail,mPhone,mAddress;
    private int ischeck =-1 ;
    private List<OrderItemEntities> mOrderItemEntities;
    private ShoppingCartPresenter mShoppingCartPresenter;
    private float finalTotal = 0;
    private String token ;
    private InformationAccountPresenter  informationAccountPresenter;
    int PAGE = 0;
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

    }
    public void getData(){
        informationAccountPresenter = new InformationAccountPresenter(getApplication(),this);
        informationAccountPresenter.getAccountFromRoom();
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
                    if(PAGE ==3) {
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
                    PAGE = 1;
                    mTxtNext.setText("Tiếp theo");
                }else  {
                    PAGE = 1;
                    mBtnBack.setVisibility(View.VISIBLE);
                }
                if(position < mViewPager.getAdapter().getCount()-1 ) {
                    mBtnNext.setVisibility(View.VISIBLE);
                    PAGE = 2;
                    mTxtNext.setText("Tiếp theo");
                }else  {
                    PAGE = 3;
                    mBtnNext.setVisibility(View.VISIBLE);
                    mTxtNext.setText("Hoàn thành");
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void payment(){
        mAccountItemEntities.getAccount().setAddress(mAddress);
        mAccountItemEntities.getAccount().setEmail(mEmail);
        mAccountItemEntities.getAccount().setPhoneNumber(mPhone);
        mCreateOrderPresenter = new CreateOrderPresenter(PaymentActivity.this,this);
        mCreateOrderPresenter.createOrder(mFullname,finalTotal,token,mAccountItemEntities,mOrderItemEntities);
    }
    private void disableTab(int tabNumber)
    {
        ViewGroup vg = (ViewGroup) mSmartTabLayout.getChildAt(0);
        ViewGroup vgTab = (ViewGroup) vg.getChildAt(tabNumber);
        vgTab.setEnabled(false);
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
        finalTotal = SharePreferenceUtils.getFloatSharedPreference(PaymentActivity.this, BundleString.TOTAL);
        mFullname = accountItemEntities.getAccount().getFirstName() + " " + accountItemEntities.getAccount().getLastName();
        mPhone = accountItemEntities.getAccount().getPhoneNumber();
        mEmail = accountItemEntities.getAccount().getPhoneNumber();
        mAddress = accountItemEntities.getAccount().getAddress();
        token = SharePreferenceUtils.getStringSharedPreference(PaymentActivity.this,BundleString.TOKEN);
        mShoppingCartPresenter = new ShoppingCartPresenter(PaymentActivity.this,getApplication(), (ShoppingCartView) this);
        mShoppingCartPresenter.getAllOrderItem();
    }
    @Override
    public void sendData(String name, String email, String phone, String address) {
        mFullname = name;
        mEmail = email;
        mPhone = phone;
        mAddress = address;
        FinishFragment fragment = (FinishFragment) getSupportFragmentManager().getFragments().get(2);
        fragment.getData(name,email,phone,address);
    }
    @Override
    public void sendMethodPayment(int index) {
        ischeck = index;
        FinishFragment fragment = (FinishFragment) getSupportFragmentManager().getFragments().get(2);
        fragment.getMethoid(index);
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
        Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteOrderFail(String message) {

    }
}
