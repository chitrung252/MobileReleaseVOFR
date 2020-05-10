package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.ProductSize;

public interface ProductSizeView extends BaseView {
    void onSuccess(List<ProductSize> list);
}
