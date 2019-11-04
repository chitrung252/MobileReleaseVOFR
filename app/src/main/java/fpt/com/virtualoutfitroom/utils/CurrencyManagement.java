package fpt.com.virtualoutfitroom.utils;

import java.text.DecimalFormat;

public class CurrencyManagement {
    public static String getPrice(double price, String currency) {

        String format = "###,###,###";

        if (price % 1 != 0) {
            format = "###,###,###";
        }

        DecimalFormat dfSing = new DecimalFormat(format);

        String result = dfSing.format(price);

        result = result.replaceAll(",", ".");

        return result + currency;
    }
}
