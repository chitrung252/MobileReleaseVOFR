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

import fpt.com.virtualoutfitroom.R;


public class MethodFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_method, container, false);
    }

    public MethodFragment() {
    }
    public static Fragment newInstance(){
        MethodFragment fragment = new MethodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
