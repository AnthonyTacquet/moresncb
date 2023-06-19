package be.antwaan.moresncb;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Arrival;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Departure;
import be.antwaan.moresncb.global.NMBS.Vehicle;
import be.antwaan.moresncb.logica.adapter.JourneyAdapter;

public class JourneyFragment extends Fragment {

    private Context context;
    private Connection connection;
    private TextView alertMessage;
    private ImageView mapsImage, compositionImage;
    private LinearLayout alertLayout;
    private ListView journeyList;
    private List<Pair<Departure, Arrival>> list;
    private JourneyAdapter journeyAdapter;

    public JourneyFragment(){}

    public JourneyFragment(Context context, Connection connection){
        this.context = context;
        this.connection = connection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_journey, container, false);

        if (context == null)
            context = requireContext();


        alertLayout = fragView.findViewById(R.id.alert_layout);
        alertMessage = fragView.findViewById(R.id.alert_text);
        mapsImage = fragView.findViewById(R.id.map_image);
        compositionImage = fragView.findViewById(R.id.composition_image);
        journeyList = fragView.findViewById(R.id.journey_list);

        list = new ArrayList<>();
        journeyAdapter = new JourneyAdapter(context, list);
        journeyList.setAdapter(journeyAdapter);

        mapsImage.setOnClickListener(v -> navigateToMapFragment(connection.getDeparture().getVehicle()));
        compositionImage.setOnClickListener(v -> navigateToInfoFragment(connection.getDeparture().getVehicle()));

        fill();

        return fragView;
    }

    private void fill(){
        if (connection == null)
            return;
        if (connection.getAlerts().size() >= 1){
            Alert alert = connection.getAlerts().get(0);

            alertMessage.setText(alert.getHeader());
        } else {
            alertLayout.setVisibility(View.GONE);
        }

        if (connection.getVias() == null || connection.getVias().size() == 0){
            list.add(new Pair<>(connection.getDeparture(), connection.getArrival()));
        } else {
            list.add(new Pair<>(connection.getDeparture(), connection.getVias().get(0).getArrival()));
            for (int i = 0; i < connection.getVias().size(); i++){
                if (i > 0)
                    list.add(new Pair<>(connection.getVias().get(i - 1).getDeparture(), connection.getVias().get(i - 1).getArrival()));
            }
            list.add(new Pair<>(connection.getVias().get(connection.getVias().size() - 1).getDeparture(), connection.getArrival()));

        }
    }

    private void navigateToInfoFragment(Vehicle vehicle) {
        InfoFragment newFragment = new InfoFragment(getContext(), vehicle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToMapFragment(Vehicle vehicle) {
        MapFragment newFragment = new MapFragment(getContext(), vehicle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}