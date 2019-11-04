package fpt.com.virtualoutfitroom.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.CartPresenter;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.views.AddToCartView;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener, AddToCartView {
    private BottomSheetBehavior mBehavior;
    private Button mBtnAddToCart;
    private CartPresenter cartPresenter;
    private LinearLayout mLnlDismiss;
    private Product mProduct;
    private ImageView mImgProduct;
    private TextView mProductName;
    private TextView mProductPrice;

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.design.widget.BottomSheetDialog dialog = (android.support.design.widget.BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog, null);
        initialView(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        initialData();
        return dialog;
    }

    public void initialView(View view) {
        mBtnAddToCart = view.findViewById(R.id.btn_add_to_cart);
        mBtnAddToCart.setOnClickListener(this);
        mLnlDismiss = view.findViewById(R.id.lnl_dismiss);
        mLnlDismiss.setOnClickListener(this);
        mImgProduct = view.findViewById(R.id.img_product_image);
        mProductName = view.findViewById(R.id.txt_product_name);
        mProductPrice = view.findViewById(R.id.txt_product_price);
    }

    public void initialData(){
        Bundle bundle = this.getArguments();
        mProduct =(Product) bundle.getSerializable("PRODUCT");
        mProductName.setText(mProduct.getProductName().toString());
        mProductPrice.setText(mProduct.getProductPrice().toString());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                addToCart();
                break;
            case R.id.lnl_dismiss:
                dismissBottomSheet();
                break;
        }
    }

    private void addToCart() {
        OrderItemEntities o = new OrderItemEntities();
        String orderId = UUID.randomUUID().toString();
        o.setOrderItemId(orderId);
        o.setProduct(mProduct);
        cartPresenter = new CartPresenter(getActivity(),getActivity().getApplication(),this);
        cartPresenter.addToCart(o);
    }
    private void  dismissBottomSheet(){
        getDialog().cancel();
    }

    @Override
    public void onSuccess() {
        getDialog().cancel();
        cartPresenter.getListOrder();
    }

    @Override
    public void showListOrderItem(List<OrderItemEntities> item) {
        Log.e("aaa",item.size() + "");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
