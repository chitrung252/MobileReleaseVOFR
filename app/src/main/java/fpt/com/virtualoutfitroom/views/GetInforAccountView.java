package fpt.com.virtualoutfitroom.views;

import android.content.Context;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;

public interface GetInforAccountView {
    void getInforSuccess(Account account);
    void getInforFail(String message);
    void getAccountFromRoom(AccountItemEntities accountItemEntities);
}
