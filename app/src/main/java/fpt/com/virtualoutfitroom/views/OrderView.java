package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.OrderHistory;

public interface OrderView {
    void getOrderSuccess(List<OrderHistory> orderHistory);
    void getOrderFail(String message);
}
