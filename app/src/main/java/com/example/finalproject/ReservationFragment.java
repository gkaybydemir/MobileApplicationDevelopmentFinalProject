package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservationFragment extends Fragment {

    private Spinner spinnerPersonCount, spinnerDays, spinnerTent, spinnerPayment;

    private TextView price_counter;

    private Button acceptButton;
    private Integer selectedPersonCount;
    private Integer selectedDays;
    private String selectedTent;
    private String selectedPayment;

    private CampingPlace campingPlace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        campingPlace = ((CampingPlace) args.get("campingPlace"));
        selectedTent = campingPlace.getTent().get("two_three");
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerPersonCount = view.findViewById(R.id.spinnerpersonCount);
        spinnerDays = view.findViewById(R.id.spinnerDays);
        spinnerTent = view.findViewById(R.id.spinnerTent);
        spinnerPayment = view.findViewById(R.id.spinnerPayment);
        price_counter = view.findViewById(R.id.price_counter);


        // Populate spinners with dummy data (you should replace this with your actual data)
        ArrayAdapter<Integer> personCountAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new Integer[]{1, 2, 3, 4, 5, 6, 7});
        ArrayAdapter<Integer> daysAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new Integer[]{1, 2, 3, 4, 5, 6, 7});
        ArrayAdapter<String> tentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"2-3 Person", "4-5 Person", "No Tent"});
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Credit Card", "PayPal", "Cash"});
        personCountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPersonCount.setAdapter(personCountAdapter);
        spinnerDays.setAdapter(daysAdapter);
        spinnerTent.setAdapter(tentAdapter);
        spinnerPayment.setAdapter(paymentAdapter);

        spinnerPersonCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedPersonCount = (Integer) parentView.getItemAtPosition(position);
                calculateTotal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedDays = (Integer) parentView.getItemAtPosition(position);
                calculateTotal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        spinnerTent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner

                selectedTent = (String) parentView.getItemAtPosition(position);
                if (selectedTent.equals("2-3 Person")){
                    selectedTent = campingPlace.getTent().get("two_three");
                }
                if (selectedTent.equals("4-5 Person")){
                    selectedTent = campingPlace.getTent().get("four_five");
                }
                if (selectedTent.equals("No Tent")){
                    selectedTent = campingPlace.getTent().get("no_tent");
                }
                calculateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedPayment = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });




        acceptButton = view.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPersonCount > (Integer.parseInt(campingPlace.getCapacity().get("capacity_max")) - Integer.parseInt(campingPlace.getCapacity().get("instant_number")))){
                    showMessage("Capacity is not enough");
                    return;
                }
                showMessage("Reservation successful");
                updateInstantNumber(campingPlace.getPlaceKey(), String.valueOf(Integer.parseInt(campingPlace.getCapacity().get("instant_number")) + selectedPersonCount));
                System.out.println(Integer.parseInt(campingPlace.getCapacity().get("instant_number")));
                openCitiesPage();
            }
        });
    }

    private void openCitiesPage() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CityListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void calculateTotal() {
        if (selectedDays == null || selectedPersonCount == null || selectedTent == null) return;
        Integer total = (Integer.parseInt(campingPlace.getPrice()) * selectedDays * selectedPersonCount + (Integer.parseInt(selectedTent) * selectedDays));
        price_counter.setText("Total Price " + total + "₺");
    }


    private void updateInstantNumber(String placeKey, String newInstantNumber) {
        // Initialize campingPlaces and set up RecyclerView adapter
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference citiesRef = db.collection("cities");

        citiesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                // Get the 'cities' document data
                Map<String, Object> cityData = document.getData();
                Map<String, Object> placeData = (Map<String, Object>) cityData.get(placeKey);

                // Check if capacity exists in placeData
                if (placeData.containsKey("capacity")) {
                    // Get the current capacity map
                    Map<String, String> capacity = (Map<String, String>) placeData.get("capacity");

                    // Update the instant_number value
                    capacity.put("instant_number", newInstantNumber);

                    // Update the document in Firestore
                    db.collection("cities").document(document.getId()).set(cityData);
                }
            }
        });
    }



}