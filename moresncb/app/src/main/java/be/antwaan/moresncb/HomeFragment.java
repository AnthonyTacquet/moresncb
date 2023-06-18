package be.antwaan.moresncb;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.ArrayList;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Station;


public class HomeFragment extends Fragment {

    private TextInputEditText fromInput, toInput;
    private TextInputLayout fromLayout, toLayout;
    private ArrayList<Station> stations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_home, container, false);
        fromLayout = fragView.findViewById(R.id.from_input_layout);
        fromInput = fragView.findViewById(R.id.from_input);
        toLayout = fragView.findViewById(R.id.to_input_layout);
        toInput = fragView.findViewById(R.id.to_input);

        loadData(new NMBSData());

        fromLayout.getEditText().setOnTouchListener((v, event) -> {
            navigateToSearchFragment("Departure");
            return true;
        });


        toLayout.getEditText().setOnTouchListener((v, event) -> {
            navigateToSearchFragment("Destination");
            return true;
        });

        return fragView;
    }

    private void navigateToSearchFragment(String name) {
        if (stations == null)
            return;
        SearchFragment newFragment = new SearchFragment(getContext(), name, stations);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToRouteFragment(Station departure, Station destination) {
        RouteFragment newFragment = new RouteFragment(getContext(), departure, destination);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadData(NMBSData nmbsData){
        Thread thread = new Thread(() -> {
            try {
                stations = nmbsData.GetStations();
                getActivity().runOnUiThread(() -> loadMemory());
            } catch (JSONException e) {
                showToast(e.getMessage());
            }
        });
        thread.start();
    }

    private void loadMemory(){
        String departure = readFromMemory("Departure");
        String destination = readFromMemory("Destination");

        if (departure != null)
            fromInput.setText(stations.stream().filter(e -> e.getId().equals(departure)).findFirst().get().getName());
        if (destination != null)
            fromInput.setText(stations.stream().filter(e -> e.getId().equals(destination)).findFirst().get().getName());

        if (departure != null && destination != null)
            navigateToRouteFragment(stations.stream().filter(e -> e.getId().equals(departure)).findFirst().get(),
                                    stations.stream().filter(e -> e.getId().equals(destination)).findFirst().get());

    }

    public String readFromMemory(String key){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", getContext().MODE_PRIVATE);

        String value = sharedPreferences.getString(key, null);
        return value;
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }


}