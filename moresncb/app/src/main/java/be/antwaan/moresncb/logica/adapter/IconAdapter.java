package be.antwaan.moresncb.logica.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.antwaan.moresncb.R;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private List<Drawable> icons;
    private Context context;

    public IconAdapter(List<Drawable> icons) {
        this.icons = icons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable icon = icons.get(position);

        int color;
        Resources resources = context.getResources();
        if (resources.getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_YES) {
            color = ContextCompat.getColor(context, R.color.white);
        } else {
            color = ContextCompat.getColor(context, R.color.darkgrey);
        }

        icon.setTint(color);
        holder.iconImage.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_image);
        }
    }
}