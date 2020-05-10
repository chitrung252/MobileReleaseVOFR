package fpt.com.virtualoutfitroom.repository;

import java.util.Map;

import fpt.com.virtualoutfitroom.webservice.ConfigApi;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface VofrService {
    @GET(ConfigApi.Api.PRODUCT)
    Call<ResponseBody> getListProduct();

    @GET(ConfigApi.Api.PRODUCT)
    Call<ResponseBody> getProduct(@Query("id") int productId);

    @POST(ConfigApi.Api.LOGIN)
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> checkLogin(@Body RequestBody requestBody);

    @GET(ConfigApi.Api.PARENTCATEGORY)
    Call<ResponseBody> getListCategory();

    @GET(ConfigApi.Api.SUBCATEGORY)
    Call<ResponseBody> getListSubCategory(@Query("categoryId") int categoryId );

    @GET(ConfigApi.Api.PRODUCTCATE)
    Call<ResponseBody> getListProduByCateId(@Query("id") int cateId );
    @GET(ConfigApi.Api.PRODUCTIMG)
    Call<ResponseBody> getListImage(@Query("id") int productID, @Query("type") String typeImage);

    @GET(ConfigApi.Api.GETINFORACCOUNT)
    Call<ResponseBody> getInforAccount(@Query("id") String accountId );

    @PUT(ConfigApi.Api.UPDATEINFO)
    Call<ResponseBody> updateaInfoAccount(@HeaderMap Map<String, String> header,@Body RequestBody orderJsonObject);

    @POST(ConfigApi.Api.UPDATEAVATAR)
    Call<ResponseBody> updateaImage(@HeaderMap Map<String, String> header,@Query("userId")String userId,@Part MultipartBody.Part  image);

    @Headers({"Content-Type: application/json"})
    @POST(ConfigApi.Api.CREATEORDER)
    Call<ResponseBody> createOrder(@HeaderMap Map<String, String> header,@Body RequestBody orderJsonObject);

    @GET(ConfigApi.Api.ORDER)
    Call<ResponseBody> getOrder(@Query("accountId") String accountId );

    @GET(ConfigApi.Api.ORDERITEM)
    Call<ResponseBody> getOrderItem(@Query("orderId") String orderId);

    @GET(ConfigApi.Api.SEARCHPRODUCT)
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> searchProduct(@Query("proName") String proName);

    @POST(ConfigApi.Api.CREATUSER)
    Call<ResponseBody> createUser(@Body RequestBody userJsonObject);

    @PUT(ConfigApi.Api.FORGOTPASSWORD)
    Call<ResponseBody> forgotPassword(@Query("username") String username, @Query("email") String email);

    @PUT(ConfigApi.Api.CHANGEPASSWORD)
    Call<ResponseBody> changePassword(@HeaderMap Map<String, String> header,@Query("password") String password,@Query("passwordNew") String passwordNew);

    @GET(ConfigApi.Api.PRODUCTSIZE)
    Call<ResponseBody> getProductSize( @Query("id") int productId);
}
