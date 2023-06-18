package be.antwaan.moresncb;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Composition;
import be.antwaan.moresncb.global.NMBS.Unit;
import be.antwaan.moresncb.global.NMBS.Vehicle;
import be.antwaan.moresncb.logica.CompositionManager;
import be.antwaan.moresncb.logica.adapter.IconAdapter;
import be.antwaan.moresncb.logica.adapter.UnitAdapter;

public class InfoFragment extends Fragment {

    private Context context;
    private Vehicle vehicle;
    private Composition composition;
    private CompositionManager compositionManager;
    private ProgressBar progressBar;
    private RecyclerView listView;
    private RecyclerView recyclerView;
    private IconAdapter iconAdapter;
    private List<Drawable> icons;

    public InfoFragment(){

    }

    public InfoFragment(Context context, Vehicle vehicle){
        this.context = context;
        this.vehicle = vehicle;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_info, container, false);

        progressBar = fragView.findViewById(R.id.progressBar);
        listView = fragView.findViewById(R.id.icon_list);
        recyclerView = fragView.findViewById(R.id.recycler_view);

        if (context == null)
            context = requireContext();

        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        icons = new ArrayList<>();

        iconAdapter = new IconAdapter(icons);
        listView.setAdapter(iconAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int centerItemPosition = layoutManager.findFirstVisibleItemPosition() + layoutManager.getChildCount() / 2;
                    if (centerItemPosition >= 0 && centerItemPosition < layoutManager.getItemCount()) {
                        Unit unit = compositionManager.getUnits().get(centerItemPosition);
                        displayUnit(unit);
                    }
                }
            }
        });


        progressBar.setVisibility(View.VISIBLE);

        loadData(new NMBSData());

        return fragView;
    }


    private void displayUnit(Unit unit){
        List<Drawable> newIcons = new ArrayList<>();
        if (unit.hasPrmSection())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.baseline_accessible_24));
        if (unit.hasBikeSection())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.baseline_directions_bike_24));
        if (unit.hasAirco())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.fan_solid));
        if (unit.hasTables())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.baseline_table_bar_24));
        if (unit.hasHeating())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.fire_solid));
        if (unit.hasLuggageSection())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.suitcase_solid));
        if (unit.hasToilets())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.restroom_solid));
        if (unit.hasSecondClassOutlets() || unit.hasFirstClassOutlets())
            newIcons.add(ContextCompat.getDrawable(context, R.drawable.plug_solid));

        icons.clear();
        icons.addAll(newIcons);
        iconAdapter.notifyDataSetChanged();
    }


    private void draw(){
        compositionManager = new CompositionManager(composition);
        List<Unit> units = compositionManager.getUnits();
        UnitAdapter adapter = new UnitAdapter(context, units);
        recyclerView.setAdapter(adapter);

    }

    private void loadData(NMBSData nmbsData){
        String trainId = vehicle.getId();
        if(trainId == null || trainId.length() < 0)
            return;
        Thread thread = new Thread(() -> {
            try{
                composition = nmbsData.GetComposition(trainId);
            } catch (JSONException e) {
                showToast(e.getMessage());
                return;
            }

            getActivity().runOnUiThread(() -> draw());
            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
        });
        thread.start();
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}