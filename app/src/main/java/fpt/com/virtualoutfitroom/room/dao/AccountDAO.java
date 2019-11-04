package fpt.com.virtualoutfitroom.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import fpt.com.virtualoutfitroom.room.AccountItemEntities;

@Dao
public interface AccountDAO {
    @Insert
    void insertAccount(AccountItemEntities accountItemEntities);

    @Query("Select * From `account`")
    AccountItemEntities getAccount();
}
