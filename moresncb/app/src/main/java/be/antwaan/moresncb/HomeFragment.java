package be.antwaan.moresncb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class HomeFragment extends Fragment {

    private TextInputEditText fromInput, toInput;
    private TextInputLayout fromLayout, toLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_home, container, false);
        fromLayout = fragView.findViewById(R.id.from_input_layout);
        fromInput = fragView.findViewById(R.id.from_input);
        toLayout = fragView.findViewById(R.id.to_input_layout);
        toInput = fragView.findViewById(R.id.to_input);

        fromLayout.setOnClickListener(v -> {
            navigateToSearchFragment("Departure");
        });

        fromInput.setOnClickListener(v -> navigateToSearchFragment("Departure"));

        toLayout.setOnClickListener(v -> {
            navigateToSearchFragment("Destination");
        });

        toInput.setOnClickListener(v -> navigateToSearchFragment("Destination"));
        return fragView;
    }

    private void navigateToSearchFragment(String name) {
        SearchFragment newFragment = new SearchFragment(name);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}