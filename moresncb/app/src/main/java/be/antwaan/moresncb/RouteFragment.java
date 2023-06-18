package be.antwaan.moresncb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.antwaan.moresncb.global.NMBS.Station;

public class RouteFragment extends Fragment {

    private Station departure, destination;
    private Context context;

    public RouteFragment(Context context, Station departure, Station destination){
        this.departure = departure;
        this.destination = destination;
        this.context = context;

        writeToMemory("Departure", null);
        writeToMemory("Destination", null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_route, container, false);




        return fragView;
    }

    public void writeToMemory(String key, Station station){
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (station != null)
            editor.putString(key, station.getId());
        else
            editor.putString(key, null);

        editor.apply();
    }

}