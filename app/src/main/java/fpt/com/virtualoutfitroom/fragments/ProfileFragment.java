package fpt.com.virtualoutfitroom.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.activities.EditAccountActivity;
import fpt.com.virtualoutfitroom.activities.LoginActivity;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.AddAccountToRoomPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.UpdateAvatarPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.views.AddToRoomView;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
import fpt.com.virtualoutfitroom.views.UpdateAvataView;
public class ProfileFragment extends Fragment implements AddToRoomView, GetInforAccountView {
    private View mView;
    private String token;
    private String userId;
    private TextView mTxtName,mTxtEmail,mTxtPhone,nmTxtAddress;
    private AddAccountToRoomPresenter mAddAccountToRoomPresenter;
    private InformationAccountPresenter mInformationAccountPresenter;
    private Account mAccount;
    public ProfileFragment() {

    }
  public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        token = SharePreferenceUtils.getStringSharedPreference(getContext(), BundleString.TOKEN);
        userId = SharePreferenceUtils.getStringSharedPreference(getContext(), BundleString.USERID);
        mView= inflater.inflate(R.layout.fragment_profile, container, false);
        initialView();
        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();
    }


    private void initialView(){
        mTxtName = mView.findViewById(R.id.txt_name_account);
        mTxtEmail = mView.findViewById(R.id.txt_email_account);
        mTxtPhone = mView.findViewById(R.id.txt_phone_account);
        nmTxtAddress = mView.findViewById(R.id.txt_address_account);
    }
    private void initialData(){
        mInformationAccountPresenter = new InformationAccountPresenter(this.getContext(),this);
        mInformationAccountPresenter.getInforAccount(userId);
    }



    private void addToRoom(Account account){
        mAccount = account;
        AccountItemEntities accountItemEntities= new AccountItemEntities();
        String accountId = UUID.randomUUID().toString();
        accountItemEntities.setAccountId(accountId);
        accountItemEntities.setAccount(account);
        mAddAccountToRoomPresenter = new AddAccountToRoomPresenter(this.getContext(),this.getActivity().getApplication(),this);
        mAddAccountToRoomPresenter.addAccountToRooṃ(accountItemEntities);
    }
    @Override
    public void AddToRoomSuccess() {
        mTxtName.setText(mAccount.getFirstName() + " " + mAccount.getLastName());
        mTxtEmail.setText(mAccount.getEmail());
        mTxtPhone.setText(mAccount.getPhoneNumber());
        nmTxtAddress.setText(mAccount.getAddress());
    }
    @Override
    public void getInforSuccess(Account account) {
        if(account !=null){
            addToRoom(account);
        }
        else {
            Intent intent = new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void getInforFail(String message) {
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {

    }
}
