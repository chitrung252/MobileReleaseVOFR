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


public class MethodFragment extends Fragment implements View.OnClickListener {
    private LinearLayout mLnlMethod1,mLnlMethod2,mLnlMethod3;
    private View mView;
    private SecordFragmentListener secordFragmentListener;
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
        mLnlMethod1.setOnClickListener(this::onClick);
        mLnlMethod2.setOnClickListener(this::onClick);
        mLnlMethod3.setOnClickListener(this::onClick);
        mLnlMethod1.setBackgroundResource(R.drawable.radius_blue_color);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
            switch (id){
                case R.id.lnl_method_1 :
                    chooseMethod1();
                break;
                case R.id.lnl_method_2:
                    chooseMethod2();
                    break;
                case R.id.lnl_method_3:
                    chooseMethod3();
                    break;
            }

    }
    private void chooseMethod1(){
        mLnlMethod1.setBackgroundResource(R.drawable.radius_blue_color);
        mLnlMethod2.setBackgroundResource(R.drawable.radius_white_color);
        mLnlMethod3.setBackgroundResource(R.drawable.radius_white_color);
        index = 1;
        secordFragmentListener.sendMethodPayment(index);
    }
    private void chooseMethod2(){
        mLnlMethod2.setBackgroundResource(R.drawable.radius_blue_color);
        mLnlMethod1.setBackgroundResource(R.drawable.radius_white_color);
        mLnlMethod3.setBackgroundResource(R.drawable.radius_white_color);
        index = 2;
        secordFragmentListener.sendMethodPayment(index);
    }
    private void chooseMethod3(){
        mLnlMethod3.setBackgroundResource(R.drawable.radius_blue_color);
        mLnlMethod1.setBackgroundResource(R.drawable.radius_white_color);
        mLnlMethod2.setBackgroundResource(R.drawable.radius_white_color);
        index = 3;
        secordFragmentListener.sendMethodPayment(index);
    }
    public interface SecordFragmentListener {
        void sendMethodPayment(int index);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            secordFragmentListener = (SecordFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " You must implement FirstFragmentListener");
        }
    }
}
