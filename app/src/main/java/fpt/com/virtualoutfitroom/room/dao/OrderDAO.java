package fpt.com.virtualoutfitroom.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;


@Dao
public interface OrderDAO {
    @Insert
    void insertOrderItem(OrderItemEntities...orderItemEntities);

    @Query("Select * From `order`")
    List<OrderItemEntities> getListOrderItem();

    @Delete
    void deleteOrderItem(OrderItemEntities ... orderItemEntities);

    @Update
    void updateOrder(OrderItemEntities... orderItemEntities);

    @Query("DELETE FROM `order`")
    void deleleAllOrder();
}
