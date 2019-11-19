package fpt.com.virtualoutfitroom.presenter.accounts;

import android.content.Context;

import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.views.UpdateInforAccountView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class UpdateInforAccountPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private UpdateInforAccountView mView;

    public UpdateInforAccountPresenter(Context mContext, UpdateInforAccountView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void updateInforAccount(String token,AccountItemEntities accountItemEntities){
        this.mRepository.updateInforAccount(mContext, token, accountItemEntities.getAccount(), new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mView.updapteSuccess(s);
            }

            @Override
            public void onFail(String message) {
                mView.updateFail(message);
            }
        });
    }
}
