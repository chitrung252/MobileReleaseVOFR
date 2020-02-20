package fpt.com.virtualoutfitroom.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.presenter.accounts.ChangePasswordPresenter;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.views.ChangePasswordView;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ChangePasswordView {
    private EditText mEdtPassword, mEdtPasswordNew, mEdtPasswordConfirm;
    private Button mBtnBack, mBtnUpdate;
    private ChangePasswordPresenter mPresenter;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialView();
    }

    private void getSpinner() {
        hud = SpinnerManagement.getSpinner(this);
    }

    private void initialView() {
        mEdtPassword = findViewById(R.id.edt_password);
        mEdtPasswordNew = findViewById(R.id.edt_password_new);
        mEdtPasswordConfirm = findViewById(R.id.edt_password_confirm);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mBtnUpdate = findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(this);
        mPresenter = new ChangePasswordPresenter(this, this);
    }

    private void updatePassword() {
        boolean check = true;
        String password = mEdtPassword.getText().toString();
        if (password.length() <= 0) {
            check = false;
            mEdtPassword.setError("Mật khẩu không được để trống");
        }
        String passwordNew = mEdtPasswordNew.getText().toString();
        if (passwordNew.length() <= 0) {
            check = false;
            mEdtPasswordNew.setError("Mật khẩu mới không được để trống");
        }
        String passwordConfirm = mEdtPasswordConfirm.getText().toString();
        if(passwordConfirm.length() <= 0){
            check = false;
            mEdtPasswordConfirm.setError("Xác nhận mật khẩu không được để trống");
        }else{
            if (!passwordConfirm.equals(passwordNew)) {
                check = false;
                mEdtPasswordConfirm.setError("Mật khẩu không trùng khớp");
            }
        }
        if (check) {
            getSpinner();
            mPresenter.changePassword(password, passwordNew);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_update:
                updatePassword();
                break;
        }
    }

    @Override
    public void changePasswordSuccess(Account account) {
        hud.dismiss();
        if(account != null){
            SharePreferenceUtils.saveStringSharedPreference(this,BundleString.TOKEN,account.getAccessToken());
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
            mEdtPassword.setText("");
            mEdtPasswordNew.setText("");
            mEdtPasswordConfirm.setText("");
        }else {
            Toast.makeText(this, "Không có tài khoản được trả về", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showError(String message) {
        hud.dismiss();
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }
}
