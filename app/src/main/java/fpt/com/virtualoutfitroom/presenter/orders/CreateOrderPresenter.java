package fpt.com.virtualoutfitroom.presenter.orders;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.views.CreateOrderView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;
public class CreateOrderPresenter {
    private Context  mContext;
    private VofrRepository mVofrRepository;
    private CreateOrderView mCreateOrderView;
    public CreateOrderPresenter(Context mContext, CreateOrderView mCreateOrderView) {
        this.mContext = mContext;
        this.mVofrRepository = new VofrImpl();
        this.mCreateOrderView = mCreateOrderView;
    }
    public  void createOrder(String fullname,double finalPrice,String token, AccountItemEntities mAccountItemEntities, List<OrderItemEntities> orderItemEntitiesList){
        this.mVofrRepository.createOrder(mContext,fullname,finalPrice, token, mAccountItemEntities, orderItemEntitiesList, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mCreateOrderView.createOrderSuccess(s);
            }
            @Override
            public void onFail(String message) {
                mCreateOrderView.createOrderFail(message);
            }
        });
    }
}
