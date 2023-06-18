package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.draw.HorizontalRouteDraw;

public class RouteAdapter extends ArrayAdapter<Connection> {
    private LayoutInflater inflater;

    public RouteAdapter(Context context, List<Connection> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RouteAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.route_item, parent, false);

            viewHolder = new RouteAdapter.ViewHolder();
            viewHolder.departureTime = convertView.findViewById(R.id.departure_time);
            viewHolder.arrivalTime = convertView.findViewById(R.id.arrival_time);
            viewHolder.platformField = convertView.findViewById(R.id.platform_field);
            viewHolder.durationField = convertView.findViewById(R.id.duration_field);
            viewHolder.messageImage = convertView.findViewById(R.id.message_image);
            viewHolder.messageField = convertView.findViewById(R.id.message_text);
            viewHolder.horizontalRouteDraw = convertView.findViewById(R.id.route_draw);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RouteAdapter.ViewHolder) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Connection item = getItem(position);
        if (item == null)
            return convertView;

        if (item.getDeparture().getDelay() == 0)
            viewHolder.departureTime.setText(sdf.format(item.getDeparture().getDateTime()));
        else
            viewHolder.departureTime.setText(sdf.format(item.getDeparture().getDateTime()) + " +" + item.getDeparture().getDelay());

        if (item.getArrival().getDelay() == 0)
            viewHolder.arrivalTime.setText(sdf.format(item.getArrival().getDateTime()));
        else
            viewHolder.arrivalTime.setText(sdf.format(item.getArrival().getDateTime()) + " +" + item.getArrival().getDelay());

        viewHolder.platformField.setText("Platform " + item.getDeparture().getPlatformNumber());
        viewHolder.durationField.setText("" + item.getDuration().toMinutes() + " min");

        if (item.getAlerts().size() > 1){
            Drawable drawable = getContext().getDrawable(R.drawable.triangle_exclamation_solid);
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            viewHolder.messageField.setTextColor(Color.RED);

            viewHolder.messageImage.setImageDrawable(drawable);
            viewHolder.messageField.setText("Multiple messages for this journey");
        } else if (item.getAlerts().size() == 1){
            Alert alert = item.getAlerts().get(0);

            Drawable drawable = getContext().getDrawable(R.drawable.triangle_exclamation_solid);
            drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            viewHolder.messageField.setTextColor(Color.RED);

            viewHolder.messageImage.setImageDrawable(drawable);
            viewHolder.messageField.setText(alert.getHeader());
        } else {
            viewHolder.messageField.setVisibility(View.GONE);
            viewHolder.messageImage.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        HorizontalRouteDraw horizontalRouteDraw;
        TextView departureTime;
        TextView arrivalTime;
        TextView platformField;
        TextView durationField;
        ImageView messageImage;
        TextView messageField;
    }
}
