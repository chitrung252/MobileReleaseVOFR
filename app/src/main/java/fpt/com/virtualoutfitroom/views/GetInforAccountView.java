package fpt.com.virtualoutfitroom.views;

import android.content.Context;

import fpt.com.virtualoutfitroom.model.Account;
public interface GetInforAccountView {
    void getInforSuccess(Account account);
    void getInforFail(String message);
}
