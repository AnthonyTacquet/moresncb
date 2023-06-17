package be.antwaan.moresncb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import be.antwaan.moresncb.data.NMBSData;

public class MapFragment extends Fragment {
    private View fragView;
    private MapView mapView;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_map, container, false);

        progressBar = fragView.findViewById(R.id.progressBar);


        Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());

        mapView = fragView.findViewById(R.id.mapView);

        setDefaultOsmSettings(); // Set default map settings
        drawIcon(51.0874, 3.4483, "Train 1", ContextCompat.getDrawable(getContext(), R.drawable.baseline_train_24)); // draw test icon


        return fragView;
    }


    private void setDefaultOsmSettings(){
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(10.0);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        GeoPoint startPoint = new GeoPoint(50.5039, 4.4699); // Set the latitude and longitude of the map center
        mapView.getController().setCenter(startPoint);
    }

    private void drawIcon(double latitude, double longitude, String name, Drawable drawable){

        Marker marker = new Marker(mapView);
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(name);

        if (drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);            Drawable resizedDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (48.0f * getResources().getDisplayMetrics().density), (int) (48.0f * getResources().getDisplayMetrics().density), true));
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            drawable = new BitmapDrawable(getResources(), bitmap);
        }

        marker.setIcon(drawable);

        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }
}