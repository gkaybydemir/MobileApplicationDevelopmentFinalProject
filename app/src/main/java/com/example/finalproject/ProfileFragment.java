package com.example.finalproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextSurname, editTextTelephone, editTextEmail, editTextBirthDate;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize SharedPreferencesHelper
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());

        editTextName = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextTelephone = view.findViewById(R.id.editTextTelephone);
        editTextBirthDate = view.findViewById(R.id.editTextBirthDate);

        // Set hints from SharedPreferencesHelper
        editTextName.setHint(sharedPreferencesHelper.getName());
        editTextSurname.setHint(sharedPreferencesHelper.getSurname());
        editTextTelephone.setHint(sharedPreferencesHelper.getTelephone());
        editTextBirthDate.setHint(sharedPreferencesHelper.getBirthdate());

        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        Button buttonLogOut = view.findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    public void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                        // Check the birth date
                        if (isUnder18(year, month, dayOfMonth)) {
                            // Show a Toast message if under 18
                            Toast.makeText(requireContext(), "Update is not allowed for individuals under 18", Toast.LENGTH_SHORT).show();
                        } else {
                            // If 18 or older, set the birth date in the editTextBirthDate field
                            editTextBirthDate.setText(selectedDate);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void updateProfile() {
        String newName = editTextName.getText().toString().trim();
        String newSurname = editTextSurname.getText().toString().trim();
        String newTelephone = editTextTelephone.getText().toString().trim();
        String newBirthDate = editTextBirthDate.getText().toString().trim();

        String userDocumentId = sharedPreferencesHelper.getUserDocument();

        if (!userDocumentId.isEmpty()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userDocumentId);

            Map<String, Object> updates = new HashMap<>();

            if (!newName.isEmpty()) {
                updates.put("name", newName);
            }
            if (!newSurname.isEmpty()) {
                updates.put("surname", newSurname);
            }
            if (!newTelephone.isEmpty()) {
                updates.put("telephone", newTelephone);
            }
            if (!newBirthDate.isEmpty()) {
                updates.put("birthdate", newBirthDate);
            }

            userRef.update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String updatedProfileData = "Updated Profile:";
                            if (!newName.isEmpty()) {
                                updatedProfileData += "\nName: " + newName;
                            }
                            if (!newSurname.isEmpty()) {
                                updatedProfileData += "\nSurname: " + newSurname;
                            }
                            if (!newTelephone.isEmpty()) {
                                updatedProfileData += "\nTelephone: " + newTelephone;
                            }
                            if (!newBirthDate.isEmpty()) {
                                updatedProfileData += "\nBirth Date: " + newBirthDate;
                            }
                            Toast.makeText(getActivity(), updatedProfileData, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "User document not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void logOut() {
        sharedPreferencesHelper.clearUserData();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_SHORT).show();
    }

    private boolean isUnder18(int year, int month, int day) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month, day);

        Calendar today = Calendar.getInstance();

        // Return true if under 18, false otherwise
        return today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) < 18 ||
                (today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) == 18 &&
                        today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR));
    }

}
