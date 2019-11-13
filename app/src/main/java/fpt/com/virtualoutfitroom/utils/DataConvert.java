package fpt.com.virtualoutfitroom.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import fpt.com.virtualoutfitroom.model.Account;
import fpt.com.virtualoutfitroom.model.Product;

public class DataConvert {
    @TypeConverter // note this annotation
    public String fromFoodList(Product product) {
        if (product == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Product>() {
        }.getType();
        String json = gson.toJson(product, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Product toExtraList(String productString) {
        if (productString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Product>() {
        }.getType();
        Product cardType = gson.fromJson(productString, type);
        return cardType;
    }
    @TypeConverter // note this annotation
    public String fromAccountObj(Account account) {
        if (account == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Account>() {
        }.getType();
        String json = gson.toJson(account, type);
        return json;
    }
    @TypeConverter // note this annotation
    public Account toAccountString(String accountString) {
        if (accountString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Account>() {
        }.getType();
        Account accountType = gson.fromJson(accountString, type);
        return accountType;
    }
}
