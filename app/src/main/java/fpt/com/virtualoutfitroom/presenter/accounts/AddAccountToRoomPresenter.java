package fpt.com.virtualoutfitroom.presenter.accounts;

import android.app.Application;
import android.content.Context;

import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.management.AccountManagement;
import fpt.com.virtualoutfitroom.views.AddToRoomView;

public class AddAccountToRoomPresenter {
    private Context mContext;
    private Application mApplication;
    private AccountManagement accountManagement;
    private AddToRoomView addToRoomView;
    public AddAccountToRoomPresenter(Context mContext, Application Application, AddToRoomView addToRoomView) {
        this.mContext = mContext;
        this.mApplication = Application;
        accountManagement = new AccountManagement(mApplication);
        this.addToRoomView = addToRoomView;
    }
    public void addAccountToRooṃ(AccountItemEntities accountItemEntities){
        accountManagement.addAccountItem(accountItemEntities);
        addToRoomView.addToRoomSuccess();
    }
}
