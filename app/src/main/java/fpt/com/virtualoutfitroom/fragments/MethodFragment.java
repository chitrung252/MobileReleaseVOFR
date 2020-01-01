package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;


public class MethodFragment extends Fragment {
    private LinearLayout mLnlMethod1,mLnlMethod2,mLnlMethod3;
    private View mView;
    private int index =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_method, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView();
        initialData();
    }
    public MethodFragment() {
    }
    public static Fragment newInstance(){
        MethodFragment fragment = new MethodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void initialView(){
        mLnlMethod1 = mView.findViewById(R.id.lnl_method_1);
        mLnlMethod2 = mView.findViewById(R.id.lnl_method_2);
        mLnlMethod3 = mView.findViewById(R.id.lnl_method_3);
    }

    private void initialData(){
        mLnlMethod1.setOnClickListener(new CustomClickListener(1));
        mLnlMethod2.setOnClickListener(new CustomClickListener(2));
        mLnlMethod3.setOnClickListener(new CustomClickListener(3));
        mLnlMethod1.setBackgroundResource(R.drawable.radius_blue_color);
        SharePreferenceUtils.saveIntSharedPreference(getActivity(),"METHOD",1);
    }

    private class CustomClickListener implements View.OnClickListener{
        int number;
        public CustomClickListener(int number){
            this.number = number;
        }
        @Override
        public void onClick(View view) {
            mLnlMethod1.setBackgroundResource(R.drawable.edit_text_cus_background);
            mLnlMethod2.setBackgroundResource(R.drawable.edit_text_cus_background);
            mLnlMethod3.setBackgroundResource(R.drawable.edit_text_cus_background);
            if(number == 1){
                mLnlMethod1.setBackgroundResource(R.drawable.radius_blue_color);
                SharePreferenceUtils.saveIntSharedPreference(getActivity(),"METHOD",1);
            }
            if(number == 2){
                mLnlMethod2.setBackgroundResource(R.drawable.radius_blue_color);
                SharePreferenceUtils.saveIntSharedPreference(getActivity(),"METHOD",2);
            }
            if(number == 3){
                mLnlMethod3.setBackgroundResource(R.drawable.radius_blue_color);
                SharePreferenceUtils.saveIntSharedPreference(getActivity(),"METHOD",3);
            }
        }
    }
}
