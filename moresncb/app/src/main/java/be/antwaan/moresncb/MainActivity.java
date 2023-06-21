package be.antwaan.moresncb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

import java.time.LocalDateTime;

import be.antwaan.moresncb.databinding.ActivityMainBinding;
import be.antwaan.moresncb.global.Enum.DepartureOrArrival;
import be.antwaan.moresncb.global.NMBS.Station;
import be.antwaan.moresncb.logica.Memory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home)
                replaceFragment(new HomeFragment());
            else if(item.getItemId() == R.id.favorites)
                replaceFragment(new FavoriteFragment());
            else if(item.getItemId() == R.id.maps)
                replaceFragment(new MapFragment());
            else if(item.getItemId() == R.id.info)
                replaceFragment(new InfoFragment());
            return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}