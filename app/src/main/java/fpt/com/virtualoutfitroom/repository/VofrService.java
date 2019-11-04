package fpt.com.virtualoutfitroom.repository;

import fpt.com.virtualoutfitroom.webservice.ConfigApi;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
}
