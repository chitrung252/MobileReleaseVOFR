package fpt.com.virtualoutfitroom.presenter.detail_product;

import android.content.Context;

import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.DetailProductView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class DetailProductPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private DetailProductView mView;

    public DetailProductPresenter(Context mContext, DetailProductView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }

    public void getProduct(int productId){
        mRepository.getProduct(mContext, productId, new CallBackData<Product>() {
            @Override
            public void onSuccess(Product product) {
                mView.getProductSuccess(product);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
