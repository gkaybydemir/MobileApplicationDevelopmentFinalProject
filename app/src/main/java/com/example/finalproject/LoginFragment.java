package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment {

    private EditText editTextMail, editTextPassword;
    private FirebaseFirestore db;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());
        editTextMail = view.findViewById(R.id.editTextMail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        db = FirebaseFirestore.getInstance();

        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterPage();
            }
        });
    }

    public void login() {
        final String email = editTextMail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            db.collection("users")
                    .whereEqualTo("mail", email)
                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                    String name = document.getString("name");
                                    String surname = document.getString("surname");
                                    String birthdate = document.getString("birthdate");
                                    String telephone = document.getString("telephone");
                                    String userDocument = document.getId(); // Firestore'daki belge kimliği

                                    sharedPreferencesHelper.saveLoginData(email, password, name, surname, birthdate, telephone, userDocument);
                                    openCityList();
                                } else {
                                    Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("FirestoreError", "Error getting documents: ", task.getException());
                                Toast.makeText(getActivity(), "Error retrieving user data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void openRegisterPage() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RegisterFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openCityList() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CityListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
