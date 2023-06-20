package be.antwaan.moresncb;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.Enum.DepartureOrArrival;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.adapter.RouteAdapter;

public class RouteFragment extends Fragment {

    private Station departure, destination;
    private TextInputEditText fromInput, toInput;
    private TextView settingTime;
    private TextInputLayout fromLayout, toLayout;
    private Context context;
    private ArrayList<Connection> connections;
    private ProgressBar progressBar;
    private ListView listView;
    private RouteAdapter routeAdapter;
    private ImageView backView;
    private int results = 6;
    private LocalDateTime dateTime = LocalDateTime.now();
    private LocalDateTime selectedDateTime = LocalDateTime.now();

    private DepartureOrArrival departureOrArrival = DepartureOrArrival.DEPARTURE;
    private Dialog settingTimeDialog;
    private ArrayList<Station> stations = new ArrayList<>();

    public RouteFragment(){
    }

    public RouteFragment(Context context, Station departure, Station destination, LocalDateTime localDateTime, DepartureOrArrival departureOrArrival){
        this.departure = departure;
        this.destination = destination;
        this.context = context;
        this.dateTime = localDateTime;
        this.departureOrArrival = departureOrArrival;

        writeToMemory("Departure", null);
        writeToMemory("Destination", null);
        writeToMemory(null, DepartureOrArrival.DEPARTURE);

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
        backView = fragView.findViewById(R.id.back_image);
        settingTime = fragView.findViewById(R.id.setting_time);
        settingTimeDialog = new Dialog(getContext());

        connections = new ArrayList<>();
        routeAdapter = new RouteAdapter(getContext(), connections);
        listView.setAdapter(routeAdapter);

        setFields();
        closeDialog(null, null);
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

        backView.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        fromLayout.getEditText().setOnTouchListener((v, event) -> {
            navigateToSearchFragment("Departure");
            return true;
        });


        toLayout.getEditText().setOnTouchListener((v, event) -> {
            navigateToSearchFragment("Destination");
            return true;
        });

        settingTime.setOnClickListener(v -> showTimeDialog(fragView));

        return fragView;
    }

    private void showTimeDialog(View view){
        TextView cancelView, submitView, dayView;
        ImageView nextDayButton, backDayButton;
        Button nowButton, fifteenButton, oneButton;
        NumberPicker hourPicker, minutePicker;
        TabLayout tabLayout;

        settingTimeDialog.setContentView(R.layout.settings_window);

        cancelView = settingTimeDialog.findViewById(R.id.cancel_button);
        submitView = settingTimeDialog.findViewById(R.id.submit_button);
        hourPicker = settingTimeDialog.findViewById(R.id.hour_picker);
        minutePicker = settingTimeDialog.findViewById(R.id.minute_picker);
        dayView = settingTimeDialog.findViewById(R.id.day_view);
        nextDayButton = settingTimeDialog.findViewById(R.id.day_next_button);
        backDayButton = settingTimeDialog.findViewById(R.id.day_back_button);
        nowButton = settingTimeDialog.findViewById(R.id.now_button);
        fifteenButton = settingTimeDialog.findViewById(R.id.fifteen_min_button);
        oneButton = settingTimeDialog.findViewById(R.id.one_hour_button);
        tabLayout = settingTimeDialog.findViewById(R.id.tab_layout_dep_arr);

        hourPicker.setValue(dateTime.getHour());
        minutePicker.setValue(dateTime.getMinute());
        DateTimeFormatter dayMonthYearFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dayView.setText(selectedDateTime.format(dayMonthYearFormatter));


        nextDayButton.setOnClickListener(v -> {
            selectedDateTime = selectedDateTime.plusDays(1);
            dayView.setText(selectedDateTime.format(dayMonthYearFormatter));
        });

        backDayButton.setOnClickListener(v -> {
            selectedDateTime = selectedDateTime.minusDays(1);
            dayView.setText(selectedDateTime.format(dayMonthYearFormatter));
        });

        cancelView.setOnClickListener(v -> closeDialog(dateTime, null));

        submitView.setOnClickListener(v -> {
            dateTime = LocalDateTime.of(selectedDateTime.getYear(), selectedDateTime.getMonthValue(), selectedDateTime.getDayOfMonth(), hourPicker.getValue(), minutePicker.getValue());
            departureOrArrival = tabIndex(tabLayout);
            closeDialog(dateTime, departureOrArrival);
        });

        nowButton.setOnClickListener(v -> {
            departureOrArrival = tabIndex(tabLayout);
            dateTime = LocalDateTime.now();
            closeDialog(dateTime, departureOrArrival);
        });

        oneButton.setOnClickListener(v -> {
            departureOrArrival = tabIndex(tabLayout);
            dateTime = LocalDateTime.now().plusMinutes(60);
            closeDialog(dateTime, departureOrArrival);
        });

        fifteenButton.setOnClickListener(v -> {
            departureOrArrival = tabIndex(tabLayout);
            dateTime = LocalDateTime.now().plusMinutes(15);
            closeDialog(dateTime, departureOrArrival);
        });

        settingTimeDialog.show();
    }

    private void closeDialog(LocalDateTime localDateTime, DepartureOrArrival depArr){
        if (depArr == null)
            depArr = departureOrArrival;
        String enumString = depArr.name().toLowerCase();

        String formattedString = Character.toUpperCase(enumString.charAt(0)) + enumString.substring(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        if (localDateTime == null)
            localDateTime = dateTime;
        String dateString = localDateTime.format(formatter);

        settingTime.setText(formattedString + ": " + dateString);
        writeToMemory(localDateTime, depArr);
        settingTimeDialog.dismiss();
    }
    private DepartureOrArrival tabIndex(TabLayout tabLayout){
        if (tabLayout.getSelectedTabPosition() == 0)
            return DepartureOrArrival.DEPARTURE;
        return DepartureOrArrival.ARRIVAL;
    }

    private void navigateToSearchFragment(String name) {
        writeToMemory("Departure", departure);
        writeToMemory("Destination", destination);
        writeToMemory(dateTime, departureOrArrival);

        SearchFragment newFragment = new SearchFragment(getContext(), name, stations);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
                stations.clear();
                connections.addAll(nmbsData.GetConnections(departure, destination, dateTime, departureOrArrival, results));
                stations.addAll(nmbsData.GetStations());

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

    private void loadMemory(){
        String dateString = readFromMemory("DateTime");
        String depArr = readFromMemory("DepArr");

        if (dateString != null && depArr != null)
            closeDialog(LocalDateTime.parse(dateString), DepartureOrArrival.valueOf(depArr));
        else
            closeDialog(null, null);

    }

    public String readFromMemory(String key){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", getContext().MODE_PRIVATE);

        String value = sharedPreferences.getString(key, null);
        return value;
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

    public void writeToMemory(LocalDateTime dateTime, DepartureOrArrival depArr){
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (dateTime != null)
            editor.putString("DateTime", dateTime.toString());
        else
            editor.putString("DateTime", null);

        if (depArr != null)
            editor.putString("DepArr", depArr.name());
        else
            editor.putString("DepArr", null);

        editor.apply();
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

}