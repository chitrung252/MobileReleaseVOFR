package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;

public interface AddToCartView extends BaseView {
    void onSuccess();
    void showListOrderItem(List<OrderItemEntities> item);
}
