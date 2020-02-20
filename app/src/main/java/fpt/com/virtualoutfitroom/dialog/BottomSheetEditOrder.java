package fpt.com.virtualoutfitroom.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.HomeActivity;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.presenter.CartPresenter;
import fpt.com.virtualoutfitroom.presenter.ShoppingCartPresenter;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.ChangeValue;
import fpt.com.virtualoutfitroom.utils.CurrencyManagement;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.DeleteToRomView;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;
import fpt.com.virtualoutfitroom.views.UpdateCardView;

public class BottomSheetEditOrder extends BottomSheetDialogFragment implements View.OnClickListener, UpdateCardView{
    private BottomSheetBehavior mBehavior;
    private Button mBtnUpdateToCart;
    private CartPresenter cartPresenter;
    private LinearLayout mLnlDismiss;
    private OrderItemEntities orderItemEntities;
    private double mTotal = 0;
    private int mQuantity = 1;
    private int mOldQuantity;
    private EditText mEdtQuantity;
    private ImageView mImgIncrease;
    private ImageView mImgDecrease;
    private ImageView mProductImg;
    private TextView mProductName;
    private TextView mProductPrice;
    private OnUpdateSuccess mOnUpdateSuccess;
    private KProgressHUD hud;

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.design.widget.BottomSheetDialog dialog = (android.support.design.widget.BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_update_order, null);
        initialView(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        initialData();
        return dialog;
    }
    public void initialView(View view) {
        mBtnUpdateToCart = view.findViewById(R.id.btn_update_card);
        mBtnUpdateToCart.setOnClickListener(this);
        mLnlDismiss = view.findViewById(R.id.lnl_dismiss_update);
        mLnlDismiss.setOnClickListener(this);
        mProductName = view.findViewById(R.id.txt_product_name_update);
        mProductPrice = view.findViewById(R.id.txt_product_price_update);
        mProductImg = view.findViewById(R.id.img_product_image);
        mEdtQuantity = view.findViewById(R.id.edit_text_quantity_order);
        mEdtQuantity.setEnabled(false);
        mImgDecrease = view.findViewById(R.id.image_decrease_setting_order_update);
        mImgIncrease = view.findViewById(R.id.image_increase_setting_order_update);
        mImgIncrease.setOnClickListener(this::onClick);
        mImgDecrease.setOnClickListener(this::onClick);
    }
    public void getSpinner(){
        hud = SpinnerManagement.getSpinner(getActivity());
    }
    public void initialData(){
        Bundle bundle = this.getArguments();
        orderItemEntities =(OrderItemEntities) bundle.getSerializable("Order");
        mQuantity = orderItemEntities.getQuality();
        mOldQuantity = orderItemEntities.getQuality();
        mTotal = orderItemEntities.getTotal();
        mProductName.setText(orderItemEntities.getProduct().getProductName());
        mProductPrice.setText(ChangeValue.formatDecimalPrice(orderItemEntities.getTotal()));
        Picasso.get().load(RefineImage.getUrlImage(orderItemEntities.getProduct().getProductImageList(),"img")).into(mProductImg);
        mEdtQuantity.setText(orderItemEntities.getQuality() +"");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_card:
                updateToCart();
                break;
            case R.id.lnl_dismiss_update:
                dismissBottomSheet();
                break;
            case R.id.image_decrease_setting_order_update:
                buttonDecrease();
                break;
            case R.id.image_increase_setting_order_update:
                buttonIncrease();
                break;
        }
    }

    private void updateToCart(){
        getSpinner();
        orderItemEntities.setQuality(mQuantity);
        orderItemEntities.setTotal(mTotal);
        cartPresenter = new CartPresenter(getActivity(),getActivity().getApplication(),this);
        cartPresenter.updateToCart(orderItemEntities);
    }
    private void buttonIncrease(){
        mQuantity++;
        mTotal = orderItemEntities.getProduct().getProductPrice()* mQuantity;
        mEdtQuantity.setText(String.valueOf(mQuantity));
        mProductPrice.setText(ChangeValue.formatDecimalPrice((double) mTotal));
    }
    private void buttonDecrease(){
        mQuantity--;
        if (mQuantity < 1) {
            mQuantity = 1;
        }
        mTotal = orderItemEntities.getProduct().getProductPrice()* mQuantity;
        mEdtQuantity.setText(String.valueOf(mQuantity));
        mProductPrice.setText(ChangeValue.formatDecimalPrice((double) mTotal));
    }
    private void  dismissBottomSheet(){
        getDialog().cancel();
    }
    private void countShopCart(){
        int count = SharePreferenceUtils.getIntSharedPreference(getActivity(), BundleString.COUNTSHOPCART);
        int num = mOldQuantity - mQuantity;
        if(num != 0){
            SharePreferenceUtils.saveIntSharedPreference(getActivity(),BundleString.COUNTSHOPCART, count - num);
        }
//        HomeActivity.updateUI();
    }
    @Override
    public void updateCardSuccess() {
            countShopCart();
            dismissBottomSheet();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              hud.dismiss();
            }
        }, 500);
        mOnUpdateSuccess.clickUpdateSuccess(true);
    }
    public  void OnClickUpdateSucess(OnUpdateSuccess onUpdateSuccess){
        this.mOnUpdateSuccess = onUpdateSuccess;
    }
    public interface OnUpdateSuccess{
        void clickUpdateSuccess(boolean isSuccess);
    }
}
