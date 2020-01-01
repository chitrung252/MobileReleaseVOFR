package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable {
    @SerializedName("order_item_id")
    private int orderIdItem;
    @SerializedName("price")
    private double price;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("product")
    private Product product;
    @SerializedName("order_id")
    private String orderId;

    public OrderItem() {
    }

    public int getOrderIdItem() {
        return orderIdItem;
    }

    public void setOrderIdItem(int orderIdItem) {
        this.orderIdItem = orderIdItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
