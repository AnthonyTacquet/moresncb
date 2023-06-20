package be.antwaan.moresncb;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.Enum.DepartureOrArrival;
import be.antwaan.moresncb.global.NMBS.Station;


public class HomeFragment extends Fragment {

    private TextInputEditText fromInput, toInput;
    private TextInputLayout fromLayout, toLayout;
    private TextView settingTime;
    private Dialog settingTimeDialog;
    private ArrayList<Station> stations;
    private LocalDateTime dateTime;
    private DepartureOrArrival departureOrArrival = DepartureOrArrival.DEPARTURE;
    private LocalDateTime selectedDateTime = LocalDateTime.now();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_home, container, false);
        fromLayout = fragView.findViewById(R.id.from_input_layout);
        fromInput = fragView.findViewById(R.id.from_input);
        toLayout = fragView.findViewById(R.id.to_input_layout);
        toInput = fragView.findViewById(R.id.to_input);
        settingTime = fragView.findViewById(R.id.setting_time);
        settingTimeDialog = new Dialog(getContext());

        dateTime = LocalDateTime.now();
        loadData(new NMBSData());

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
        RouteFragment newFragment = new RouteFragment(getContext(), departure, destination, dateTime, departureOrArrival);
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
        String dateString = readFromMemory("DateTime");
        String depArr = readFromMemory("DepArr");

        if (departure != null)
            fromInput.setText(stations.stream().filter(e -> e.getId().equals(departure)).findFirst().get().getName());
        if (destination != null)
            fromInput.setText(stations.stream().filter(e -> e.getId().equals(destination)).findFirst().get().getName());

        if (dateString != null && depArr != null){
            departureOrArrival = DepartureOrArrival.valueOf(depArr);
            dateTime = LocalDateTime.parse(dateString);
            closeDialog(null, null);
        } else
            closeDialog(null, null);


        if (departure != null && destination != null)
            navigateToRouteFragment(stations.stream().filter(e -> e.getId().equals(departure)).findFirst().get(),
                                    stations.stream().filter(e -> e.getId().equals(destination)).findFirst().get());

    }

    public void writeToMemory(LocalDateTime dateTime, DepartureOrArrival depArr){
        if (getContext() == null)
            return;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", getContext().MODE_PRIVATE);

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

    public String readFromMemory(String key){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPreferences", getContext().MODE_PRIVATE);

        String value = sharedPreferences.getString(key, null);
        return value;
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }


}