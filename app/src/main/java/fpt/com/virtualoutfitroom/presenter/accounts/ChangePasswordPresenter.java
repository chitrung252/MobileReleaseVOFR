package fpt.com.virtualoutfitroom.presenter.accounts;

import android.content.Context;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.ChangePasswordView;
import fpt.com.virtualoutfitroom.views.ForgetView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class ChangePasswordPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private ChangePasswordView mView;

    public ChangePasswordPresenter(Context mContext, ChangePasswordView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }

    public void changePassword(String password, String passwordNew){
        mRepository.changePassword(mContext, password, passwordNew, new CallBackData<Account>() {
            @Override
            public void onSuccess(Account account) {
                mView.changePasswordSuccess(account);
            }

            @Override
            public void onFail(String message) {
                mView.showError(message);
            }
        });
    }
}
