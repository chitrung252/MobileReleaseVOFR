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

//    @Headers({"Content-Type: multipart/form-data","Content-Type: text/plain"})
    @Multipart
    @PUT(ConfigApi.Api.UPDATEAVATAR)
    Call<ResponseBody> updateaAvatarInfo(@HeaderMap Map<String, String> header,@Query("id") String accountId ,@Part MultipartBody.Part  image);
    @GET(ConfigApi.Api.GETINFORACCOUNT)
    Call<ResponseBody> getInforAccount(@Query("id") String accountId );
    @PUT(ConfigApi.Api.UPDATEINFO)
    Call<ResponseBody> updateaInfoAccount(@HeaderMap Map<String, String> header,@Body RequestBody orderJsonObject);
}
