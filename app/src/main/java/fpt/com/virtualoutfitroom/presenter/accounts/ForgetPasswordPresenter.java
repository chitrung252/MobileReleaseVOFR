package fpt.com.virtualoutfitroom.presenter.accounts;

import android.content.Context;

import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.ForgetView;
import fpt.com.virtualoutfitroom.views.RegisterView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class ForgetPasswordPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private ForgetView forgetView;

    public ForgetPasswordPresenter(Context mContext, ForgetView forgetView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.forgetView = forgetView;
    }

    public void forgotPassword(String username, String email){
        mRepository.forgotPassword(mContext, username, email, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                forgetView.sendMailSuccess(s);
            }
            @Override
            public void onFail(String message) {
                forgetView.showError(message);
            }
        });
    }
}
