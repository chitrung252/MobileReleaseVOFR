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
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.ProductPayment;
import fpt.com.virtualoutfitroom.presenter.ShoppingCartPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;


public class FinishFragment extends Fragment implements GetInforAccountView, ShoppingCartView {
    private View mView;
    private RecyclerView mRcvPayemnt3;
    private FinishPaymentAdapter mAdapter;
    private List<ProductPayment> data;
    private TextView mTxtAddres;
    private TextView mTxtNameMethod;
    private ShoppingCartPresenter mShoppingCartPresenter;
    private List<OrderItemEntities> mOrderItemEntities;
    private String mAddress;
     private InformationAccountPresenter mInformationAccountPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_finish, container, false);

        return mView;
    }
    public FinishFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView();
        getData();
    }
    public static Fragment newInstance(String name, String email, String phone, String address){
        FinishFragment fragment = new FinishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private void initialView(){
        mRcvPayemnt3 = mView.findViewById(R.id.rcv_finish);
        mTxtAddres = mView.findViewById(R.id.txt_address_finish);
        mTxtNameMethod = mView.findViewById(R.id.txt_method_finish);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvPayemnt3.setLayoutManager(layoutManager);
    }
    public  void getData(){
        mInformationAccountPresenter = new InformationAccountPresenter(getActivity().getApplication(),this);
        mInformationAccountPresenter.getAccountFromRoom();
    }
    private void  initialData(Account account){
        mAddress = account.getAddress();
        mTxtAddres.setText(mAddress + "");
       mShoppingCartPresenter = new ShoppingCartPresenter(getContext(),getActivity().getApplication(),this);
       mShoppingCartPresenter.getAllOrderItem();
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
    public void getData(String name,String email,String phone,String address){
        mTxtAddres.setText(address+"");
    }
    public void getMethoid(int position){
        if(position == 1 ){
            mTxtNameMethod.setText("Thanh toán khi nhận hàng");
        }
        else if(position == 2 ){
            mTxtNameMethod.setText("Thanh toán bằng thẻ ngân hàng");
        }
        else if(position == 3){
            mTxtNameMethod.setText("Thanh toán bằng Paypal");
        }
        else
            mTxtNameMethod.setText("Bạn chưa chọn phương thức");
    }

    @Override
    public void getInforSuccess(Account account) {
    }

    @Override
    public void getInforFail(String message) {

    }

    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {
        initialData(accountItemEntities.getAccount());
    }
    @Override
    public void showListOrderItem(List<OrderItemEntities> orderItemEntities) {
        mOrderItemEntities = orderItemEntities;
        data = new ArrayList<>();
        for (int i = 0; i <mOrderItemEntities.size() ; i++) {
            String urlImg = RefineImage.getUrlImage(mOrderItemEntities.get(i).getProduct().getProductImageList(),"img");
            data.add(new ProductPayment(urlImg,mOrderItemEntities.get(i).getProduct().getProductName(),mOrderItemEntities.get(i).getProduct().getProductPrice(),mOrderItemEntities.get(i).getQuality()));
        }
        updateUỊ̣();
    }
    @Override
    public void showError(String message) {
    }
}
