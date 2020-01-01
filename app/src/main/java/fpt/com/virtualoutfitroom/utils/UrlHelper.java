package fpt.com.virtualoutfitroom.utils;

import java.net.URL;

public class UrlHelper {
    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
