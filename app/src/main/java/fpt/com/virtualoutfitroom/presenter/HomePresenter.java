package fpt.com.virtualoutfitroom.presenter;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.HomeView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class HomePresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private HomeView mView;

    public HomePresenter() {
    }

    public HomePresenter(Context mContext, HomeView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void getListProduct(){
        mRepository.getListProduct(mContext, new CallBackData<List<Product>>() {
            @Override
            public void onSuccess(List<Product> productList) {
                mView.showListProduct(productList);
            }

            @Override
            public void onFail(String message) {
                mView.showError(message);
            }
        });
    }
}
