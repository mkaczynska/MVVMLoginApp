package com.blstream.kaczynska.mvvmloginapp;


import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;


public class ViewModel {

    public ObservableString email = new ObservableString();
    public ObservableString password = new ObservableString();
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> passError = new ObservableField<>();
    public ObservableBoolean isLoginEnabled = new ObservableBoolean();
    public String emailTag = "email";
    public String passwordTag = "password";
    private Context context;


    ViewModel(@NonNull Context context) {
        this.context = context;
    }

    public boolean onEditorAction(View view) {
        boolean isEmailValid = validateEmail();
        boolean isPasswordValid = validatePassword();

        if (view.getTag().equals(passwordTag)) {
            handleValidPasswordInput(view, isEmailValid && isPasswordValid);
        }
        if (view.getTag().equals(emailTag)) {
            handleValidEmailInput(view, isEmailValid);
        }
        return true;
    }

    private void handleValidPasswordInput(View view, boolean isAllowed) {
        if (isAllowed) {
            isLoginEnabled.set(true);
            setNextFocus(view);
        }
    }

    private void handleValidEmailInput(View view, boolean isAllowed) {
        if (isAllowed) {
            setNextFocus(view);
        }
    }

    public void onClick(View view) {
        if (isLoginEnabled.get()) {
            showLogin(view);
            isLoginEnabled.set(false);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private String getEmailErrorMsg(boolean isNullOrEmpty) {
        return isNullOrEmpty ? context.getResources().getString(R.string.error_field_required) : context.getResources().getString(R.string.error_invalid_email);
    }

    private String getPassErrorMsg() {
        return context.getResources().getString(R.string.error_invalid_password);
    }

    private boolean validateEmail() {
        emailError.set(null);
        if (email.isNullOrEmpty() || !isEmailValid(email.get())) {
            emailError.set(getEmailErrorMsg(email.isNullOrEmpty()));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        passError.set(null);

        if (password.isNullOrEmpty() || !isPasswordValid(password.get())) {
            passError.set(getPassErrorMsg());
            return false;
        }
        return true;
    }

    private void setNextFocus(View currentView) {
        View nextField = currentView.focusSearch(View.FOCUS_DOWN);
        nextField.requestFocus();
    }

    private void showLogin(View view) {
        Snackbar.make(view, context.getText(R.string.login), Snackbar.LENGTH_SHORT).show();
    }
}
