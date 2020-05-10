package fpt.com.virtualoutfitroom.presenter.product_size;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.ProductSize;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;

import fpt.com.virtualoutfitroom.views.ProductSizeView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class ProductSizePresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private ProductSizeView mView;

    public ProductSizePresenter(Context mContext, ProductSizeView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }

    public void getProductSize(int productId){
        mRepository.getProductSize(mContext, productId, new CallBackData<List<ProductSize>>() {
            @Override
            public void onSuccess(List<ProductSize> productSizes) {
                mView.onSuccess(productSizes);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
