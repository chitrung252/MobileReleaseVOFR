package fpt.com.virtualoutfitroom.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.EditAccountActivity;
import fpt.com.virtualoutfitroom.activities.HomeActivity;
import fpt.com.virtualoutfitroom.activities.LoginActivity;
import fpt.com.virtualoutfitroom.presenter.accounts.DeleteAccountToRoomPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.DeleteToRomView;

public class BottomSheetEditAccount extends BottomSheetDialogFragment implements View.OnClickListener, DeleteToRomView {
    private BottomSheetBehavior mBehavior;
    private LinearLayout mLnlEditAccount,mLnlSignOut;
    private DeleteAccountToRoomPresenter mDeleteAccountToRoomPresenter;
    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.design.widget.BottomSheetDialog dialog = (android.support.design.widget.BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_edit_account, null);
        initialView(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        initialData();
        return dialog;
    }
    public  void initialView(View view){
        mLnlEditAccount = view.findViewById(R.id.lnl_edit_account);
        mLnlSignOut = view.findViewById(R.id.lnl_sign_out);

    }
    public  void initialData(){
        mLnlEditAccount.setOnClickListener(this);
        mLnlSignOut.setOnClickListener(this);
    }
    private void  dismissBottomSheet(){
        getDialog().cancel();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.lnl_edit_account:
                dismissBottomSheet();
                moveToEditFragment();
                break;
            case R.id.lnl_sign_out:
                signOut();
                break;
        }
    }
    private  void signOut(){
        SharePreferenceUtils.removeStringSharedPreference(getContext(),BundleString.USERID);
        SharePreferenceUtils.removeStringSharedPreference(getContext(),BundleString.TOKEN);
        mDeleteAccountToRoomPresenter = new DeleteAccountToRoomPresenter(getActivity().getApplication(),this::deleteAccountSuccess);
        mDeleteAccountToRoomPresenter.deleteAccount();
    }
    public void moveToEditFragment(){
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
    }
    public void moveToLoginActivity(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().finishAffinity();
        startActivity(intent);
    }
    @Override
    public void deleteAccountSuccess() {
        moveToLoginActivity();
    }
}
