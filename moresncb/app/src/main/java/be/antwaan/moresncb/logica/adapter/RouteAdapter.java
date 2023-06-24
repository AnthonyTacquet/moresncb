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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Alert;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.Memory;
import be.antwaan.moresncb.logica.draw.HorizontalRouteDraw;

public class RouteAdapter extends ArrayAdapter<Connection> {
    private LayoutInflater inflater;
    private Context context;
    private Memory memory;
    private ArrayList<Connection> favorites;

    public RouteAdapter(Context context, List<Connection> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
        this.context = context;
        memory = new Memory(context);
        favorites = memory.readFromConnectionMemory();
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
            viewHolder.delayLayout = convertView.findViewById(R.id.delay_layout);
            viewHolder.departureDelay = convertView.findViewById(R.id.departure_delay);
            viewHolder.arrivalDelay = convertView.findViewById(R.id.arrival_delay);
            viewHolder.favoriteButton = convertView.findViewById(R.id.favorite_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RouteAdapter.ViewHolder) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Connection item = getItem(position);
        if (item == null)
            return convertView;

        if (item.getDeparture().getDelay() == 0)
            viewHolder.departureDelay.setText("");
        else
            viewHolder.departureDelay.setText("+" + item.getDeparture().getDelay());

        if (item.getArrival().getDelay() == 0)
            viewHolder.arrivalDelay.setText("");
        else
            viewHolder.arrivalDelay.setText("+" + item.getArrival().getDelay());

        if (item.getArrival().getDelay() == 0 && item.getDeparture().getDelay() == 0)
            viewHolder.delayLayout.setVisibility(View.GONE);


        viewHolder.departureTime.setText(sdf.format(item.getDeparture().getDateTime()));
        viewHolder.arrivalTime.setText(sdf.format(item.getArrival().getDateTime()));
        viewHolder.platformField.setText("Platform " + item.getDeparture().getPlatformNumber());
        viewHolder.durationField.setText("" + item.getDuration().toMinutes() + " min");

        int colorRes = R.color.orange;
        int colorOrange = ContextCompat.getColor(context, colorRes);

        if (item.getAlerts().size() > 1){
            Drawable drawable = getContext().getDrawable(R.drawable.triangle_exclamation_solid);
            drawable.setColorFilter(colorOrange, PorterDuff.Mode.SRC_IN);
            viewHolder.messageField.setTextColor(colorOrange);

            viewHolder.messageImage.setImageDrawable(drawable);
            viewHolder.messageField.setText("Multiple messages for this journey");
        } else if (item.getAlerts().size() == 1){
            Alert alert = item.getAlerts().get(0);

            Drawable drawable = getContext().getDrawable(R.drawable.triangle_exclamation_solid);
            drawable.setColorFilter(colorOrange, PorterDuff.Mode.SRC_IN);
            viewHolder.messageField.setTextColor(colorOrange);

            viewHolder.messageImage.setImageDrawable(drawable);
            viewHolder.messageField.setText(alert.getHeader());
        } else {
            viewHolder.messageField.setVisibility(View.GONE);
            viewHolder.messageImage.setVisibility(View.GONE);
        }

        viewHolder.horizontalRouteDraw.setConnection(item);
        checkStar(item, viewHolder);

        viewHolder.favoriteButton.setOnClickListener(v -> {
            if (favorites.contains(item)){
                favorites.remove(item);
                memory.removeConnectionFromMemory(item);

                int colorRes2 = R.color.white;
                int color2 = ContextCompat.getColor(context, colorRes2);
                viewHolder.favoriteButton.setColorFilter(color2);

                int drawableRes = R.drawable.star_regular;
                viewHolder.favoriteButton.setImageResource(drawableRes);
            } else {
                int colorRes2 = R.color.gold;
                int color2 = ContextCompat.getColor(context, colorRes2);
                viewHolder.favoriteButton.setColorFilter(color2);

                int drawableRes = R.drawable.star_solid;
                viewHolder.favoriteButton.setImageResource(drawableRes);

                memory.writeToConnectionMemory(item);
            }
        });


        return convertView;
    }

    private void checkStar(Connection connection, ViewHolder viewHolder){
        if (favorites.contains(connection)) {
            int colorRes = R.color.gold;
            int color = ContextCompat.getColor(context, colorRes);
            viewHolder.favoriteButton.setColorFilter(color);

            int drawableRes = R.drawable.star_solid;
            viewHolder.favoriteButton.setImageResource(drawableRes);
        } else {
            int colorRes = R.color.white;
            int color = ContextCompat.getColor(context, colorRes);
            viewHolder.favoriteButton.setColorFilter(color);

            int drawableRes = R.drawable.star_regular;
            viewHolder.favoriteButton.setImageResource(drawableRes);
        }
    }

    private static class ViewHolder {
        HorizontalRouteDraw horizontalRouteDraw;
        LinearLayout delayLayout;
        TextView departureDelay;
        TextView arrivalDelay;
        TextView departureTime;
        TextView arrivalTime;
        TextView platformField;
        TextView durationField;
        ImageView messageImage, favoriteButton;
        TextView messageField;
    }
}
