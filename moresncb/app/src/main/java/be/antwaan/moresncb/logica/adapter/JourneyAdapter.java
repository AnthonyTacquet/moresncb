package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Arrival;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Departure;
import be.antwaan.moresncb.global.NMBS.Stop;
import be.antwaan.moresncb.logica.draw.HorizontalRouteDraw;

public class JourneyAdapter  extends RecyclerView.Adapter<JourneyAdapter.ViewHolder> {
    private Context context;
    private List<Pair<Departure, Arrival>> itemList;

    public JourneyAdapter(Context context, List<Pair<Departure, Arrival>> items) {
        this.itemList = items;
        this.context = context;
    }

    // Create ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declare your item views
        private TextView departureTime, arrivalTime, departureLocation, destinationLocation, departurePlatform, destinationPlatform, trainLocation;
        private ExpandableListView expandableListView;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize your item views
            departurePlatform = itemView.findViewById(R.id.platform_field_departure);
            destinationPlatform = itemView.findViewById(R.id.platform_field_destination);
            departureTime = itemView.findViewById(R.id.departure_time);
            arrivalTime = itemView.findViewById(R.id.arrival_time);
            departureLocation = itemView.findViewById(R.id.departure_field);
            destinationLocation = itemView.findViewById(R.id.arrival_field);
            trainLocation = itemView.findViewById(R.id.train_loc);
            expandableListView = itemView.findViewById(R.id.expandable_list_view);
        }
    }

    // Create ViewHolder and inflate item layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journey_item, parent, false);
        return new ViewHolder(itemView);
    }

    // Bind data to the views
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Pair<Departure, Arrival> deparr = itemList.get(position);
        Departure departure = deparr.first;
        Arrival arrival = deparr.second;

        if (departure == null || arrival == null)
            return;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String depTime = sdf.format(departure.getDateTime());
        String arrTime = sdf.format(arrival.getDateTime());

        viewHolder.departurePlatform.setText("Platform " + departure.getPlatform().getName());
        viewHolder.destinationPlatform.setText("Platform " + arrival.getPlatform().getName());
        viewHolder.departureLocation.setText("" + departure.getStationName());
        viewHolder.destinationLocation.setText("" + arrival.getStationName());
        viewHolder.departureTime.setText(depTime);
        viewHolder.arrivalTime.setText(arrTime);
        viewHolder.trainLocation.setText(departure.getVehicle().getShortName() + " to " + arrival.getDirection());

        List<String> groupItems = new ArrayList<>();
        groupItems.add("Journey info");

        Map<String, List<String>> childItems = new HashMap<>();
        List<String> children = new ArrayList<>();

        for (Stop stop : departure.getStops()) {
            children.add(stop.getStation().getName());
        }

        if (departure.getStops().size() == 0)
            children.add("No stops");

        childItems.put("Journey info", children);

        StopsAdapter stopsAdapter = new StopsAdapter(context, groupItems, childItems);
        viewHolder.expandableListView.setAdapter(stopsAdapter);

        int greyRes = R.color.grey;
        int grey = ContextCompat.getColor(context, greyRes);

        if (arrival.isCanceled()) {
            viewHolder.arrivalTime.setPaintFlags(viewHolder.arrivalTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.destinationLocation.setPaintFlags(viewHolder.arrivalTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.destinationPlatform.setPaintFlags(viewHolder.arrivalTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (departure.isCanceled()) {
            viewHolder.departureTime.setPaintFlags(viewHolder.departureTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.departureLocation.setPaintFlags(viewHolder.arrivalTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.departurePlatform.setPaintFlags(viewHolder.arrivalTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (arrival.isArrived()) {
            viewHolder.arrivalTime.setTextColor(grey);
            viewHolder.destinationLocation.setTextColor(grey);
            viewHolder.destinationPlatform.setTextColor(grey);
        }
        if (departure.isLeft()) {
            viewHolder.departureTime.setTextColor(grey);
            viewHolder.departureLocation.setTextColor(grey);
            viewHolder.departurePlatform.setTextColor(grey);

        }
    }

    // Return the number of items in the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
