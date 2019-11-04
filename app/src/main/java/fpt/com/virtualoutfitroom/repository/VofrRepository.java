package fpt.com.virtualoutfitroom.repository;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public interface VofrRepository {
    void getListProduct(Context context, CallBackData<List<Product>> callBackData);
    void checkLogin(Context context, String email, String password, CallBackData<Account> callBackData);
    void getListCategory(Context context, CallBackData<List<Category>> callbackData);
    void getListSubCategory(Context context,int categoryId, CallBackData<List<Category>> callbackData);
    void getProductByCateId(Context context, int cateId, CallBackData<List<Product>> callBackData);
}
