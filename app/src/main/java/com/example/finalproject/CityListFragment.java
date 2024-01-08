// CityListFragment.java
package com.example.finalproject;
import android.os.Bundle;
import android.util.Log;
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

public class CityListFragment extends Fragment {

    private List<City> cities;
    private RecyclerView cityRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        cityRecyclerView = view.findViewById(R.id.cityRecyclerView);
        setupCityRecyclerView();
        return view;
    }

    private void setupCityRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference citiesCollection = db.collection("cities");
        cities = new ArrayList<>();
        citiesCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String cityName = document.getString("city_name");
                    if (cityName != null) {
                        System.out.println(cityName);
                        cities.add(new City(cityName));
                    }
                }
                CityAdapter cityAdapter = new CityAdapter(cities, new CityAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(City city) {
                        loadCampingListFragment(city);
                    }
                });

                cityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                cityRecyclerView.setAdapter(cityAdapter);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }


    private void loadCampingListFragment(City city) {
        CampingListFragment campingListFragment = new CampingListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cityName", city.getName());
        campingListFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, campingListFragment)
                .addToBackStack(null)
                .commit();
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