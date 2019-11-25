package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.UpdateAccountToRoomPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.UpdateInforAccountPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;
import fpt.com.virtualoutfitroom.views.UpdateInforAccountView;
import fpt.com.virtualoutfitroom.views.UpdateToRoomView;

public class EditAccountActivity extends BaseActivity implements GetInforAccountView, View.OnClickListener, UpdateInforAccountView, UpdateToRoomView {
    private InformationAccountPresenter informationAccountPresenter;
    private EditText mEdtUsername,mEdtPassword,mEdtEmail,mEdtPhone,mEdtFirstName,mEdtLastname,mEdtAddress;
    private AccountItemEntities account;
    private Button mBtnBack,mBtnUpdate;
    private UpdateInforAccountPresenter updateInforAccountPresenter;
    private UpdateAccountToRoomPresenter updateAccountToRoomPresenter;
    private String mToken;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        initialView();
        initialData();
    }

    private void initialView(){
        mEdtUsername = findViewById(R.id.edt_username);
        mEdtPassword  = findViewById(R.id.edt_password);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPhone = findViewById(R.id.edt_phone);
        mEdtFirstName = findViewById(R.id.edt_first_name);
        mEdtLastname = findViewById(R.id.edt_lastname);
        mEdtAddress = findViewById(R.id.edt_address);
        mBtnUpdate = findViewById(R.id.button_update_account);
        mBtnBack = findViewById(R.id.button_back_edit_account);
    }

    private void initialData(){
        mBtnBack.setOnClickListener(this::onClick);
        mBtnUpdate.setOnClickListener(this::onClick);
        informationAccountPresenter = new InformationAccountPresenter(getApplication(),this);
        informationAccountPresenter.getAccountFromRoom();
    }

    private void setSpinner(){
        hud = SpinnerManagement.getSpinner(this);
    }
    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {
            account = accountItemEntities;
        if(account!=null){
            mEdtUsername.setText(account.getAccount().getUserName() + "");
            mEdtPassword.setText(account.getAccount().getPassword()+"");
            mEdtEmail.setText(account.getAccount().getEmail());
            mEdtFirstName.setText(account.getAccount().getFirstName() + "" );
            mEdtLastname.setText(account.getAccount().getLastName()+"");
            mEdtAddress.setText(account.getAccount().getAddress()+"");
            mEdtPhone.setText(account.getAccount().getPhoneNumber());
        }
        else{
            Intent intent = new Intent(EditAccountActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id ){
            case R.id.button_update_account :
                setSpinner();
                checkAccountExist();
                break;
            case R.id.button_back_edit_account:
                finish();
                break;
        }
    }

    private void checkAccountExist(){
        String userId = SharePreferenceUtils.getStringSharedPreference(EditAccountActivity.this, BundleString.USERID);
        informationAccountPresenter.getInforAccount(userId);
    }
    private void updateAccount(){
        mToken = SharePreferenceUtils.getStringSharedPreference(EditAccountActivity.this, BundleString.TOKEN);
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        String email = mEdtEmail.getText().toString();
        String phone = mEdtPhone.getText().toString();
        String firstname = mEdtFirstName.getText().toString();
        String lastname = mEdtLastname.getText().toString();
        String address = mEdtAddress.getText().toString();
        account.getAccount().setUserName(username);
        account.getAccount().setPassword(password);
        account.getAccount().setEmail(email);
        account.getAccount().setFirstName(firstname);
        account.getAccount().setLastName(lastname);
        account.getAccount().setPhoneNumber(phone);
        account.getAccount().setAddress(address);
        updateInforAccountPresenter = new UpdateInforAccountPresenter(EditAccountActivity.this,this);
        updateInforAccountPresenter.updateInforAccount(mToken,account);
    }

    @Override
    public void updapteSuccess(String result) {
        updateAccountToRoomPresenter = new UpdateAccountToRoomPresenter(getApplication(),this);
        updateAccountToRoomPresenter.updateAccount(account);
    }

    @Override
    public void updateFail(String message) {
        Toast.makeText(EditAccountActivity.this,"Cập nhật khônh thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateAccountSuccess() {
        hud.dismiss();
        Intent intent = new Intent(EditAccountActivity.this,HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
    @Override
    public void getInforSuccess(Account account) {
        if (account != null){
            updateAccount();
        }else {
            Toast.makeText(this,"Your session is expried",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void getInforFail(String message) {

    }
}
