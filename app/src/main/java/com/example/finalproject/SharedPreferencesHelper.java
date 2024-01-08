package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPreferencesHelper {

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_TELEPHONE = "telephone";
    private static final String KEY_USER_DOCUMENT = "userDocument";  // New key for storing user document

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLoginData(String email, String password, String name, String surname, String birthdate, String telephone, String userDocument) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_BIRTHDATE, birthdate);
        editor.putString(KEY_TELEPHONE, telephone);
        editor.putString(KEY_USER_DOCUMENT, userDocument);  // Save the entire user document as a string
        editor.apply();
    }

    // Add other getter methods as needed

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getSurname() {
        return sharedPreferences.getString(KEY_SURNAME, "");
    }

    public String getBirthdate() {
        return sharedPreferences.getString(KEY_BIRTHDATE, "");
    }

    public String getTelephone() {
        return sharedPreferences.getString(KEY_TELEPHONE, "");
    }
    public String getUserDocument() {
        return sharedPreferences.getString(KEY_USER_DOCUMENT, "");
    }

    // Add a method to clear all saved data
    public void clearUserData() {
        editor.clear();
        editor.apply();
    }

}