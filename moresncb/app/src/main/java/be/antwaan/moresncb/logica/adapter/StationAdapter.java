package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Station;

public class StationAdapter extends ArrayAdapter<Station> {
    private LayoutInflater inflater;

    public StationAdapter(Context context, List<Station> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StationAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.station_item, parent, false);

            viewHolder = new StationAdapter.ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image_view);
            viewHolder.stationName = convertView.findViewById(R.id.station_name);
            viewHolder.stationType = convertView.findViewById(R.id.station_type);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StationAdapter.ViewHolder) convertView.getTag();
        }

        Station item = getItem(position);
        viewHolder.stationName.setText(item.getName());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView stationName;
        TextView stationType;
    }
}
