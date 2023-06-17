package be.antwaan.moresncb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Composition;
import be.antwaan.moresncb.global.NMBS.Unit;
import be.antwaan.moresncb.logica.CompositionManager;
import be.antwaan.moresncb.logica.adapter.IconAdapter;
import be.antwaan.moresncb.logica.adapter.UnitAdapter;

public class InfoActivity extends AppCompatActivity {
    private String trainId = "IC530";
    private Composition composition;
    private CompositionManager compositionManager;
    private ProgressBar progressBar;
    private RecyclerView listView;
    private RecyclerView recyclerView;
    private IconAdapter iconAdapter;
    private List<Drawable> icons;

    public InfoActivity(){
    }

    public InfoActivity(String id){
        this.trainId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.icon_list);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        icons = new ArrayList<>();

        iconAdapter = new IconAdapter(icons);
        listView.setAdapter(iconAdapter);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
    }
    private void displayUnit(Unit unit){
        List<Drawable> newIcons = new ArrayList<>();
        if (unit.hasPrmSection())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.baseline_accessible_24));
        if (unit.hasBikeSection())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.baseline_directions_bike_24));
        if (unit.hasAirco())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.fan_solid));
        if (unit.hasTables())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.baseline_table_bar_24));
        if (unit.hasHeating())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.fire_solid));
        if (unit.hasLuggageSection())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.suitcase_solid));
        if (unit.hasToilets())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.restroom_solid));
        if (unit.hasSecondClassOutlets() || unit.hasFirstClassOutlets())
            newIcons.add(ContextCompat.getDrawable(this, R.drawable.plug_solid));

        icons.clear();
        icons.addAll(newIcons);
        iconAdapter.notifyDataSetChanged();
    }


    private void draw(){
        compositionManager = new CompositionManager(composition);
        List<Unit> units = compositionManager.getUnits();
        UnitAdapter adapter = new UnitAdapter(InfoActivity.this, units);
        recyclerView.setAdapter(adapter);

    }

    private void loadData(NMBSData nmbsData){
        if(trainId == null || trainId.length() < 0)
            return;
        Thread thread = new Thread(() -> {
            try{
                composition = nmbsData.GetComposition(trainId);
            } catch (JSONException e) {
                showToast(e.getMessage());
                return;
            }

            runOnUiThread(() -> draw());
            runOnUiThread(() -> progressBar.setVisibility(View.GONE));
        });
        thread.start();
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(InfoActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}