package be.antwaan.moresncb.logica.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.Memory;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder>{
    private List<Station> stations;

    public ButtonAdapter(List<Station> stations) {
        this.stations = stations;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stations.get(position);

        holder.nameText.setText(station.getName());
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_image);
            nameText = itemView.findViewById(R.id.location_name);
        }
    }
}