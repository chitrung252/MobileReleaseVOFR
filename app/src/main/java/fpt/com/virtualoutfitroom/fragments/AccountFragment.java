package fpt.com.virtualoutfitroom.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.com.virtualoutfitroom.R;

import fpt.com.virtualoutfitroom.activities.LoginActivity;
import fpt.com.virtualoutfitroom.adapter.SwitchTabAdapter;
import fpt.com.virtualoutfitroom.dialog.BottomSheetEditAccount;
import fpt.com.virtualoutfitroom.presenter.accounts.UpdateAvatarPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.UpdateAvataView;

public class AccountFragment extends Fragment implements UpdateAvataView, View.OnClickListener {
   private View mView;
    private TabLayout mTabs;
    private View mIndicator;
   private ViewPager mViewPager;
    private int indicatorWidth;
    SwitchTabAdapter adapter;
    private ImageView mImgLoginFirst;
    private CircleImageView mImageAvata;
    private Uri resultUri;
    private UpdateAvatarPresenter mUpdateAvatarPresenter;
    private LinearLayout mLnlChooseEditAccout;
    private String token;
    private String userId;
    public AccountFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static Fragment newInstance(){
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        token = SharePreferenceUtils.getStringSharedPreference(getContext(), BundleString.TOKEN);
        userId = SharePreferenceUtils.getStringSharedPreference(getContext(), BundleString.USERID);
        if (token.length() == 0 || token == null) {
            mView = inflater.inflate(R.layout.fragment_guest, container, false);
            initialView1();
        } else {
            mView = inflater.inflate(R.layout.fragment_account, container, false);
            initialView();

        }

        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(token.length() == 0 ||token == null){
            initialData1();
        }
        else {
            initialData();

        }
    }
    private void initialView1() {
        mImgLoginFirst = mView.findViewById(R.id.img_login_first);
    }
    private void initialData1(){
        mImgLoginFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                mView.getContext().startActivity(intent);
            }
        });
    }
    private void initialView() {
        mTabs = mView.findViewById(R.id.tab);
        mIndicator = mView.findViewById(R.id.indicator);
        mViewPager = mView.findViewById(R.id.viewPager);
        mImageAvata = mView.findViewById(R.id.profile_image);
        mLnlChooseEditAccout = mView.findViewById(R.id.lnl_choose_edit_account);
    }
    private void initialData(){
        SwitchTabAdapter adapter= new SwitchTabAdapter(getChildFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Thông tin");
        adapter.addFragment(new HistoryFragment(), "Lịch sử");
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);

        mTabs.setupWithViewPager(mViewPager);

        mTabs.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = mTabs.getWidth() / mTabs.getTabCount();
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset = (positionOffset + i) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
                mTabs.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mImageAvata.setOnClickListener(this::onClick);
      mLnlChooseEditAccout.setOnClickListener(this::onClick);

    }

    @Override
    public void updateAvatarSuccess(String result) {

    }

    @Override
    public void updateAvatarFail(String message) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_image:
                accessImageLibrary();
                break;
            case R.id.lnl_choose_edit_account:
                showBottomSheetChooseEdit();
                break;
        }
    }
    public void showBottomSheetChooseEdit(){
        BottomSheetEditAccount bottomSheetEditAccount = new BottomSheetEditAccount();
        bottomSheetEditAccount.show(getFragmentManager(),"BottomSheetEditAccount");
    }
    public void accessImageLibrary() {
        startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), BundleString.CODEIMGGALLERY);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BundleString.CODEIMGGALLERY && resultCode == Activity.RESULT_OK)
        {
            resultUri = data.getData();
            if (resultUri != null)
            {
                mImageAvata.setImageURI(resultUri);
                mUpdateAvatarPresenter = new UpdateAvatarPresenter(getContext(),this);
                mUpdateAvatarPresenter.updateAvatarAccount(token,userId,resultUri);
            }
        }
    }
}
