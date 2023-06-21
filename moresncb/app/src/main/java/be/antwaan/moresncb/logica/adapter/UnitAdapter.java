package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Unit;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {
    private List<Unit> units;
    private Context context;
    private List<Drawable> drawables = new ArrayList<>();

    public UnitAdapter(Context context, List<Unit> units) {
        this.units = units;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Unit unit = units.get(position);
        // Set the image resource or other data for the item view
        holder.itemImage.setImageResource(unit.getImageId());

        displayUnit(unit);

        holder.trainName.setText("Number: " + (unit.getId() + 1));

        holder.iconContainer.removeAllViews();

        for (Drawable icon : drawables) {
            ImageView imageView = new ImageView(context); // Use the appropriate context if you're not inside an Activity
            imageView.setImageDrawable(icon);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            imageView.setColorFilter(Color.WHITE);

            int margin = 5;
            layoutParams.setMarginEnd(margin);

            holder.iconContainer.addView(imageView, layoutParams);
        }

        if (drawables.size() == 0)
            holder.iconContainer.setVisibility(View.GONE);
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

        drawables.clear();
        drawables.addAll(newIcons);
    }


    @Override
    public int getItemCount() {
        return units.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView trainName;
        LinearLayout iconContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.train_image);
            iconContainer = itemView.findViewById(R.id.icon_container);
            trainName = itemView.findViewById(R.id.train_name);
        }
    }
}