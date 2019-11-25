package fpt.com.virtualoutfitroom.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.CartPresenter;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.ChangeValue;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.views.AddToCartView;
import fpt.com.virtualoutfitroom.views.UpdateCardView;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener, AddToCartView, UpdateCardView {
    private BottomSheetBehavior mBehavior;
    private Button mBtnAddToCart;
    private CartPresenter cartPresenter;
    private LinearLayout mLnlDismiss;
    private Product mProduct;
    private ImageView mImgProduct;
    private double mTotal = 1;
    private int mQuantity = 1;
    private EditText mEdtQuantity;
    private ImageView mImgIncrease;
    private ImageView mImgDecrease;
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
        mEdtQuantity = view.findViewById(R.id.edit_text_quantity_product);
        mEdtQuantity.setEnabled(false);
        mImgDecrease = view.findViewById(R.id.image_decrease_setting_order);
        mImgIncrease = view.findViewById(R.id.image_increase_setting_order);
        mImgIncrease.setOnClickListener(this::onClick);
        mImgDecrease.setOnClickListener(this::onClick);
    }

    public void initialData(){
        Bundle bundle = this.getArguments();
        mProduct =(Product) bundle.getSerializable("PRODUCT");
        mProductName.setText(mProduct.getProductName().toString());
        mProductPrice.setText(CurrencyManagement.getPrice(mProduct.getProductPrice(),"đ"));
        String img = RefineImage.getUrlImage(mProduct.getProductImageList(),"img");
        Picasso.get().load(img).placeholder(R.mipmap.glasses).into(mImgProduct);
        mEdtQuantity.setText("1");
        mTotal = mProduct.getProductPrice();
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
            case R.id.image_decrease_setting_order:
                buttonDecrease();
                break;
            case R.id.image_increase_setting_order:
                buttonIncrease();
                break;
        }
    }
    private void buttonIncrease(){
        mQuantity++;
        mTotal = mProduct.getProductPrice()* mQuantity;
        mEdtQuantity.setText(String.valueOf(mQuantity));
        mProductPrice.setText(ChangeValue.formatDecimalPrice((double) mTotal));
    }
    private void buttonDecrease(){
        mQuantity--;
        if (mQuantity < 1) {
            mQuantity = 1;
        }
        mTotal = mProduct.getProductPrice()* mQuantity;
        mEdtQuantity.setText(String.valueOf(mQuantity));
        mProductPrice.setText(ChangeValue.formatDecimalPrice((double) mTotal));
    }
    private void addToCart() {
        cartPresenter = new CartPresenter(getActivity(),getActivity().getApplication(),this,this);
        cartPresenter.getListOrder();
    }
    private void  dismissBottomSheet(){
        getDialog().cancel();
    }
    @Override
    public void onSuccess() {
        cancelDialog();
    }

    @Override
    public void showListOrderItem(List<OrderItemEntities> item) {
        if(item != null){
            for (OrderItemEntities order:item) {
                if(order.getProduct().getId() == mProduct.getId()){
                    order.setQuality(order.getQuality() + mQuantity);
                    order.setTotal(order.getTotal() + order.getProduct().getProductPrice() * mQuantity);
                    cartPresenter = new CartPresenter(getActivity(),getActivity().getApplication(),this,this);
                    cartPresenter.updateToCart(order);
                    return;
                }
            }
        }
        createOrderItem();
    }
    private void createOrderItem(){
        OrderItemEntities o = new OrderItemEntities();
        String orderId = UUID.randomUUID().toString();
        o.setOrderItemId(orderId);
        o.setTotal(mTotal);
        o.setQuality(mQuantity);
        o.setProduct(mProduct);
        cartPresenter.addToCart(o);
    }
    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateCardSuccess() {
        cancelDialog();
    }
    private void cancelDialog(){
        Toast.makeText(getActivity(),"Đã thêm vào gỏ hàng", Toast.LENGTH_LONG).show();
        getDialog().cancel();
    }
}
