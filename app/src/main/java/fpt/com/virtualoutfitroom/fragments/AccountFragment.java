package fpt.com.virtualoutfitroom.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;
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
public class AccountFragment extends Fragment implements View.OnClickListener, AddToRoomView , UpdateAvataView, GetInforAccountView {
    private FrameLayout mFrmedit;
    private View mView;
    private String token;
    private String userId;
    private TextView mTxtName,mTxtEmail,mTxtPhone,nmTxtAddress;
    private AddAccountToRoomPresenter mAddAccountToRoomPresenter;
    private UpdateAvatarPresenter mUpdateAvatarPresenter;
    private InformationAccountPresenter mInformationAccountPresenter;
    private Button mBtnLoginFirst;
    private CircleImageView mImageAvata;
    private Account mAccount;
    private Uri resultUri;
    public AccountFragment() {
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
        if(token.length() == 0 || token ==null){
            mView= inflater.inflate(R.layout.fragment_guest, container, false);
            initialView1();
            initialData1();
        }
        else {
            mView= inflater.inflate(R.layout.fragment_account, container, false);
            initialView2();
            initialData2();
        }
        return mView;
    }
    private void initialView1() {
        mBtnLoginFirst = mView.findViewById(R.id.btn_login_first);
    }
    private void initialData1(){
        mBtnLoginFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                mView.getContext().startActivity(intent);
            }
        });
    }
    private void initialView2(){
        mFrmedit = mView.findViewById(R.id.frm_edit);
        mImageAvata = mView.findViewById(R.id.profile_image);
        mTxtName = mView.findViewById(R.id.txt_name_account);
        mTxtEmail = mView.findViewById(R.id.txt_email_account);
        mTxtPhone = mView.findViewById(R.id.txt_phone_account);
        nmTxtAddress = mView.findViewById(R.id.txt_address_account);
    }
    private void initialData2(){
        mFrmedit.setOnClickListener(this);
        mImageAvata.setOnClickListener(this);
        mInformationAccountPresenter = new InformationAccountPresenter(this.getContext(),this);
        mInformationAccountPresenter.getInforAccount(userId);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frm_edit : moveToEditFragment();
            break;
            case R.id.profile_image:
                accessImageLibrary();
                break;
        }
    }
    public void moveToEditFragment(){
        Intent intent = new Intent(getActivity(), EditAccountActivity.class);
        startActivity(intent);
    }
    public void accessImageLibrary() {
        startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), BundleString.CODEIMGGALLERY);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BundleString.CODEIMGGALLERY && resultCode == Activity.RESULT_OK)
        {
            resultUri = data.getData();
            if (resultUri != null)
            {
                mImageAvata.setImageURI(resultUri);
                mUpdateAvatarPresenter = new UpdateAvatarPresenter(getContext(),this);
                mUpdateAvatarPresenter.updateAvatarAccount(token,userId,resultUri);
            }
        }

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
        Picasso.get().load(mAccount.getImageUser()).into(mImageAvata);
    }

    @Override
    public void updateAvatarSuccess(String result) {
    }

    @Override
    public void updateAvatarFail(String message) {

    }
    @Override
    public void getInforSuccess(Account account) {
        if(account !=null){
            addToRoom(account);
        }
    }
    @Override
    public void getInforFail(String message) {
    }
}
