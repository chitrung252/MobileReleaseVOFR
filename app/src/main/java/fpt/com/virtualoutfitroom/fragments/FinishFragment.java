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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.FinishPaymentAdapter;
import fpt.com.virtualoutfitroom.model.ProductPayment;


public class FinishFragment extends Fragment {
    private View mView;
    private RecyclerView mRcvPayemnt3;
    private FinishPaymentAdapter mAdapter;
    private List<ProductPayment> data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_finish, container, false);
        initialView();
        initialData();
        return mView;
    }

    public FinishFragment() {
    }
    public static Fragment newInstance(){
        FinishFragment fragment = new FinishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private void initialView(){
        mRcvPayemnt3 = mView.findViewById(R.id.rcv_finish);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvPayemnt3.setLayoutManager(layoutManager);
    }
    private void  initialData(){
        data = new ArrayList<>();
        data.add(new ProductPayment("http://www.chiemtaimobile.vn/images/detailed/29/mat-kinh-m1001-h10.jpg","Kính",1500000,2));
        data.add(new ProductPayment("http://www.chiemtaimobile.vn/images/detailed/21/mat-kinh-MS655.jpg","Kính thời trang",4500000,2));
        data.add(new ProductPayment("http://www.chiemtaimobile.vn/images/detailed/15/khau-trang-khang-bui-hinh-1.jpg","Khẩu Trang",1500000,8));
        data.add(new ProductPayment("https://khogiaythethao.vn/wp-content/uploads/2019/06/gucci-chunky-rhyton-sf-2.jpg","Giày",2500000,1));
        data.add(new ProductPayment("https://canifa.s3.amazonaws.com/media/catalog/product/cache_generated/500x/6ts19c002-sw001-m_2.jpg","Áo thun",3500000,2));
        updateUỊ̣();
    }
    private void updateUỊ̣(){
        if(mAdapter == null){
            mAdapter = new FinishPaymentAdapter(getContext(),data);
            mRcvPayemnt3.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
