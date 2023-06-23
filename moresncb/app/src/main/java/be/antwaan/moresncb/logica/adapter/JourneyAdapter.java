package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.graphics.Color;
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

public class JourneyAdapter  extends ArrayAdapter<Pair<Departure, Arrival>> {
    private LayoutInflater inflater;
    private Context context;

    public JourneyAdapter(Context context, List<Pair<Departure, Arrival>> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JourneyAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.journey_item, parent, false);

            viewHolder = new JourneyAdapter.ViewHolder();
            viewHolder.departurePlatform = convertView.findViewById(R.id.platform_field_departure);
            viewHolder.destinationPlatform = convertView.findViewById(R.id.platform_field_destination);
            viewHolder.departureTime = convertView.findViewById(R.id.departure_time);
            viewHolder.arrivalTime = convertView.findViewById(R.id.arrival_time);
            viewHolder.departureLocation = convertView.findViewById(R.id.departure_field);
            viewHolder.destinationLocation = convertView.findViewById(R.id.arrival_field);
            viewHolder.trainLocation = convertView.findViewById(R.id.train_loc);
            viewHolder.expandableListView = convertView.findViewById(R.id.expandable_list_view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (JourneyAdapter.ViewHolder) convertView.getTag();
        }

        Pair<Departure, Arrival> deparr = getItem(position);
        Departure departure = deparr.first;
        Arrival arrival = deparr.second;

        if (departure == null || arrival == null)
            return convertView;
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

        return convertView;
    }

    private static class ViewHolder {
        private TextView departureTime, arrivalTime, departureLocation, destinationLocation, departurePlatform, destinationPlatform, trainLocation;
        private ExpandableListView expandableListView;
    }
}
