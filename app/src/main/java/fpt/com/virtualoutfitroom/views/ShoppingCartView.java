package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;

public interface ShoppingCartView extends BaseView {
    void showListOrderItem(List<OrderItemEntities> orderItemEntities);
}
