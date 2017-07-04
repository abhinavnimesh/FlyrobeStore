package com.animator_abhi.flyrobestore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by ANIMATOR ABHI on 16/03/2017.
 */


public class Prefs {


  //  private static final String IS_PERSISTENCE = "is_persistance";
    private static final String USER_ID = "userId";
    private static final String USERNAME = "userName";
    private static final String MOBILE = "mobile";
    private static final String STOREID = "storeId";
    private static final String CITY = "city";
    private static final String EMAIL = "email";
    private static final String MasterId = "masterId";
    private static final String MasterPass = "masterPass";





    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0);
    }

    //////////////////////////////////////////////////////////////////
 /*   public static boolean isPersisrence(Context context) {

        return getPrefs(context).getBoolean(IS_PERSISTENCE, false);
    }

    public static void setPersistence(Context context, boolean value) {
        getPrefs(context).edit().putBoolean(IS_PERSISTENCE, value).commit();
    }
*/


    @NonNull
    public static String getMasterId(Context context) {
        return getPrefs(context).getString(MasterId, "");
    }

    public static void setMasterId(Context context, String value) {
        getPrefs(context).edit().putString(MasterId, value).commit();
    }

    @NonNull
    public static String getMasterPass(Context context) {
        return getPrefs(context).getString(MasterPass, "");
    }

    public static void setMasterPass(Context context, String value) {
        getPrefs(context).edit().putString(MasterPass, value).commit();
    }


    @NonNull
    public static String getEmail(Context context) {
        return getPrefs(context).getString(EMAIL, "");
    }

    public static void setEmail(Context context, String value) {
        getPrefs(context).edit().putString(EMAIL, value).commit();
    }


    @NonNull
    public static String getUserId(Context context) {
        return getPrefs(context).getString(USER_ID, "");
    }

    public static void setUserId(Context context, String value) {
        getPrefs(context).edit().putString(USER_ID, value).commit();
    }

    @NonNull
    public static String getUSERNAME(Context context) {
        return getPrefs(context).getString(USERNAME, "");
    }

    public static void setUSERNAME(Context context, String value) {
        getPrefs(context).edit().putString(USERNAME, value).commit();
    }

    @NonNull
    public static String getMobile(Context context) {
        return getPrefs(context).getString(MOBILE, "");
    }

    public static void setMobile(Context context, String value) {
        getPrefs(context).edit().putString(MOBILE, value).commit();
    }

    public static String getStoreid(Context context) {
        return getPrefs(context).getString(STOREID, "");

    }

    public static void setStoreid(Context context, String value) {
        getPrefs(context).edit().putString(STOREID, value).commit();
    }
    public static String getCity(Context context) {
        return getPrefs(context).getString(CITY, "");

    }

    public static void setCity(Context context, String value) {
        getPrefs(context).edit().putString(CITY, value).commit();
    }
}
