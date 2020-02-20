package fpt.com.virtualoutfitroom.webservice;

public class ConfigApi {
    public static final String BASE_URL = "http://23.94.26.75/vat-api/api/";
    public interface Api{
       String PRODUCT = "product";
       String LOGIN = "account/check-login-mobile";
       String PARENTCATEGORY = "category/get-parent-cate";
       String SUBCATEGORY ="category/get-child-category";
       String PRODUCTCATE ="product/get-list-pro-by-cate-id";
       String PRODUCTIMG= "product-image/get-list-img";
       String UPDATEAVATAR="update-user-image";
       String GETINFORACCOUNT ="account";
       String UPDATEINFO = "account/update-account";
       String CREATEORDER = "order/create-order";;
       String ORDER = "order";
       String ORDERITEM = "order-item/order-item-history";
       String SEARCHPRODUCT = "product/search-product-by-name";
       String CREATUSER = "account/create-account";
       String FORGOTPASSWORD = "account/forgot-password";
       String CHANGEPASSWORD ="account/change-password-mobile";
    }
}
