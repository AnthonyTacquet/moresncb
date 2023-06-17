package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.antwaan.moresncb.R;
import be.antwaan.moresncb.global.NMBS.Unit;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {
    private List<Unit> units;
    private Context context;

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

        if (!unit.hasPrmSection())
            return;
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.baseline_accessible_24);
        ImageView iconView = new ImageView(context);
        iconView.setImageDrawable(drawable);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        holder.iconContainer.addView(iconView, layoutParams);
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        LinearLayout iconContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.train_image);
            iconContainer = itemView.findViewById(R.id.icon_container);
        }
    }
}