package fpt.com.virtualoutfitroom.repository;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.model.OrderItem;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.model.ProductSize;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public interface VofrRepository {
    void getListProduct(Context context, CallBackData<List<Product>> callBackData);
    void getProduct(Context context,int productId, CallBackData<Product> callBackData);
    void checkLogin(Context context, String email, String password, CallBackData<Account> callBackData);
    void getListCategory(Context context, CallBackData<List<Category>> callbackData);
    void getListSubCategory(Context context,int categoryId, CallBackData<List<Category>> callbackData);
    void getProductByCateId(Context context, int cateId, CallBackData<List<Product>> callBackData);
    void getListImage(Context context, int productId, String imageType, CallBackData<List<ProductImage>> callBackData);
    void getInforAccount (Context context,String accountID,CallBackData<Account>callBackData);
    void updateInforAccount(Context context,String token, Account account,CallBackData<String>callBackData);
    void updateImage(Context context,String token,String userId,Uri imageUri,CallBackData<String>callBackData);
    void createOrder(Context context,OrderHistory order,String token, List<OrderItemEntities> orderItemEntities,CallBackData<String> callBackData);
    void getOrder(Context context, String token, String accountId, CallBackData<List<OrderHistory>> callBackData);
    void getOrderItem(Context context,String token, String orderId, CallBackData<List<OrderItem>> callBackData);
    void searchProduct(Context context, String productName, CallBackData<List<Product>> callBackData);
    void createUser(Context context, Account account, CallBackData<String> callBackData);
    void forgotPassword(Context context, String username, String email, CallBackData<String> callBackData);
    void changePassword(Context context,String password, String passwordNew, CallBackData<Account> callBackData);
    void getProductSize(Context context, int productId, CallBackData<List<ProductSize>> callBackData);
}
