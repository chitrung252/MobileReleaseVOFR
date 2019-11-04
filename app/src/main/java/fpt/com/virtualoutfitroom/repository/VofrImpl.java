package fpt.com.virtualoutfitroom.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ResponseResult;
import fpt.com.virtualoutfitroom.webservice.CallBackData;
import fpt.com.virtualoutfitroom.webservice.ClientApi;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VofrImpl implements VofrRepository {

    @Override
    public void getListProduct(Context context,final CallBackData<List<Product>> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getListProduct();
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    if (response.code() == 200) {
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<Product>>>() {
                            }.getType();
                            ResponseResult<List<Product>> responseResult =
                                    new Gson().fromJson(result, type);

                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<Product> productList = responseResult.getData();

                                callBackData.onSuccess(productList);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callBackData.onFail("Tải dữ liệu thất bại!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Tải dữ liệu thất bại");
            }
        });
    }

    @Override
    public void checkLogin(Context context, String email, String password, final CallBackData<Account> callBackData) {
        ClientApi clientApi = new ClientApi();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("Email",email);
            jsonObject.put("Password",password);
        }catch (JSONException je){
            je.printStackTrace();
        }
            final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            Call<ResponseBody> serviceCall = clientApi.rmapService().checkLogin(requestBody);
            serviceCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200 && requestBody != null ){
                        String result = null;
                        try {
                            result = response.body().string();
                            Type type = new TypeToken<ResponseResult<Account>>(){}.getType();
                            ResponseResult<Account> responseResult = new Gson().fromJson(result, type);
                            if(responseResult != null){
                                Account account = responseResult.getData();
                                if(account.getAccessToken().length() != 0){
                                    callBackData.onSuccess(account);
                                }
                                else{
                                    callBackData.onFail("Not Found User");
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else {
                        callBackData.onFail("Error");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("Failure");

                }
            });


    }

    @Override
    public void getListCategory(Context context,final CallBackData<List<Category>> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getListCategory();
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    if (response.code() == 200) {
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<Category>>>() {
                            }.getType();
                            ResponseResult<List<Category>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<Category> categoryList = responseResult.getData();
                                callBackData.onSuccess(categoryList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getListSubCategory(Context context, int categoryId,final CallBackData<List<Category>> callbackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getListSubCategory(categoryId);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<Category>>>() {
                            }.getType();
                            ResponseResult<List<Category>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callbackData.onFail(response.message());
                            } else {
                                List<Category> categoryList = responseResult.getData();
                                callbackData.onSuccess(categoryList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getProductByCateId(Context context, int cateId,final CallBackData<List<Product>> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getListProduByCateId(cateId);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<Product>>>() {
                            }.getType();
                            ResponseResult<List<Product>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<Product> productList = responseResult.getData();
                                callBackData.onSuccess(productList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
