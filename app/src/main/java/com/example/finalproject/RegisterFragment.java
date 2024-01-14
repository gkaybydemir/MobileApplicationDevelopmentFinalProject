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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private EditText editTextName, editTextSurname, editTextTelephone, editTextEmail, editTextPassword, editTextBirthDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextTelephone = view.findViewById(R.id.editTextTelephone);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextBirthDate = view.findViewById(R.id.editTextBirthDate);

        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button buttonRegister = view.findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

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
                            Toast.makeText(requireContext(), "Registration not allowed for individuals under 18", Toast.LENGTH_SHORT).show();
                        } else {
                            // If 18 or older, set the birth date in the editTextBirthDate field
                            editTextBirthDate.setText(selectedDate);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Method for checking if the birth date is under 18
    private boolean isUnder18(int year, int month, int day) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month, day);

        Calendar today = Calendar.getInstance();

        // Return true if under 18, false otherwise
        return today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) < 18 ||
                (today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR) == 18 &&
                        today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR));
    }

    public void register() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String telephone = editTextTelephone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String birthDate = editTextBirthDate.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || telephone.isEmpty() ||
                email.isEmpty() || password.isEmpty() || birthDate.isEmpty()) {
            // Toast mesajı gönder
            Toast.makeText(getActivity(), "Please fill the all blanks", Toast.LENGTH_SHORT).show();

        }
        else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("surname", surname);
            user.put("telephone", telephone);
            user.put("mail", email);
            user.put("password", password);
            user.put("birthdate", birthDate);

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "User added to Firestore", Toast.LENGTH_SHORT).show();
                            openLoginPage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error adding user to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void openLoginPage() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
