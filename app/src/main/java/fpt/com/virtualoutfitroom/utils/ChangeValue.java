package fpt.com.virtualoutfitroom.utils;

import java.text.DecimalFormat;

public class ChangeValue {
    public static String formatDecimalPrice(Double number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(number) + " đ";
    }
}
