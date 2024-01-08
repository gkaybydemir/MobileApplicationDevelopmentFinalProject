// CampingListFragment.java
package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CampingListFragment extends Fragment {

    private List<CampingPlace> campingPlaces;
    private RecyclerView campingRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        View view = inflater.inflate(R.layout.fragment_camping_list, container, false);
        campingRecyclerView = view.findViewById(R.id.campingRecyclerView);
        setupCampingRecyclerView();
        return view;
    }

    private void setupCampingRecyclerView() {
        // Initialize campingPlaces and set up RecyclerView adapter
        campingPlaces = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference citiesRef = db.collection("cities");

        citiesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                // Get the 'cities' document data
                Map<String, Object> cityData = document.getData();

                // Iterate over 'place' fields (place1, place2, etc.)
                for (int i = 1; i <= 10; i++) { // Assuming there's a maximum number of places
                    String placeKey = "place" + i;
                    Map<String, Object> placeData = (Map<String, Object>) cityData.get(placeKey);

                    if (placeData != null) {
                        // Extract name, price, and location from placeData

                        String name = (String) placeData.get("name");
                        String price = (String) placeData.get("price");
                        String location = (String) placeData.get("location");

                        // Create CampingPlace object
                        CampingPlace campingPlace = new CampingPlace(name, price, location);

                        campingPlace.setBeach((boolean) placeData.get("beach"));
                        campingPlace.setDescription((String) placeData.get("description"));
                        campingPlace.setElectricity((boolean) placeData.get("electricity"));
                        campingPlace.setLocation((String) placeData.get("location"));
                        campingPlace.setMapLink((String) placeData.get("map_link"));
                        campingPlace.setMarket((boolean) placeData.get("market"));
                        campingPlace.setParkingArea((boolean) placeData.get("parking_area"));
                        campingPlace.setPhotoLink((String) placeData.get("photo_link"));
                        campingPlace.setShower((boolean) placeData.get("shower"));
                        campingPlace.setToilet((boolean) placeData.get("toilet"));
                        campingPlace.setWater((boolean) placeData.get("water"));
                        campingPlace.setWifi((boolean) placeData.get("wifi"));

                        //tent ve capacity eklenmedi

                        campingPlaces.add(campingPlace);
                    }
                }
            }
        CampingPlaceAdapter campingPlaceAdapter = new CampingPlaceAdapter(campingPlaces, getParentFragmentManager()); // Pass FragmentManager
        campingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        campingRecyclerView.setAdapter(campingPlaceAdapter);
    });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_profile) {
            openProfileFragment();
            return true;
        } else if (itemId == R.id.action_settings) {
            openSettingsFragment();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openProfileFragment() {
        // Replace the current fragment with the ProfileFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openSettingsFragment() {
        // Replace the current fragment with the SettingsFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SettingsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}