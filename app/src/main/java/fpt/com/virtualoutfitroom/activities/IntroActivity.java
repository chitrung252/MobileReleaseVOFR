package fpt.com.virtualoutfitroom.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import fpt.com.virtualoutfitroom.R;

public class IntroActivity extends BaseActivity {
    private LinearLayout lnlLayout;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        lnlLayout =(LinearLayout) findViewById(R.id.lnl_intro);
        lnlLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
        animation = AnimationUtils.loadAnimation(this, R.anim.introtransition);
        lnlLayout.startAnimation(animation);
        int SplashDuration = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(IntroActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SplashDuration);
    }
}
