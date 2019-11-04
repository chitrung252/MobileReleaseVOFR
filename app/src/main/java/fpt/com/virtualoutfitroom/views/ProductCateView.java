package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Product;

public interface ProductCateView extends BaseView {
    void showListProduct(List<Product> productList);
}
