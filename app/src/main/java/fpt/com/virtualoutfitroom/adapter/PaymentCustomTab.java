package fpt.com.virtualoutfitroom.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.fragments.AddressFragment;
import fpt.com.virtualoutfitroom.fragments.FinishFragment;
import fpt.com.virtualoutfitroom.fragments.MethodFragment;

public class PaymentCustomTab implements SmartTabLayout.TabProvider {
    private Context mContext;

    public PaymentCustomTab(Context mContext) {
        this.mContext = mContext;
    }
    public enum PaymentActivityPages {
        TAB_1(0,  R.drawable.ic_location, AddressFragment.newInstance()),
        TAB_2(1,  R.drawable.ic_method, MethodFragment.newInstance()),
        TAB_3(2,  R.drawable.ic_finish, FinishFragment.newInstance());
        public int index;
        public int resourceId;
        public Fragment fragment;
        PaymentActivityPages(int index,int resourceId, Fragment fragment) {
            this.index = index;
            this.resourceId = resourceId;
            this.fragment = fragment;
        }
    }
    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.activity_payment_tab, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (v != null) {
            ImageView icon = (ImageView) v.findViewById(R.id.activity_payment_tab_icon);
            icon.setImageResource(PaymentActivityPages.values()[position].resourceId);
        }
        return v;
    }
}
