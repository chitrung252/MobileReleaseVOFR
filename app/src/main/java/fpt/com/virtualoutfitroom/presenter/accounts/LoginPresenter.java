package fpt.com.virtualoutfitroom.presenter.accounts;

import android.content.Context;
import android.view.View;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.AccountView;
import fpt.com.virtualoutfitroom.views.HomeView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class LoginPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private AccountView mView;

    public LoginPresenter(Context mContext, AccountView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void checkLogin(String email, String password){
        mRepository.checkLogin(mContext, email, password, new CallBackData<Account>() {
            @Override
            public void onSuccess(Account account) {
                mView.loginSuccess(account);
            }

            @Override
            public void onFail(String message) {
                mView.showError(message);
            }
        });
    }
}
