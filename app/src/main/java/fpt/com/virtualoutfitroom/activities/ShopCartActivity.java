package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.ShopCartAdapter;
import fpt.com.virtualoutfitroom.presenter.ShoppingCartPresenter;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;

public class ShopCartActivity extends BaseActivity implements ShoppingCartView, View.OnClickListener {
    private RecyclerView mRcvShopCart;
    private List<OrderItemEntities> mListOrder;
    private ShopCartAdapter mShopCartApdapter;
    private ShoppingCartPresenter mPresenter;
    private Button mBtnBack;
    private Button mPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        initialView();
        initialData();
    }
    void initialView(){
        mRcvShopCart = findViewById(R.id.rcv_shop_cart);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvShopCart.setLayoutManager(layoutManager);
        mListOrder = new ArrayList<>();
        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mPayment = findViewById(R.id.btn_payment);
        mPayment.setOnClickListener(this);

    }
    private void initialData(){
        mPresenter= new ShoppingCartPresenter(this, getApplication(), this);
        mPresenter.getAllOrderItem();
    }
    void updateUI(){
        mShopCartApdapter = new ShopCartAdapter(this, mListOrder);
        mRcvShopCart.setAdapter(mShopCartApdapter);
    }

    @Override
    public void showListOrderItem(List<OrderItemEntities> orderItemEntities) {
        mListOrder = orderItemEntities;
        updateUI();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(ShopCartActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back: finish();
            break;
            case R.id.btn_payment: payment();
        }
    }
    public void payment(){
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }
}
