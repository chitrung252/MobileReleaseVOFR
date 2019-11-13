package fpt.com.virtualoutfitroom.views;

import fpt.com.virtualoutfitroom.model.Account;

public interface AccountView  extends BaseView{
    void loginSuccess(Account account);
}
