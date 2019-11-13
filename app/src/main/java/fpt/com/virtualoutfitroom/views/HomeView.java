package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;

public interface HomeView extends BaseView{
    void showListProduct(List<Product> productList);
}
