package fpt.com.virtualoutfitroom.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fpt.com.virtualoutfitroom.room.OrderItemEntities;


@Dao
public interface OrderDAO {
    @Insert
    void insertOrderItem(OrderItemEntities...orderItemEntities);

    @Query("Select * From `order`")
    List<OrderItemEntities> getListOrderItem();
}
