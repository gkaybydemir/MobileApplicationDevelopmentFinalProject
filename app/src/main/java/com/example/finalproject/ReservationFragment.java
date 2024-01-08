package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ReservationFragment extends Fragment {

    private Spinner spinnerPersonCount, spinnerDays, spinnerTent, spinnerPayment;
    private Button acceptButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        // Populate spinners with dummy data (you should replace this with your actual data)
        ArrayAdapter<String> personCountAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"1", "2", "3", "4", "5", "6", "7"});
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"1", "2", "3", "4", "5", "6", "7"});
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

        acceptButton = view.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCitiesPage();
                showMessage("Reservation successful");
            }
        });;
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
}
