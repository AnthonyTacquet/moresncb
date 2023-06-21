package be.antwaan.moresncb.logica;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
}
