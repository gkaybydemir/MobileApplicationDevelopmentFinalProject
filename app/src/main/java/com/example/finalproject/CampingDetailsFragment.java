package com.example.finalproject;

// CampingDetailsFragment.java
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.squareup.picasso.Picasso;

public class CampingDetailsFragment extends Fragment {

    private CampingPlace campingPlace;

    public static CampingDetailsFragment newInstance(CampingPlace campingPlace) {
        CampingDetailsFragment fragment = new CampingDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("campingPlace", campingPlace);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camping_details, container, false);
        ImageView campingPlaceImageView = view.findViewById(R.id.campingPlaceImageView);
        TextView campingPlaceNameTextView = view.findViewById(R.id.campingPlaceNameTextView);
        TextView campingPlacePriceTextView = view.findViewById(R.id.campingPlacePriceTextView);
        TextView campingPlaceLocationTextView = view.findViewById(R.id.campingPlaceLocationTextView);
        TextView campingPlaceDescription = view.findViewById(R.id.campingPlaceDescriptionTextView);
        CheckBox campingPlaceToiletCheckBox = view.findViewById(R.id.campingPlaceToiletCheckBox);
        CheckBox campingPlaceWifiCheckBox = view.findViewById(R.id.campingPlaceWifiCheckBox);
        CheckBox campingPlaceShowerCheckBox = view.findViewById(R.id.campingPlaceShowerCheckBox);
        CheckBox campingPlaceWaterCheckBox = view.findViewById(R.id.campingPlaceWaterCheckBox);
        CheckBox campingPlaceMarketCheckBox = view.findViewById(R.id.campingPlaceMarketCheckBox);
        CheckBox campingPlaceParkingAreaCheckBox = view.findViewById(R.id.campingPlaceParkingAreaCheckBox);
        CheckBox campingPlaceBeachCheckBox = view.findViewById(R.id.campingPlaceBeachCheckBox);
        CheckBox campingPlaceElectricityCheckBox = view.findViewById(R.id.campingPlaceElectricityCheckBox);
        ImageView campingPlaceMapLinkImageView = view.findViewById(R.id.campingPlaceMapLinkImageView);


        if (getArguments() != null) {
            campingPlace = getArguments().getParcelable("campingPlace");
            if (campingPlace != null) {
                Picasso.with(campingPlaceImageView.getContext()).load(campingPlace.getPhotoLink()).into(campingPlaceImageView);
                campingPlaceNameTextView.setText(campingPlace.getName());
                campingPlacePriceTextView.setText("Price: " + campingPlace.getPrice());
                campingPlaceLocationTextView.setText("Location: " + campingPlace.getLocation());
                campingPlaceDescription.setText(campingPlace.getDescription());
                CheckBox[] checkBoxes = {
                        campingPlaceToiletCheckBox,
                        campingPlaceWifiCheckBox,
                        campingPlaceShowerCheckBox,
                        campingPlaceWaterCheckBox,
                        campingPlaceMarketCheckBox,
                        campingPlaceParkingAreaCheckBox,
                        campingPlaceBeachCheckBox,
                        campingPlaceElectricityCheckBox
                };
                boolean[] databaseValues = {
                        campingPlace.isToilet(),
                        campingPlace.isWifi(),
                        campingPlace.isShower(),
                        campingPlace.isWater(),
                        campingPlace.isMarket(),
                        campingPlace.isParkingArea(),
                        campingPlace.isBeach(),
                        campingPlace.isElectricity()
                };
                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i].setChecked(databaseValues[i]);
                    checkBoxes[i].setClickable(false);
                }

                campingPlaceMapLinkImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = campingPlace.getMapLink();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });

            }
        }
        Button reservationButton = view.findViewById(R.id.reservation_button);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReservationFragment();
            }
        });;

        return view;
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


    private void openReservationFragment() {
        // Create an instance of the ReservationFragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ReservationFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}