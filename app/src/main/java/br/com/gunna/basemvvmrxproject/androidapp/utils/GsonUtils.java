package br.com.gunna.basemvvmrxproject.androidapp.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Daniel on 06/12/17.
 */

public class GsonUtils {
    public static Object jsonToObject(String obj, Class<?> classModel) {
        Gson gson = new Gson();
        return gson.fromJson(obj, classModel);
    }

    public static Object jsonToObject(String obj, Type classModel) {
        Gson gson = new Gson();
        return gson.fromJson(obj, classModel);
    }

    public static String objectToString(Object obj) {
        try {
            if (obj == null) {
                return "";
            }
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception ex) {
            Log.e("ObjectToString", "objectToString: " + ex.getMessage().toString());
            return ex.getMessage();
        }
    }

}
