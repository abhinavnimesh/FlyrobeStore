package com.animator_abhi.flyrobestore.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by monisha on 31/03/16.
 */
public class PrefUtils {

    private static SharedPreferences mSharedPreferences;
    private static final String FILE_NAME = "com.flyrobe.android.fe.default.prefs";
    public static final String PREF_NAME = "MyPrefs" ;
    public static final String ORDER_ID = "OrderId" ;
    public static final String CUSTOMER_NAME = "CustomerName" ;
    public static final String DELIVERY_DATE = "deliveryDate" ;
    public static final String NO_OF_DAYS = "noOfDays";

    public static SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(FILE_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveHashList(Context context, List<String> arrayList, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        //Set the values
        Set<String> set = new HashSet<>();
        set.addAll(arrayList);
        editor.putStringSet(key, set);
        editor.commit();
    }

    public static void saveArrayList(Context context, List<String> arrayList, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        //Set the values
        Set<String> set = new HashSet<>();
        set.addAll(arrayList);
        editor.putStringSet(key, set);
        editor.commit();
    }

    public static void clearAppliedFilters(Context context) {
        saveArrayList(context, new ArrayList<String>(), Constants.PREF_PRICE_RESERVE);
        saveArrayList(context, new ArrayList<String>(), Constants.PREF_BRAND_RESERVE);
        saveArrayList(context, new ArrayList<String>(), Constants.PREF_CATEGORY_RESERVE);
    }

    public static List<String> retrieveArrayList(Context context, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        Set<String> set = new HashSet<>();
        set = preferences.getStringSet(key, null);
        List<String> list = new ArrayList<>();
        list.add("");

        if (set != null) {
            for (String str : set) {
                list.add(str);
            }
        }

        return list;
    }
}
