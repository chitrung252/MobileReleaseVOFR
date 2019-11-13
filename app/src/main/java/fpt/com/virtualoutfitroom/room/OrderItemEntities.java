package fpt.com.virtualoutfitroom.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.utils.DataConvert;

@Entity(tableName = "Order")
public class OrderItemEntities {
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String orderItemId;
    @ColumnInfo(name = "Product")
    @TypeConverters(DataConvert.class)
    private Product product;

    public OrderItemEntities() {
    }

    @NonNull
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(@NonNull String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
