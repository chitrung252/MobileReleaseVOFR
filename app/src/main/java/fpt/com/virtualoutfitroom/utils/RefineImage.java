package fpt.com.virtualoutfitroom.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fpt.com.virtualoutfitroom.model.ProductImage;

public class RefineImage {
    public static String getUrlImage(List<ProductImage> productImageList, String typeImg){
        String url;
        for (int i = 0; i < productImageList.size(); i++) {
            if(productImageList.get(i).getImageType().equals(typeImg)){
                url = productImageList.get(i).getImageUrl();
                return url;
            }
        }
       return null;
    }
}
