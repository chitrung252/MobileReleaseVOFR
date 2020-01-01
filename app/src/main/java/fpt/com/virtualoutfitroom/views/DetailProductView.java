package fpt.com.virtualoutfitroom.views;

import fpt.com.virtualoutfitroom.model.Product;

public interface DetailProductView extends BaseView {
    void getProductSuccess(Product product);
}
