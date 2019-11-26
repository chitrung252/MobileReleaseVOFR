package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.OrderItem;

public interface OrderItemView extends BaseView {
    void showListOrderItem(List<OrderItem> listOrderItem);
}
