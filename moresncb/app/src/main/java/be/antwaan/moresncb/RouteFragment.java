package be.antwaan.moresncb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.Enum.DepartureOrArrival;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.adapter.RouteAdapter;

public class RouteFragment extends Fragment {

    private Station departure, destination;
    private TextInputEditText fromInput, toInput;
    private TextInputLayout fromLayout, toLayout;
    private Context context;
    private ArrayList<Connection> connections;
    private ProgressBar progressBar;
    private ListView listView;
    private RouteAdapter routeAdapter;
    private int results = 6;

    public RouteFragment(){
    }

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

        if(context == null)
            context = requireContext();

        progressBar = fragView.findViewById(R.id.progressBar);
        listView = fragView.findViewById(R.id.route_list);
        fromLayout = fragView.findViewById(R.id.from_input_layout_route);
        fromInput = fragView.findViewById(R.id.from_input_route);
        toLayout = fragView.findViewById(R.id.to_input_layout_route);
        toInput = fragView.findViewById(R.id.to_input_route);

        connections = new ArrayList<>();
        routeAdapter = new RouteAdapter(getContext(), connections);
        listView.setAdapter(routeAdapter);

        setFields();
        progressBar.setVisibility(View.VISIBLE);
        loadData(new NMBSData());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (connections == null)
                return;

            try {
                Connection connection = connections.get(position);
                if (connection == null)
                    return;
                navigateToJourneyFragment(connection);
            } catch (RuntimeException e){
                showToast(e.getMessage());
            }

        });

        return fragView;
    }

    private void navigateToJourneyFragment(Connection connection) {
        JourneyFragment newFragment = new JourneyFragment(getContext(), connection);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setFields(){

        if (departure != null)
            fromInput.setText(departure.getName());
        if (destination != null)
            toInput.setText(destination.getName());

        //if (departure != null && destination != null)

    }

    private void loadData(NMBSData nmbsData){
        Thread thread = new Thread(() -> {
            try {
                connections.clear();
                connections.addAll(nmbsData.GetConnections(departure, destination, LocalDateTime.now(), DepartureOrArrival.DEPARTURE, results));

                getActivity().runOnUiThread(() -> {
                    routeAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
            } catch (JSONException e) {
                showToast(e.getMessage());
            }

        });
        thread.start();
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

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

}