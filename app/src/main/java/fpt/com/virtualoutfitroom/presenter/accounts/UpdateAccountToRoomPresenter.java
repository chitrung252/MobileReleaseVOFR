package fpt.com.virtualoutfitroom.presenter.accounts;

import android.app.Application;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.management.AccountManagement;
import fpt.com.virtualoutfitroom.views.UpdateToRoomView;

public class UpdateAccountToRoomPresenter {
    private Application mApplication;
    private AccountManagement accountManagement;
    private UpdateToRoomView updateToRoomView;

    public UpdateAccountToRoomPresenter(Application mApplication, UpdateToRoomView updateToRoomView) {
        this.mApplication = mApplication;
        this.accountManagement = new AccountManagement(mApplication);
        this.updateToRoomView = updateToRoomView;
    }
    public void updateAccount(AccountItemEntities accountItemEntities){
        this.accountManagement.updateAccount(accountItemEntities);
        this.updateToRoomView.updateAccountSuccess();
    }
}
