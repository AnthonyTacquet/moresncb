package be.antwaan.moresncb.logica.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private Dialog dialog;
    private Memory memory;

    public ButtonAdapter(List<Station> stations) {
        this.stations = stations;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
        dialog = new Dialog(parent.getContext());
        memory = new Memory(parent.getContext());
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stations.get(position);

        holder.nameText.setText(station.getName());
        holder.iconImage.setOnClickListener(v -> showDialog(station));
    }

    public void removeStation(Station station) {
        stations.remove(station);
        notifyDataSetChanged();
    }

    private void showDialog(Station station){
        LinearLayout removeLayout;
        TextView stationName;

        dialog.setContentView(R.layout.button_box);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;

        dialog.getWindow().setAttributes(layoutParams);

        removeLayout = dialog.findViewById(R.id.remove_layout);
        stationName = dialog.findViewById(R.id.station_name);
        stationName.setText(station.getName());

        removeLayout.setOnClickListener(v -> {
            memory.removeShortcutFromMemory(station);
            removeStation(station);
            dialog.dismiss();
        });

        dialog.show();
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