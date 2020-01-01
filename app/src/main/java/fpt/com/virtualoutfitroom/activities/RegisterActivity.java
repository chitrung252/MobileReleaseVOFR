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
import fpt.com.virtualoutfitroom.presenter.accounts.CreateAccountPresenter;
import fpt.com.virtualoutfitroom.utils.RegexHelper;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.RegisterView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {
    private Button mBtnBack;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtEmail;
    private EditText mEdtPhone;
    private Button mBtnConfirm;
    private CreateAccountPresenter mPresenter;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialView();
    }
    private void getSpinner(){
        hud = SpinnerManagement.getSpinner(this);
    }
    public void initialView() {
        mEdtUsername = findViewById(R.id.edt_username);
        mEdtPassword = findViewById(R.id.edt_password);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPhone = findViewById(R.id.edt_phone_number);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnConfirm.setOnClickListener(this);
        mBtnBack = findViewById(R.id.btn_back_reg);
        mBtnBack.setOnClickListener(this);
        mPresenter = new CreateAccountPresenter(this,this);
    }

    public void createUser() {
        boolean check = true;
        String username = mEdtUsername.getText().toString();
        if(username.length() <= 0){
            check = false;
            mEdtUsername.setError("Tài khoản không được để trống");
        }else{
            if (!RegexHelper.checkUserName(username)) {
                check = false;
                mEdtUsername.setError("Tài khoản không có kí tự đặc biệt");
            }
        }
        String password = mEdtPassword.getText().toString();
        if(password.length() <= 0){
            check = false;
            mEdtPassword.setError("Mật khẩu không được để trống");
        }
        String phoneNumber = mEdtPhone.getText().toString();
        if(phoneNumber.length() <= 0){
            check = false;
            mEdtPhone.setError("Số điện thoại không được để trống");
        }else{
            if(!RegexHelper.checkPhoneNumber(phoneNumber)){
                check = false;
                mEdtPhone.setError("Số điện thoại không hợp lệ");
            }
        }
        String email = mEdtEmail.getText().toString();
        if(email.length() <= 0){
            check = false;
            mEdtEmail.setError("Email không được để trống");
        }else{
            if(!RegexHelper.checkEmail(email)){
                check = false;
                mEdtEmail.setError("Email không đúng định dạng");
            }
        }
        if(check){
            getSpinner();
            Account account = new Account();
            account.setEmail(email);
            account.setPhoneNumber(phoneNumber);
            account.setUserName(username);
            account.setPassword(password);
            account.setRoleId(2);
            mPresenter.createUser(account);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_reg:
                finish();
                break;
            case R.id.btn_confirm:
                createUser();
                break;
        }
    }

    @Override
    public void createSuccess(String message) {
        hud.dismiss();
        finish();
    }

    @Override
    public void showError(String message) {
        hud.dismiss();
        Toast.makeText(this,"Can't create user", Toast.LENGTH_LONG).show();
    }
}
