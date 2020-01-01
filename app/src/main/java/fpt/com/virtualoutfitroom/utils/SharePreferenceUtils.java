package fpt.com.virtualoutfitroom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpt.com.virtualoutfitroom.model.ListOfSomething;
import fpt.com.virtualoutfitroom.model.Product;

public class SharePreferenceUtils {

    public static void saveStringSharedPreference(Context context, String key, String value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getStringSharedPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        String defaultValue = "";
        return prefs.getString(key, defaultValue);
    }

    public static void removeStringSharedPreference(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        }catch (Exception e){

        }
    }

    public static void saveIntSharedPreference(Context context, String key, int value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(key, value);
            editor.commit();
        }catch (Exception e){

        }
    }
    public static void saveFloatSharedPreference(Context context, String key, float value) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(key, value);
            editor.commit();
        }catch (Exception e){

        }
    }
    public static <T> void saveListObjectSharedPreference(Context context,String key, List<T> list){
        try{
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(list);
            editor.putString(key, json);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static<T> List<T> getListObjectSharedPreference(Context context, String key,Class<T> clazz){
        Gson gson = new Gson();
        List<T> productFromShared = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        String jsonPreferences = prefs.getString(key, null);
        //Type type = new TypeToken<List<T>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, new ListOfSomething<T>(clazz));
        return  productFromShared;
    }
    public static void removeFloatSharedPreference(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        } catch (Exception e) {

        }
    }
    public static float getFloatSharedPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        float defaultValue = 0;
        return prefs.getFloat(key, defaultValue);
    }
    public static int getIntSharedPreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        int defaultValue = 0;
        return prefs.getInt(key, defaultValue);
    }

    public static void removeIntSharedPreference(Context context, String key) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(
                    context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.commit();
        }catch (Exception e){

        }
    }
}
