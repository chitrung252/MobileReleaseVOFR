package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.OrderItemAdapter;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.model.OrderItem;
import fpt.com.virtualoutfitroom.presenter.orderitem.OrderItemPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.OrderItemView;

public class OrderItemDetailActivity extends BaseActivity implements View.OnClickListener, OrderItemView {
    private ImageView mImgBack;
    private TextView mTxtName;
    private TextView mTxtEmail;
    private TextView mTxtPhone;
    private TextView mTxtAddress;
    private OrderHistory mOrder;
    private RecyclerView mRcvOrderItem;
    private OrderItemAdapter mRcvOrderItemAdapter;
    private List<OrderItem> mListOrderItem;
    private OrderItemPresenter mOrderItemPresenter;
    private Button mBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_detail);
        initialView();
        initialData();
    }

    private void initialView(){
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
        mTxtName = findViewById(R.id.txt_name_account);
        mTxtEmail = findViewById(R.id.txt_email_account);
        mTxtPhone = findViewById(R.id.txt_phone_account);
        mTxtAddress = findViewById(R.id.txt_address_account);
        mRcvOrderItem = findViewById(R.id.rcv_order_item);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRcvOrderItem.setLayoutManager(layoutManager);
        mListOrderItem = new ArrayList<>();
    }

    private void initialData(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNBLE");
        mOrder = (OrderHistory)bundle.getSerializable("ORDER");
        mTxtName.setText(mOrder.getFull_name());
        mTxtEmail.setText(mOrder.getEmail());
        mTxtPhone.setText(mOrder.getPhone_number());
        mTxtAddress.setText(mOrder.getAddress());
        mOrderItemPresenter = new OrderItemPresenter(this,this);
        String token = SharePreferenceUtils.getStringSharedPreference(this, BundleString.TOKEN);
        mOrderItemPresenter.getListOrderItem(token,mOrder.getOrderId());
    }
    private void updateUI(){
        mRcvOrderItemAdapter = new OrderItemAdapter(getApplication(),mListOrderItem);
        mRcvOrderItem.setAdapter(mRcvOrderItemAdapter);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_to_previous : finish();
            case R.id.btn_back: finish();
        }
    }

    @Override
    public void showListOrderItem(List<OrderItem> listOrderItem) {
        if(listOrderItem != null){
            mListOrderItem = listOrderItem;
            updateUI();
        }
    }

    @Override
    public void showError(String message) {

    }
}
