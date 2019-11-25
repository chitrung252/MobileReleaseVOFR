package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.AddAccountToRoomPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.InformationAccountPresenter;
import fpt.com.virtualoutfitroom.presenter.accounts.LoginPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.AccountView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, AccountView{
    private LinearLayout lnlLayout;
    private TextView mTxtRegister;
    private TextView mTxtForgetPass;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mLogin;
    private AddAccountToRoomPresenter mAddAccountToRoomPresenter;
    private InformationAccountPresenter mInformationAccountPresenter;
    private KProgressHUD hud;
    private String mCurrentSrceen;

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
                getSpinner();
                checkLogin();
            break;
        }
    }
    private void checkLogin() {
        LoginPresenter loginPresenter = new LoginPresenter(LoginActivity.this,this);
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        loginPresenter.checkLogin(username, password );

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
        hud.dismiss();
        SharePreferenceUtils.saveStringSharedPreference(LoginActivity.this, BundleString.TOKEN,account.getAccessToken());
        SharePreferenceUtils.saveStringSharedPreference(LoginActivity.this, BundleString.USERID,account.getAccountId());
        Intent intent = new Intent();
        intent.putExtra("success", true);
        setResult(RESULT_OK, intent);
        finish();

    }
    @Override
    public void showError(String message) {

    }
}
