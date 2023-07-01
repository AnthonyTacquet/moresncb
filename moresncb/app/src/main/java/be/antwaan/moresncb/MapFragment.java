package be.antwaan.moresncb;

import android.content.Context;
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

import org.json.JSONException;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Map;

import be.antwaan.moresncb.data.NMBSData;
import be.antwaan.moresncb.global.NMBS.Vehicle;

public class MapFragment extends Fragment {
    private View fragView;
    private MapView mapView;
    private ProgressBar progressBar;
    private Context context;
    private Vehicle vehicle;
    private Handler handler;
    private Runnable runnable;
    private boolean start = true;

    public MapFragment(){}

    public MapFragment(Context context, Vehicle vehicle){
        this.context = context;
        this.vehicle = vehicle;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragView = inflater.inflate(R.layout.fragment_map, container, false);

        progressBar = fragView.findViewById(R.id.progressBar);
        if (context == null)
            context = requireContext();
        Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());

        mapView = fragView.findViewById(R.id.mapView);

        setDefaultOsmSettings(); // Set default map settings
        reloadVehicle(new NMBSData()); // draw icon

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

    private void reloadVehicle(NMBSData nmbsData){
        if (vehicle ==  null)
            return;

        Thread thread = new Thread(() -> {
            boolean run = true;
            while (run){
                try {
                    vehicle = nmbsData.GetVehicle(vehicle.getName(), LocalDateTime.now());
                    if (getActivity() == null)
                        return;
                    getActivity().runOnUiThread(() -> drawVehicle());
                    Thread.sleep(500);
                } catch (JSONException e) {
                    showToast(e.getMessage());
                    run = false;
                } catch (Exception e) {
                    showToast(e.getMessage());
                    run = false;
                }
            }

        });
        thread.start();
    }

    private void drawVehicle(){
        try {
            double latitude = vehicle.getLocationY();
            double longitude = vehicle.getLocationX();
            if (latitude == 0 && longitude == 0){
                showToast("Couldn't find " + vehicle.getName());
                return;
            }
            String name = vehicle.getName();
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.baseline_train_24);

            Marker marker = new Marker(mapView);
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(name);

            if (drawable instanceof VectorDrawable) {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                Drawable resizedDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int) (48.0f * getResources().getDisplayMetrics().density), (int) (48.0f * getResources().getDisplayMetrics().density), true));
                marker.setIcon(resizedDrawable);
            } else {
                marker.setIcon(drawable);
            }

            mapView.getOverlays().add(marker);
            mapView.invalidate();

            setZoom(latitude, longitude);
        } catch (Exception exception){

        }


    }

    private void setZoom(double latitude, double longitude){
        if (!start)
            return;
        mapView.getController().setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(latitude, longitude); // Set the latitude and longitude of the map center
        mapView.getController().setCenter(startPoint);
        start = false;
    }

    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}