package com.moin.covid19tracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Util {

    public static boolean isAppOnLine(Context context) {
        Log.d("Util", " in isAppOnLine");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // INDIA STATS

    private static List<String[]> time_series;  // india

    public static List<String[]> getTimeSeries() {
        if (time_series == null) {
            time_series = new ArrayList<>();
        }
        return time_series;
    }

    // ARRAYLIST FROM MAP

    private static ArrayList<String[]> getArrayListFromHAshMap(HashMap<String, String> map) {
        HashMap<String, String> newMap = new HashMap<>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            String newKey = "";
            String s = "";
            int i = 0;
            while (key.charAt(i) != '/') {
                s += key.charAt(i);
                i++;
            }
            if (s.length() == 1) {
                newKey = "0";
            }
            newKey += s + "/";
            i++;
            s = "";
            while (key.charAt(i) != '/') {
                s += key.charAt(i);
                i++;
            }
            if (s.length() == 1) {
                newKey += "0";
            }
            newKey += s;
            while (i < key.length()) {
                newKey += key.charAt(i);
                i++;
            }
            newMap.put(newKey, value);
        }

        ArrayList<String[]> list = new ArrayList<>();

        Map<String, String> sortedMap = new TreeMap<String, String>(newMap);
        Set<Map.Entry<String, String>> setOfEntries = sortedMap.entrySet();
        for (Map.Entry<String, String> entry : setOfEntries) {
            String[] str = new String[2];
            str[0] = entry.getKey();
            str[1] = entry.getValue();
            list.add(str);
        }

        return list;
    }

    // WORLD STATS

    private static ArrayList<String[]> worldConfirmedList;
    private static ArrayList<String[]> worldRecoveredList;
    private static ArrayList<String[]> worldDeathsList;

    public static void setWorldConfirmedList(HashMap<String, String> confirmedMap) {
        worldConfirmedList = Util.getArrayListFromHAshMap(confirmedMap);
    }

    public static void setWorldRecoveredList(HashMap<String, String> recoveredMap) {
        worldRecoveredList = Util.getArrayListFromHAshMap(recoveredMap);
    }

    public static void setWorldDeathsList(HashMap<String, String> deathsMap) {
        worldDeathsList = Util.getArrayListFromHAshMap(deathsMap);
    }

    public static ArrayList<String[]> getWorldConfirmedList() {
        return worldConfirmedList;
    }

    public static ArrayList<String[]> getWorldRecoveredList() {
        return worldRecoveredList;
    }

    public static ArrayList<String[]> getWorldDeathsList() {
        return worldDeathsList;
    }

    // WORLD STATS OVER OR NOT

    private static boolean isWorldStatsDone = false;

    public static boolean getIsWorldStatsDone() {
        return isWorldStatsDone;
    }

    public static void setIsWorldStatsDone(boolean isWorldStatsDone) {
        Util.isWorldStatsDone = isWorldStatsDone;
    }

    // COUNTRY STATS

    private static String countryName;
    private static ArrayList<String[]> countryConfirmedList;
    private static ArrayList<String[]> countryRecoveredList;
    private static ArrayList<String[]> countryDeathsList;

    public static void setCountryConfirmedList(HashMap<String, String> confirmedMap) {
        if (confirmedMap!=null) {
            countryConfirmedList = Util.getArrayListFromHAshMap(confirmedMap);
        } else {
            countryConfirmedList = null;
        }
    }

    public static void setCountryRecoveredList(HashMap<String, String> recoveredMap) {
        if (recoveredMap!=null) {
            countryRecoveredList = Util.getArrayListFromHAshMap(recoveredMap);
        } else {
            countryRecoveredList = null;
        }
    }

    public static void setCountryDeathsList(HashMap<String, String> deathsMap) {
        if (deathsMap!=null) {
            countryDeathsList = Util.getArrayListFromHAshMap(deathsMap);
        } else {
            countryDeathsList = null;
        }
    }

    public static ArrayList<String[]> getCountryConfirmedList() {
        return countryConfirmedList;
    }

    public static ArrayList<String[]> getCountryRecoveredList() {
        return countryRecoveredList;
    }

    public static ArrayList<String[]> getCountryDeathsList() {
        return countryDeathsList;
    }

    public static String getCountryName() {
        return countryName;
    }

    public static void setCountryName(String countryName) {
        Util.countryName = countryName;
    }

    // ADD COMMAS IN NUMBERS

    public static String addCommas(String number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);
        return number == null ? null : numberFormat.format(Float.parseFloat(number));
    }
}
