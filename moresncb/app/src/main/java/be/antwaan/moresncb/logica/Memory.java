package be.antwaan.moresncb.logica;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;

public class Memory {
    private Context context;
    private static final String PREFS_NAME = "MyPreferences";

    public Memory(Context context){
        this.context = context;
    }

    public void writeToMemory(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);

        editor.apply();
    }

    public String readFromMemory(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void writeToConnectionMemory(Connection connection){

        List<Connection> connectionList = readFromConnectionMemory();
        if (connectionList == null)
            connectionList = new ArrayList<>();

        HashSet<Connection> mylist = new HashSet<>();
        mylist.add(connection);
        mylist.addAll(connectionList);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(mylist);
        editor.putString("ListConnection", json);
        editor.apply();
    }

    public void removeConnectionFromMemory(Connection connection){

        List<Connection> connectionList = readFromConnectionMemory();
        if (connectionList == null)
            connectionList = new ArrayList<>();

        connectionList.remove(connection);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(connectionList);
        editor.putString("ListConnection", json);
        editor.apply();
    }


    public ArrayList<Connection> readFromConnectionMemory(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("ListConnection", null);
        if (json == null)
            return new ArrayList<>();
        return new Gson().fromJson(json, new TypeToken<List<Connection>>() {}.getType());

    }

    public void writeToListMemory(Station station){

        List<Station> stationsList = readListFromMemory();
        if (stationsList == null)
            stationsList = new ArrayList<>();

        HashSet<Station> mylist = new HashSet<>();
        mylist.add(station);
        if (stationsList.size() < 3)
            mylist.addAll(stationsList);
        else
            mylist.addAll(stationsList.subList(0, 3));

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(mylist);
        editor.putString("ListStation", json);
        editor.apply();
    }

    public List<Station> readListFromMemory(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("ListStation", null);
        if (json == null)
            return null;
        return new Gson().fromJson(json, new TypeToken<List<Station>>() {}.getType());

    }

    public void writeToShortcutMemory(Station station){

        List<Station> stationsList = readShortcutsFromMemory();
        if (stationsList == null)
            stationsList = new ArrayList<>();

        HashSet<Station> mylist = new HashSet<>();
        mylist.add(station);
        mylist.addAll(stationsList);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(mylist);
        editor.putString("ShortcutList", json);
        editor.apply();
    }

    public List<Station> readShortcutsFromMemory(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("ShortcutList", null);
        if (json == null)
            return new ArrayList<>();
        return new Gson().fromJson(json, new TypeToken<List<Station>>() {}.getType());

    }

    public void removeShortcutFromMemory(Station station){

        List<Station> stationList = readShortcutsFromMemory();
        if (stationList == null)
            stationList = new ArrayList<>();

        stationList.remove(station);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(stationList);
        editor.putString("ShortcutList", json);
        editor.apply();
    }
}
