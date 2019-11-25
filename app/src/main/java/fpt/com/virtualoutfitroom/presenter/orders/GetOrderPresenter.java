package fpt.com.virtualoutfitroom.presenter.orders;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.OrderView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class GetOrderPresenter  {
    private Context mContext;
    private VofrRepository mVofrRepository;
    private OrderView mOrderView;

    public GetOrderPresenter(Context mContext, OrderView mOrderView) {
        this.mContext = mContext;
        this.mVofrRepository = new VofrImpl();
        this.mOrderView = mOrderView;
    }
    public  void getOrder(String token,String accountId){
        this.mVofrRepository.getOrder(mContext, token, accountId, new CallBackData<List<OrderHistory>>() {
            @Override
            public void onSuccess(List<OrderHistory> orderHistories) {
                mOrderView.getOrderSuccess(orderHistories);
            }

            @Override
            public void onFail(String message) {
                mOrderView.getOrderFail(message);
            }
        });
    }

}
