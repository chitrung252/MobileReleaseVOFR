package fpt.com.virtualoutfitroom.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.utils.DataConvert;
@Entity(tableName = "Account")
public class AccountItemEntities {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String accountId;
    @TypeConverters(DataConvert.class)
    @ColumnInfo(name = "account")
    private Account account;
    @NonNull
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(@NonNull String accountId) {
        this.accountId = accountId;
    }

    public AccountItemEntities() {
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
