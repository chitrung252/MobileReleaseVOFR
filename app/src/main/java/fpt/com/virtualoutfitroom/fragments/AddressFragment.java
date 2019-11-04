package fpt.com.virtualoutfitroom.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.AddressPaymentAdapter;


public class AddressFragment extends Fragment {
    private RecyclerView mRcvAddressPayment;
    private AddressPaymentAdapter mAddressPaymentAdapter;
    private List<String> mListAddress;
    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_address, container, false);
        initialView();
        initialData();
        return mView;
    }

    public AddressFragment() {
    }
    public static Fragment newInstance(){
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private  void initialView(){
        mRcvAddressPayment = mView.findViewById(R.id.rcv_payment_address);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvAddressPayment.setLayoutManager(layoutManager);
    }
    private void initialData(){
        mListAddress = new ArrayList<>();
        mListAddress.add("496/18 Lê Xuân Oai,Phường Long Thành,Quận 9,Thành Phố Hồ Chí Minh");
        mListAddress.add("92 Hàm Tử P. Đa kao.Quận 1,Thành Phố Hồ Chí Minh");
        updateUI();
    }
    private void updateUI(){
        if(mAddressPaymentAdapter == null){
            mAddressPaymentAdapter = new AddressPaymentAdapter(getContext(),mListAddress);
            mRcvAddressPayment.setAdapter(mAddressPaymentAdapter);
        }else {
            mAddressPaymentAdapter.notifyDataSetChanged();
        }
    }
}
