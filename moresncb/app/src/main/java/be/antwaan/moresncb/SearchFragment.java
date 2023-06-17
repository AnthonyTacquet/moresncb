package be.antwaan.moresncb;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Station;
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
    private String name = "";
    public SearchFragment(String name){
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_search, container, false);

        listView = fragView.findViewById(R.id.station_list);
        input = fragView.findViewById(R.id.input_field);
        layout = fragView.findViewById(R.id.input_layout);
        progressBar = fragView.findViewById(R.id.progressBar);
        depArr = fragView.findViewById(R.id.dep_arr);
        depArr.setText(this.name);

        stations = new ArrayList<>();
        stationAdapter = new StationAdapter(getContext(), stations);
        listView.setAdapter(stationAdapter);

        loadData(new NMBSData());
        progressBar.setVisibility(View.VISIBLE);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input.getText().toString().length() > 2){
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



        return fragView;
    }

    private void loadData(NMBSData nmbsData){
        Thread thread = new Thread(() -> {
            try {
                allStations = nmbsData.GetStations();
                getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            } catch (JSONException e) {
                showToast(e.getMessage());
            }
        });
        thread.start();
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }
}