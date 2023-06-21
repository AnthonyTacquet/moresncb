package be.antwaan.moresncb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.Memory;
import be.antwaan.moresncb.logica.adapter.IconAdapter;
import be.antwaan.moresncb.logica.adapter.StationAdapter;

public class SearchFragment extends Fragment {

    private ListView listView;
    private TextInputEditText input;
    private TextInputLayout layout;
    private List<Station> stations;
    private ArrayList<Station> allStations;

    private StationAdapter stationAdapter;
    private ProgressBar progressBar;
    private TextView depArr;
    private ImageView backImage;
    private InputMethodManager inputMethodManager;
    private String name = "";
    private Context context;

    private Memory memory;
    public SearchFragment(){
        allStations = new ArrayList<>();
        name = "Destination";

    }
    public SearchFragment(Context context, String name, ArrayList<Station> stations){
        this.name = name;
        allStations = stations;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_search, container, false);
        if (context == null)
            context = requireContext();
        memory = new Memory(context);

        listView = fragView.findViewById(R.id.station_list);
        input = fragView.findViewById(R.id.input_field);
        layout = fragView.findViewById(R.id.input_layout);
        progressBar = fragView.findViewById(R.id.progressBar);
        depArr = fragView.findViewById(R.id.dep_arr);
        backImage = fragView.findViewById(R.id.back_image);

        depArr.setText(this.name);

        stations = new ArrayList<>();
        stationAdapter = new StationAdapter(context, stations);
        listView.setAdapter(stationAdapter);

        input.requestFocus();
        inputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);

        List<Station> memoryList = memory.readListFromMemory();
        if (memoryList != null)
            stations.addAll(memoryList);


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input.getText().toString().length() > 2 && allStations != null){
                    stations.clear();
                    stations.addAll(allStations.stream()
                            .filter(object -> object.getName().toLowerCase().contains(input.getText().toString().toLowerCase()))
                            .collect(Collectors.toList()));
                    stationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                input.clearFocus();

                inputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(input.getWindowToken(), 0);

                return true;
            }
            return false;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try{
                if (stations == null)
                    return;
                Station station = stations.get(position);
                memory.writeToListMemory(station);
                memory.writeToMemory(name, station.getId());
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } catch (RuntimeException e){
                showToast(e.getMessage());
            }
        });

        backImage.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.popBackStack();
        });

        return fragView;
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}