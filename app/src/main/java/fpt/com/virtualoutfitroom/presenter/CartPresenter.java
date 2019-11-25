package fpt.com.virtualoutfitroom.presenter;

import android.app.Application;
import android.content.Context;
import android.view.View;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.room.management.OrderItemManagement;
import fpt.com.virtualoutfitroom.views.AddToCartView;
import fpt.com.virtualoutfitroom.views.UpdateCardView;

public class CartPresenter {
   private Context context;
   private Application mApplication;
   private OrderItemManagement orderItemManagement;
   private AddToCartView view;
   private UpdateCardView mView;

    public CartPresenter(Context context, Application application, AddToCartView view) {
        this.context = context;
        this.mApplication = application;
        this.orderItemManagement = new OrderItemManagement(application);
        this.view = view;
    }

    public CartPresenter(Context context, Application mApplication, UpdateCardView mView) {
        this.context = context;
        this.mApplication = mApplication;
        this.orderItemManagement = new OrderItemManagement(mApplication);
        this.mView = mView;
    }
    public CartPresenter(Context context, Application mApplication, UpdateCardView mView, AddToCartView view) {
        this.context = context;
        this.mApplication = mApplication;
        this.orderItemManagement = new OrderItemManagement(mApplication);
        this.mView = mView;
        this.view = view;
    }

    public void addToCart(OrderItemEntities o){
        orderItemManagement.addOrderItem(o);
        view.onSuccess();
    }
    public void getListOrder(){
        orderItemManagement.getOrderItem(new OrderItemManagement.DataCallBack() {
            @Override
            public void onSuccess(List<OrderItemEntities> list) {
                view.showListOrderItem(list);
            }

            @Override
            public void onFail(String message) {
                view.showError(message);
            }
        });
    }
    public  void updateToCart(OrderItemEntities orderItemEntities){
        orderItemManagement.updateOrder(orderItemEntities);
        mView.updateCardSuccess();
    }

}
