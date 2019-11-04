package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.EditAccountActivity;


public class AccountFragment extends Fragment implements View.OnClickListener{
    private FrameLayout mFrmedit;
    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_account, container, false);
        initialView(rootView);
        return rootView;
    }
    public void initialView(View view){
        mFrmedit = view.findViewById(R.id.frm_edit);
        mFrmedit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frm_edit : moveToEditFragment();
            break;
        }
    }
    public void moveToEditFragment(){
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
    }
}
