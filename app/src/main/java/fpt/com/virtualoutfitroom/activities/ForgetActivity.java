package fpt.com.virtualoutfitroom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.presenter.accounts.ForgetPasswordPresenter;
import fpt.com.virtualoutfitroom.utils.RegexHelper;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.ForgetView;

public class ForgetActivity extends BaseActivity implements View.OnClickListener, ForgetView {
    private Button mBtnBack, mBtnConfirm;
    private EditText mEdtEmail;
    private EditText mEdtUsername;
    private ForgetPasswordPresenter mPresenter;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initialView();
        initialData();
    }
    private void getSpinner(){
        hud = SpinnerManagement.getSpinner(this);
    }

    private void initialView(){
        mEdtUsername = findViewById(R.id.edt_username);
        mBtnBack = findViewById(R.id.btn_back_reg);
        mBtnBack.setOnClickListener(this);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnConfirm.setOnClickListener(this);
        mEdtEmail = findViewById(R.id.edt_email);
        mPresenter = new ForgetPasswordPresenter(this,this);
    }
    public void initialData(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_reg: backToLogin();
                break;
            case R.id.btn_confirm: sendMail();
            break;

        }
    }
    private void sendMail(){
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
            mPresenter.forgotPassword(username,email);
        }
    }
    public void backToLogin(){
        finish();
    }

    @Override
    public void sendMailSuccess(String message) {
        hud.dismiss();
        Toast.makeText(this,"Vui lòng kiểm tra email", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showError(String message) {
        hud.dismiss();
        Toast.makeText(this,"Lỗi", Toast.LENGTH_LONG).show();
    }
}
