package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.adapter.RecyclerViewApdapter;
import fpt.com.virtualoutfitroom.adapter.ShopCartAdapter;
import fpt.com.virtualoutfitroom.adapter.SwipeToDeleteCallback;
import fpt.com.virtualoutfitroom.dialog.BottomSheetEditOrder;
import fpt.com.virtualoutfitroom.presenter.ShoppingCartPresenter;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.ChangeValue;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;

public class ShopCartActivity extends BaseActivity implements ShoppingCartView, View.OnClickListener{
    private RecyclerView mRcvShopCart;
    private List<OrderItemEntities> mListOrder;
    private ShopCartAdapter mShopCartApdapter;
    private ShoppingCartPresenter mPresenter;
    private Button mBtnBack;
    private Button mPayment;
    private LinearLayout mLnlEmptyCart;

    private double mTotal;
    private TextView mTxtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        initialView();
        initialData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllOrderItem();
    }
    void initialView(){
        mRcvShopCart = findViewById(R.id.rcv_shop_cart);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvShopCart.setLayoutManager(layoutManager);
        mListOrder = new ArrayList<>();
        mBtnBack = findViewById(R.id.btn_back);
        mTxtTotal = findViewById(R.id.txt_total_shopping);
        mBtnBack.setOnClickListener(this);
        mPayment = findViewById(R.id.btn_payment);
        mPayment.setOnClickListener(this);
        mLnlEmptyCart = findViewById(R.id.lnl_cart_empty);
    }
    private void initialData(){
        mPresenter= new ShoppingCartPresenter(this, getApplication(), this);
        mPresenter.getAllOrderItem();
    }
    void updateUI(){
        if(mShopCartApdapter == null){
            mShopCartApdapter = new ShopCartAdapter(this, mListOrder);
            mRcvShopCart.setAdapter(mShopCartApdapter);
            mShopCartApdapter.setOnItemClickedListener(new RecyclerViewApdapter.OnItemClickedListener() {
                @Override
                public void onItemClicked(int position) {
                    intentToEditOrder(mListOrder.get(position));
                }
            });
        }
        else {
            mShopCartApdapter.notifyChange(mListOrder);
        }
    }
    private void intentToEditOrder(OrderItemEntities orderItemEntities){
        Bundle bundle = new Bundle();
        bundle.putSerializable("Order", (Serializable) orderItemEntities);
        BottomSheetEditOrder bottomSheetOrder = new BottomSheetEditOrder();
        bottomSheetOrder.show(getSupportFragmentManager(),"Bottom Sheet Order");
        bottomSheetOrder.setArguments(bundle);
        bottomSheetOrder.OnClickUpdateSucess(new BottomSheetEditOrder.OnUpdateSuccess() {
            @Override
            public void clickUpdateSuccess(boolean isSuccess) {
                mPresenter.getAllOrderItem();
            }
        });
    }
    @Override
    public void showListOrderItem(List<OrderItemEntities> orderItemEntities) {
        if(orderItemEntities.size() != 0){
            mListOrder = orderItemEntities;
            updateUI();
            costTotal(orderItemEntities);
            enableSwipeToDeleteAndUndo(mListOrder);
            mRcvShopCart.setVisibility(View.VISIBLE);
            mLnlEmptyCart.setVisibility(View.GONE);
        }else {
            setLayoutEmpty();
        }
    }

    private void setLayoutEmpty(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,0,0,0);
        mRcvShopCart.setVisibility(View.GONE);
        mBtnBack.setLayoutParams(params);
        mPayment.setVisibility(View.GONE);
        mLnlEmptyCart.setVisibility(View.VISIBLE);
        mTxtTotal.setText("0 VNĐ");
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
        String token = SharePreferenceUtils.getStringSharedPreference(this,BundleString.TOKEN);
        if(token.length() > 0){
            SharePreferenceUtils.saveFloatSharedPreference(ShopCartActivity.this, BundleString.TOTAL, (float) mTotal);
            Intent intent = new Intent(this, PaymentActivitys.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void enableSwipeToDeleteAndUndo(List<OrderItemEntities> mListOrder) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                mShopCartApdapter.removeItem(position);
                mPresenter.removeItemCard(mListOrder.get(position));
                mListOrder.remove(position);
                if(mListOrder.size() == 0){
                    setLayoutEmpty();
                    SharePreferenceUtils.saveIntSharedPreference(getApplication(),BundleString.COUNTSHOPCART, 0);
//                    HomeActivity.updateUI();
                }else{
                    costTotal(mListOrder);
                    countShopCart(mListOrder);
                }
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRcvShopCart);
    }
    public void costTotal(List<OrderItemEntities> mListOrder){
        mTotal = 0;
        for (int i = 0; i < mListOrder.size(); i++) {
            mTotal  += mListOrder.get(i).getQuality()*mListOrder.get(i).getProduct().getProductPrice();
        }
        mTxtTotal.setText(ChangeValue.formatDecimalPrice(mTotal));
    }

    private void countShopCart(List<OrderItemEntities> mListOrder){
        int totalCount = 0;
        for (OrderItemEntities order:mListOrder) {
           totalCount += order.getQuality();
        }
        SharePreferenceUtils.saveIntSharedPreference(this,BundleString.COUNTSHOPCART, totalCount);
        //update quantity
//        HomeActivity.updateUI();

    }
}
