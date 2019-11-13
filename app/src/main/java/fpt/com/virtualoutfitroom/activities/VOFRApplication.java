package fpt.com.virtualoutfitroom.activities;


import android.app.Application;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;



public class VOFRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initialCalligraphy();
    }

    private void initialCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/BalooTamma-Regular.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build()
        );
    }
}
