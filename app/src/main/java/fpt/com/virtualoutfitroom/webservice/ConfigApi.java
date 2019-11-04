package fpt.com.virtualoutfitroom.webservice;

public class ConfigApi {
    public static final String BASE_URL = "http://107.150.52.213/api-votf/api/";


    public interface Api{
       String PRODUCT = "product";
       String LOGIN = "account/check-login";
       String PARENTCATEGORY = "category/get-parent-cate";
       String SUBCATEGORY ="category/get-child-category";
       String PRODUCTCATE ="product/get-list-pro-by-cate-id";
    }
}
