package be.antwaan.moresncb;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import be.antwaan.moresncb.logica.Memory;
import be.antwaan.moresncb.logica.adapter.JourneyAdapter;
import be.antwaan.moresncb.logica.adapter.RouteAdapter;

public class JourneyFragment extends Fragment {

    private Context context;
    private Connection connection;
    private TextView alertMessage;
    private ImageView mapsImage, compositionImage, starImage, backButton;
    private LinearLayout alertLayout;
    private RecyclerView journeyList;
    private List<Pair<Departure, Arrival>> list;
    private JourneyAdapter journeyAdapter;
    private Memory memory;
    private ArrayList<Connection> favorites = new ArrayList<>();

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

        memory = new Memory(requireContext());
        favorites = memory.readFromConnectionMemory();

        alertLayout = fragView.findViewById(R.id.alert_layout);
        alertMessage = fragView.findViewById(R.id.alert_text);
        mapsImage = fragView.findViewById(R.id.map_image);
        compositionImage = fragView.findViewById(R.id.composition_image);
        journeyList = fragView.findViewById(R.id.journey_list);
        starImage = fragView.findViewById(R.id.star_image);
        backButton = fragView.findViewById(R.id.back_image);

        list = new ArrayList<>();
        journeyAdapter = new JourneyAdapter(context, list);
        journeyList.setLayoutManager(new LinearLayoutManager(context));
        journeyList.setAdapter(journeyAdapter);

        checkStar();

        mapsImage.setOnClickListener(v -> navigateToMapFragment(connection.getDeparture().getVehicle()));
        compositionImage.setOnClickListener(v -> navigateToInfoFragment(connection.getDeparture().getVehicle()));

        fill();

        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        starImage.setOnClickListener(v -> {
            if (favorites.contains(connection)){
                favorites.remove(connection);
                memory.removeConnectionFromMemory(connection);

                int colorRes = R.color.white;
                int color = ContextCompat.getColor(context, colorRes);
                starImage.setColorFilter(color);

                int drawableRes = R.drawable.star_regular;
                starImage.setImageResource(drawableRes);
            } else {
                int colorRes = R.color.gold;
                int color = ContextCompat.getColor(context, colorRes);
                starImage.setColorFilter(color);

                int drawableRes = R.drawable.star_solid;
                starImage.setImageResource(drawableRes);

                memory.writeToConnectionMemory(connection);
            }
        });

        return fragView;
    }

    private void checkStar(){
        if (favorites.contains(connection)) {
            int colorRes = R.color.gold;
            int color = ContextCompat.getColor(context, colorRes);
            starImage.setColorFilter(color);

            int drawableRes = R.drawable.star_solid;
            starImage.setImageResource(drawableRes);
        } else {
            int colorRes = R.color.white;
            int color = ContextCompat.getColor(context, colorRes);
            starImage.setColorFilter(color);

            int drawableRes = R.drawable.star_regular;
            starImage.setImageResource(drawableRes);
        }
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