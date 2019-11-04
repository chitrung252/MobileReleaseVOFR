package fpt.com.virtualoutfitroom.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fpt.com.virtualoutfitroom.R;

public class ForgetActivity extends BaseActivity implements View.OnClickListener{
    private Button mBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialData();
    }
    public void initialData(){
        mBtnBack = findViewById(R.id.btn_back_reg);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_reg: backToLogin();
                break;
        }
    }
    public void backToLogin(){
        finish();
    }
}
