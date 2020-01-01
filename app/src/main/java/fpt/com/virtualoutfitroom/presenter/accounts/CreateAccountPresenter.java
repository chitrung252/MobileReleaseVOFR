package fpt.com.virtualoutfitroom.presenter.accounts;

import android.app.Application;
import android.content.Context;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.room.management.AccountManagement;
import fpt.com.virtualoutfitroom.views.AddToRoomView;
import fpt.com.virtualoutfitroom.views.CategoryView;
import fpt.com.virtualoutfitroom.views.RegisterView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class CreateAccountPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private RegisterView registerView;

    public CreateAccountPresenter(Context mContext, RegisterView registerView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.registerView = registerView;
    }

    public void createUser(Account account){
            mRepository.createUser(mContext, account, new CallBackData<String>() {
                @Override
                public void onSuccess(String s) {
                    registerView.createSuccess(s);
                }

                @Override
                public void onFail(String message) {

                }
            });
    }
}
