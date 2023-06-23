package be.antwaan.moresncb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import be.antwaan.moresncb.global.NMBS.Connection;
import be.antwaan.moresncb.logica.Memory;
import be.antwaan.moresncb.logica.adapter.FavoriteAdapter;
import be.antwaan.moresncb.logica.adapter.RouteAdapter;

public class FavoriteFragment extends Fragment {
    private ProgressBar progressBar;
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<Connection> connections;
    private Memory memory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_favorite, container, false);

        listView = fragView.findViewById(R.id.route_list);

        memory = new Memory(requireContext());

        connections = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(getContext(), connections);
        listView.setAdapter(favoriteAdapter);
        listView.setDividerHeight(20);

        loadData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (connections == null)
                return;

            try {
                Connection connection = connections.get(position);
                if (connection == null)
                    return;
                navigateToJourneyFragment(connection);
            } catch (RuntimeException e){
                showToast(e.getMessage());
            }

        });


        return fragView;
    }

    private void navigateToJourneyFragment(Connection connection) {
        JourneyFragment newFragment = new JourneyFragment(requireContext(), connection);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadData(){
        connections.addAll(memory.readFromConnectionMemory());
        favoriteAdapter.notifyDataSetChanged();
    }

    private void showToast(String message) {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
    }
}