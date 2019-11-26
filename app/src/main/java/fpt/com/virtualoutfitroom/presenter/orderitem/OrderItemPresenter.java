package fpt.com.virtualoutfitroom.presenter.orderitem;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.OrderItem;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.HomeView;
import fpt.com.virtualoutfitroom.views.OrderItemView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class OrderItemPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private OrderItemView mView;

    public OrderItemPresenter() {
    }
    public OrderItemPresenter(Context mContext, OrderItemView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void getListOrderItem(String token, String userId){
        mRepository.getOrderItem(mContext, token, userId, new CallBackData<List<OrderItem>>() {
            @Override
            public void onSuccess(List<OrderItem> orderItems) {
                mView.showListOrderItem(orderItems);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
