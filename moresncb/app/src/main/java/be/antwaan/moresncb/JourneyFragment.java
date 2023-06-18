package be.antwaan.moresncb;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Vehicle;

public class JourneyFragment extends Fragment {

    private Context context;
    private Connection connection;
    private TextView departureTime, arrivalTime, departureLocation, destinationLocation, departurePlatform, destinationPlatform, trainLocation, alertMessage;
    private ImageView mapsImage, compositionImage;
    private LinearLayout alertLayout;

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

        departurePlatform = fragView.findViewById(R.id.platform_field_departure);
        destinationPlatform = fragView.findViewById(R.id.platform_field_destination);
        departureTime = fragView.findViewById(R.id.departure_time);
        arrivalTime = fragView.findViewById(R.id.arrival_time);
        departureLocation = fragView.findViewById(R.id.departure_field);
        destinationLocation = fragView.findViewById(R.id.arrival_field);
        trainLocation = fragView.findViewById(R.id.train_loc);
        mapsImage = fragView.findViewById(R.id.map_image);
        compositionImage = fragView.findViewById(R.id.composition_image);
        alertLayout = fragView.findViewById(R.id.alert_layout);
        alertMessage = fragView.findViewById(R.id.alert_text);

        mapsImage.setOnClickListener(v -> navigateToMapFragment(connection.getDeparture().getVehicle()));
        compositionImage.setOnClickListener(v -> navigateToInfoFragment(connection.getDeparture().getVehicle()));

        setFields();

        return fragView;
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

    private void setFields(){
        if (connection == null)
            return;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String depTime = sdf.format(connection.getDeparture().getDateTime());
        String arrTime = sdf.format(connection.getArrival().getDateTime());

        departurePlatform.setText("" + connection.getDeparture().getPlatform().getName());
        destinationPlatform.setText("" + connection.getArrival().getPlatform().getName());
        departureLocation.setText("" + connection.getDeparture().getStationName());
        destinationLocation.setText("" + connection.getArrival().getStationName());
        departureTime.setText(depTime);
        arrivalTime.setText(arrTime);
        trainLocation.setText(connection.getDeparture().getVehicle().getShortName() + " to " + connection.getArrival().getDirection());


        if (connection.getAlerts().size() >= 1){
            Alert alert = connection.getAlerts().get(0);

            alertMessage.setText(alert.getHeader());
        } else {
            alertLayout.setVisibility(View.GONE);
        }
    }
}