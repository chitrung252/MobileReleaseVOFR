package fpt.com.virtualoutfitroom.presenter.accounts;

import android.app.Application;
import android.content.Context;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.management.AccountManagement;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class InformationAccountPresenter {
    private Context context;
    private Application application;
    private VofrRepository mVofrRepository;
    private AccountManagement accountManagement;
    private GetInforAccountView mInforAccountView;
    public InformationAccountPresenter(Context context, GetInforAccountView mInforAccountView) {
        this.context = context;
        this.mInforAccountView = mInforAccountView;
        this.mVofrRepository= new VofrImpl();
    }

    public InformationAccountPresenter(Application application, GetInforAccountView mInforAccountView) {
        this.application = application;
        this.accountManagement = new AccountManagement(application);
        this.mVofrRepository= new VofrImpl();
        this.mInforAccountView = mInforAccountView;
    }

    public void getInforAccount(String accountId){
        mVofrRepository.getInforAccount(context,accountId, new CallBackData<Account>() {
            @Override
            public void onSuccess(Account account) {
                mInforAccountView.getInforSuccess(account);
            }
            @Override
            public void onFail(String message) {
                mInforAccountView.getInforFail(message);
            }
        });
    }
    public void getAccountFromRoom(){
        accountManagement.getAccountItem(new AccountManagement.DataCallBack() {
            @Override
            public void onSuccess(AccountItemEntities account) {
                mInforAccountView.getAccountFromRoom(account);
            }
            @Override
            public void onFail(String message) {
                mInforAccountView.getInforFail(message);
            }
        });
    }
}
