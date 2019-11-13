package fpt.com.virtualoutfitroom.presenter.product_cate;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.CategoryView;
import fpt.com.virtualoutfitroom.views.ProductCateView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class ProductCatePresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private ProductCateView mView;

    public ProductCatePresenter(Context mContext, ProductCateView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void getProductByCateId(int cateId)
    {
        mRepository.getProductByCateId(mContext, cateId, new CallBackData<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                mView.showListProduct(products);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}
