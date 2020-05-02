package fpt.com.virtualoutfitroom.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.OrderHistory;
import fpt.com.virtualoutfitroom.model.OrderItem;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.model.ResponseResult;
import fpt.com.virtualoutfitroom.room.AccountItemEntities;
import fpt.com.virtualoutfitroom.room.OrderItemEntities;
import fpt.com.virtualoutfitroom.sockets.SocketServer;
import fpt.com.virtualoutfitroom.utils.BundleString;
import fpt.com.virtualoutfitroom.utils.GetPathFile;
import fpt.com.virtualoutfitroom.utils.RealPathUtils;
import fpt.com.virtualoutfitroom.utils.SharePreferenceUtils;
import fpt.com.virtualoutfitroom.webservice.CallBackData;
import fpt.com.virtualoutfitroom.webservice.ClientApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VofrImpl implements VofrRepository {
    private SocketServer socketServer;


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
                        } catch (Exception e) {
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
    public void getProduct(Context context,int productId, CallBackData<Product> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getProduct(productId);
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
                                List<Product> product = responseResult.getData();
                                callBackData.onSuccess(product.get(0));
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
                                    callBackData.onFail("Không tìm thấy tài khoản");
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

    @Override
    public void getListImage(Context context, int productId, String imageType,final CallBackData<List<ProductImage>> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getListImage(productId,imageType);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<ProductImage>>>(){}.getType();
                            ResponseResult<List<ProductImage>> responseResult = new Gson().fromJson(result,type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<ProductImage> productImages = responseResult.getData();
                                callBackData.onSuccess(productImages);
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
    public void getInforAccount(Context context,String accountID, CallBackData<Account> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().getInforAccount(accountID);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<Account>>>() {
                            }.getType();
                            ResponseResult<List<Account>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                Account account = responseResult.getData().get(0);
                                callBackData.onSuccess(account);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callBackData.onFail("Lấy thông tin không thành công");
            }
        });
    }

    @Override
    public void updateInforAccount(Context context, String token,Account account, CallBackData<String> callBackData) {
        ClientApi clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account_id",account.getAccountId());
            jsonObject.put("first_name",account.getFirstName());
            jsonObject.put("last_name",account.getLastName());
            jsonObject.put("email",account.getEmail());
            jsonObject.put("address",account.getAddress());
            jsonObject.put("phone_number",account.getPhoneNumber());
            jsonObject.put("username",account.getUserName());
            jsonObject.put("password",account.getPassword());
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.rmapService().updateaInfoAccount(map,body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult>() {
                            }.getType();
                            ResponseResult responseResult =
                                    new Gson().fromJson(result, type);
                            callBackData.onSuccess(responseResult.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Cập nhật thông tin không thành công");
            }
        });
    }
    @Override
    public void updateImage(Context context, String token,String userId, Uri imageUri, CallBackData<String> callBackData) {
        ClientApi  clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        File file = new File(GetPathFile.getPath(context, imageUri));
        RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), fbody);
        Call<ResponseBody> serviceCall = clientApi.rmapService().updateaImage(map,userId,image);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult>() {
                            }.getType();
                            ResponseResult responseResult =
                                    new Gson().fromJson(result, type);
                            callBackData.onSuccess(responseResult.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Khong thanh cong");
            }
        });
    }
    @Override
    public void createOrder(Context context,OrderHistory order,String token, List<OrderItemEntities> orderItemEntities, CallBackData<String> callBackData) {


        ClientApi  clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        JSONObject jsonObject = new JSONObject();
        try {
            String accountId = SharePreferenceUtils.getStringSharedPreference(context, BundleString.USERID);
            jsonObject.put("total",order.getTotal());
            jsonObject.put("account_id",accountId);
            jsonObject.put("full_name",order.getFull_name());
            jsonObject.put("email",order.getEmail());
            jsonObject.put("address",order.getAddress());
            jsonObject.put("phone_number",order.getPhone_number());
            jsonObject.put("method",order.getMethod());
            jsonObject.put("description",order.getDescription());
            JSONArray  jsonArray = new JSONArray();
            for (int i = 0; i <orderItemEntities.size() ; i++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("price",orderItemEntities.get(i).getProduct().getProductPrice());
                jsonObject1.put("quantity",orderItemEntities.get(i).getQuality());
                jsonObject1.put("product_id",orderItemEntities.get(i).getProduct().getId());
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("order_items",jsonArray);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.rmapService().createOrder(map,body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult>() {
                            }.getType();
                            ResponseResult responseResult =
                                    new Gson().fromJson(result, type);
                            callBackData.onSuccess(responseResult.getMessage());
                            socketServer = new SocketServer();
                            socketServer.connect();
                            socketServer.emitOrder(jsonObject);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("F", "Msg: " + t.getMessage());
            }
        });

    }
    @Override
    public void getOrder(Context context, String token, String accountId, CallBackData<List<OrderHistory>> callBackData) {
        ClientApi  clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        Call<ResponseBody> serviceCall = clientApi.rmapService().getOrder(accountId);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<OrderHistory>>>() {
                            }.getType();
                            ResponseResult<List<OrderHistory>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<OrderHistory> orderHistoryList = responseResult.getData();
                                callBackData.onSuccess(orderHistoryList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            callBackData.onFail("Lấy thông tin không thành công");
            }
        });
    }

    @Override
    public void getOrderItem(Context context, String token, String orderId, CallBackData<List<OrderItem>> callBackData) {
        ClientApi  clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        Call<ResponseBody> serviceCall = clientApi.rmapService().getOrderItem(orderId);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<List<OrderItem>>>() {
                            }.getType();
                            ResponseResult<List<OrderItem>> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail(response.message());
                            } else {
                                List<OrderItem> orderItemHistoryList = responseResult.getData();
                                callBackData.onSuccess(orderItemHistoryList);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Get data fail");
            }
        });
    }

    @Override
    public void searchProduct(Context context, String productName, CallBackData<List<Product>> callBackData) {
        ClientApi  clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().searchProduct(productName);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
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
                else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Get data fail");
            }
        });

    }

    @Override
    public void createUser(Context context, Account account, CallBackData<String> callBackData) {
        ClientApi  clientApi = new ClientApi();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email", account.getEmail());
            jsonObject.put("phone_number", account.getPhoneNumber());
            jsonObject.put("role_id", account.getRoleId());
            jsonObject.put("username",account.getUserName());
            jsonObject.put("password", account.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.rmapService().createUser(body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                        if(response.code() == 200){
                            try {
                                String result = response.body().string();
                                Type type = new TypeToken<ResponseResult>() {
                                }.getType();
                                ResponseResult responseResult =
                                        new Gson().fromJson(result, type);
                                callBackData.onSuccess(responseResult.getMessage());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                }else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Create user fail");
            }
        });
    }

    @Override
    public void forgotPassword(Context context, String username, String email, CallBackData<String> callBackData) {
        ClientApi  clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.rmapService().forgotPassword(username,email);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult>() {
                            }.getType();
                            ResponseResult responseResult =
                                    new Gson().fromJson(result, type);
                            callBackData.onSuccess(responseResult.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("update data fail");
            }
        });
    }

    @Override
    public void changePassword(Context context, String password, String passwordNew, CallBackData<Account> callBackData) {
        String token = SharePreferenceUtils.getStringSharedPreference(context, BundleString.TOKEN);
        ClientApi  clientApi = new ClientApi();
        String hearder = "Bearer " + token;
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", hearder);
        Call<ResponseBody> serviceCall = clientApi.rmapService().changePassword(map,password,passwordNew);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null && response.body() != null){
                    if(response.code() == 200){
                        try{
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<Account>>() {
                            }.getType();
                            ResponseResult<Account> responseResult =
                                    new Gson().fromJson(result, type);
                            if (responseResult == null) {
                                callBackData.onFail("Không có user trả về");
                            } else {
                                Account account = responseResult.getData();
                                callBackData.onSuccess(account);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail("Lỗi");
            }
        });
    }
}
