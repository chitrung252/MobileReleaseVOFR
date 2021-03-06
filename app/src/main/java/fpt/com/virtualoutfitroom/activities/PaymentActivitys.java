package fpt.com.virtualoutfitroom.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class PaymentActivitys extends BaseActivity implements GetInforAccountView,AddressFragment.FirstFragmentListener, ShoppingCartView, CreateOrderView, DeleteOrderView {
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

    //impl paypal
    private Button mBtnPaypal;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialView();
        getData();
        startServicePaypal();
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
        final PaymentCustomTab mPagerAdapter = new PaymentCustomTab(PaymentActivitys.this,mAccount);
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
                        if(SharePreferenceUtils.getIntSharedPreference(getApplication(),"METHOD") == 2){
                            getPaypal();

                        }else{
                            payment();
                        }

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
        order.setTotal(SharePreferenceUtils.getFloatSharedPreference(PaymentActivitys.this, BundleString.TOTAL));
        order.setMethod(SharePreferenceUtils.getIntSharedPreference(PaymentActivitys.this,"METHOD"));
        mCreateOrderPresenter = new CreateOrderPresenter(PaymentActivitys.this,this);
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
        token = SharePreferenceUtils.getStringSharedPreference(PaymentActivitys.this,BundleString.TOKEN);
        mShoppingCartPresenter = new ShoppingCartPresenter(PaymentActivitys.this,getApplication(), (ShoppingCartView) this);
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
        mShoppingCartPresenter = new ShoppingCartPresenter(PaymentActivitys.this,getApplication(), (DeleteOrderView) this);
        mShoppingCartPresenter.deleteOrder();
    }

    @Override
    public void createOrderFail(String messageFail) {
    }

    @Override
    public void deleteOrderSuccess(String success) {
        SharePreferenceUtils.saveIntSharedPreference(this,BundleString.COUNTSHOPCART,0);
//        HomeActivity.updateUI();
        Intent intent = new Intent(PaymentActivitys.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteOrderFail(String message) {

    }

    private void getPaypal(){
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaymentActivitys.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private void startServicePaypal(){
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal("300"), "USD", "Kinh",
                paymentIntent);
    }

    private PayPalOAuthScopes getOauthScopes() {
        /* create the set of required scopes
         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
         * attributes you select for this app in the PayPal developer portal and the scopes required here.
         */
        Set<String> scopes = new HashSet<String>(
                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS) );
        return new PayPalOAuthScopes(scopes);
    }

    protected void displayResultText(String result) {
        Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    payment();
                    try {
                        Log.i("Paypal", confirm.toJSONObject().toString(4));
                        Log.i("Paypal", confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        displayResultText("PaymentConfirmation info received from PayPal");


                    } catch (JSONException e) {
                        Log.e("Paypal", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Paypal", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "Paypal",
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        displayResultText("Future Payment code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
