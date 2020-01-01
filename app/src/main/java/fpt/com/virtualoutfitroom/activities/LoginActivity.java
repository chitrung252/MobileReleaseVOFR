package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.AddAccountToRoomPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.LoginPresenter;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.AccountView;
import fpt.com.virtualoutfitroom.views.AddToRoomView;
import fpt.com.virtualoutfitroom.views.GetInforAccountView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, AccountView, GetInforAccountView, AddToRoomView {
    private LinearLayout lnlLayout;
    private TextView mTxtRegister;
    private TextView mTxtForgetPass;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mLogin;
    private KProgressHUD hud;
    private InformationAccountPresenter mInformationAccountPresenter;
    private AddAccountToRoomPresenter mAddAccountToRoomPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialData();
    }

    public void initialData(){
        lnlLayout =(LinearLayout) findViewById(R.id.lnl_login);
        lnlLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
        mTxtRegister = findViewById(R.id.txt_register);
        mTxtRegister.setOnClickListener(this);
        mTxtForgetPass = findViewById(R.id.txt_forget_pass);
        mTxtForgetPass.setOnClickListener(this);
        mEdtUsername = findViewById(R.id.edt_username);
        mEdtPassword = findViewById(R.id.edt_password);
        mLogin = findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
    }

    private void getSpinner(){
        hud = SpinnerManagement.getSpinner(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_register: clickToRegPage();
            break;
            case R.id.txt_forget_pass: clickToForgetPage();
            break;
            case R.id.btn_login :
                checkLogin();
            break;
        }
    }
    private void checkLogin() {
        LoginPresenter loginPresenter = new LoginPresenter(LoginActivity.this,this);
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        if(username.length() <= 0){
            mEdtUsername.setError("Vui lòng nhập tên tài khoản");
        }
        if(password.length() <= 0){
            mEdtPassword.setError("Vui lòng nhập mật khẩu");
        }
        if(password.length() > 0 && username.length() > 0){
            getSpinner();
            loginPresenter.checkLogin(username, password );
        }
    }
    public void clickToRegPage(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void clickToForgetPage(){
        Intent intent = new Intent(this,ForgetActivity.class);
        startActivity(intent);
    }
    @Override
    public void loginSuccess(Account account) {
        SharePreferenceUtils.saveStringSharedPreference(LoginActivity.this, BundleString.TOKEN,account.getAccessToken());
        SharePreferenceUtils.saveStringSharedPreference(LoginActivity.this, BundleString.USERID,account.getAccountId());
        mInformationAccountPresenter = new InformationAccountPresenter(this, this);
        mInformationAccountPresenter.getInforAccount(account.getAccountId());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,"Tên đăng nhập hoặc mật khẩu không đúng",Toast.LENGTH_LONG).show();
        hud.dismiss();
    }

    @Override
    public void addToRoomSuccess() {
        hud.dismiss();
        Intent intent = new Intent();
        intent.putExtra("success", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void getInforSuccess(Account account) {
        if (account != null) {
            addToRoom(account);
        }else {
            Toast.makeText(this,"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
        }
    }

     private void addToRoom(Account account) {
        AccountItemEntities accountItemEntities = new AccountItemEntities();
        String accountId = UUID.randomUUID().toString();
        accountItemEntities.setAccountId(accountId);
        accountItemEntities.setAccount(account);
        mAddAccountToRoomPresenter = new AddAccountToRoomPresenter(this, this.getApplication(), this);
        mAddAccountToRoomPresenter.addAccountToRooṃ(accountItemEntities);
    }

    @Override
    public void getInforFail(String message) {

    }

    @Override
    public void getAccountFromRoom(AccountItemEntities accountItemEntities) {

    }
}
