package fpt.com.virtualoutfitroom.presenter.accounts;

import android.app.Application;

import fpt.com.virtualoutfitroom.room.management.AccountManagement;
import fpt.com.virtualoutfitroom.views.DeleteToRomView;

public class DeleteAccountToRoomPresenter {
    private Application mApplication;
    private AccountManagement accountManagement;
    private DeleteToRomView mDeleteToRomView;

    public DeleteAccountToRoomPresenter(Application application, DeleteToRomView mDeleteToRomView) {
        this.mApplication = application;
        this.accountManagement = new AccountManagement(mApplication);
        this.mDeleteToRomView = mDeleteToRomView;
    }
    public  void deleteAccount(){
        this.accountManagement.deleteAllAccount();
        mDeleteToRomView.deleteAccountSuccess();
    }
}
