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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Arrival;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Departure;
import be.antwaan.moresncb.logica.draw.HorizontalRouteDraw;

public class JourneyAdapter  extends ArrayAdapter<Pair<Departure, Arrival>> {
    private LayoutInflater inflater;

    public JourneyAdapter(Context context, List<Pair<Departure, Arrival>> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
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

        return convertView;
    }

    private static class ViewHolder {
        private TextView departureTime, arrivalTime, departureLocation, destinationLocation, departurePlatform, destinationPlatform, trainLocation;
    }
}
