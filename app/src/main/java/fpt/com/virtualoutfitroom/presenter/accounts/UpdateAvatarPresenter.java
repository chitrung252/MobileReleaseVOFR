package fpt.com.virtualoutfitroom.presenter.accounts;
import android.content.Context;
import android.net.Uri;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.UpdateAvataView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;
public class UpdateAvatarPresenter {
    private Context context;
    private VofrRepository mVofrRepository;
    private UpdateAvataView mUpdateAvataView;
    public UpdateAvatarPresenter(Context context, UpdateAvataView mUpdateAvataView) {
        this.context = context;
        this.mUpdateAvataView = mUpdateAvataView;
        this.mVofrRepository = new VofrImpl();
    }
    public void updateAvatarAccount(String token,String accountId,Uri imageUri){
        this.mVofrRepository.updateAvatarInfo(context,token,accountId, imageUri, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mUpdateAvataView.updateAvatarSuccess(s);
            }
            @Override
            public void onFail(String message) {
                mUpdateAvataView.updateAvatarFail(message);
            }
        });
    }
}
