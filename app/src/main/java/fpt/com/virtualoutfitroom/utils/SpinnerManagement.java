package fpt.com.virtualoutfitroom.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class SpinnerManagement {
    public static KProgressHUD getSpinner(Context context){
        KProgressHUD hub =  KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Vui lòng chờ")
                .setDetailsLabel("Đang tải dữ liệu")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        return  hub;
    }
}
