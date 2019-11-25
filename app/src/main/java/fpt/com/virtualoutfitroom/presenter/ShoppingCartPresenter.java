package fpt.com.virtualoutfitroom.presenter;

import android.app.Application;
import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.room.management.OrderItemManagement;
import fpt.com.virtualoutfitroom.views.DeleteOrderView;
import fpt.com.virtualoutfitroom.views.ShoppingCartView;

public class ShoppingCartPresenter {
    private Context mContext;
    private OrderItemManagement mOrderItemManagement;
    private ShoppingCartView mView;
    private DeleteOrderView mDeleteOrderView;
    public ShoppingCartPresenter(Context context, Application application, ShoppingCartView view){
        this.mContext= context;
        this.mOrderItemManagement= new OrderItemManagement(application);
        this.mView= view;
    }

    public ShoppingCartPresenter(Context mContext, Application application,  DeleteOrderView mDeleteOrderView) {
        this.mContext = mContext;
        this.mOrderItemManagement= new OrderItemManagement(application);
        this.mDeleteOrderView = mDeleteOrderView;
    }

    public void getAllOrderItem(){
        mOrderItemManagement.getOrderItem(new OrderItemManagement.DataCallBack() {
            @Override
            public void onSuccess(List<OrderItemEntities> list) {
                if(list!= null){
                    mView.showListOrderItem(list);
                }else {
                    mView.showError("Get shopping cart failed!");
                }
            }

            @Override
            public void onFail(String message) {
mView.showError(message);
            }
        });
    }
    public void removeItemCard(OrderItemEntities orderItemEntities){
        mOrderItemManagement.deleteOrderItem(orderItemEntities);
    }
    public void deleteOrder(){
        mOrderItemManagement.deleteAllAccount();
        this.mDeleteOrderView.deleteOrderSuccess("thành công");
    }
}
