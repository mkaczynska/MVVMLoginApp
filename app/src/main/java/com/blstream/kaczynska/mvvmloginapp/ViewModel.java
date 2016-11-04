package com.blstream.kaczynska.mvvmloginapp;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import rx.functions.Action1;

import static com.blstream.kaczynska.mvvmloginapp.R.string.error_field_required;
import static com.blstream.kaczynska.mvvmloginapp.R.string.error_invalid_email;
import static com.blstream.kaczynska.mvvmloginapp.R.string.error_invalid_password;


public class ViewModel {

	public ObservableString email = new ObservableString();
	public ObservableString password = new ObservableString();
	public ObservableField<String> emailError = new ObservableField<>();
	public ObservableField<String> passError = new ObservableField<>();
	public ObservableBoolean isLoginEnabled = new ObservableBoolean();
	public String emailTag = "email";
	public String passwordTag = "password";
	private Context context;
	private Action1<View> focusOnNextView;
	private Action1<View> showLoginAction;


	ViewModel(@NonNull Context context, @Nullable Action1<View> focusOnNextView, @Nullable Action1<View> showLoginAction) {
		this.context = context;
		this.focusOnNextView = focusOnNextView;
		this.showLoginAction = showLoginAction;
	}

	public boolean onEditorAction(View view) {
		isLoginEnabled.set(false);
		boolean isEmailValid = validateEmail(email, emailError);
		boolean isPasswordValid = validatePassword(password, passError);

		if (view.getTag().equals(passwordTag)) {
			handleValidPasswordInput(view, isEmailValid && isPasswordValid);
		}
		if (view.getTag().equals(emailTag)) {
			handleValidEmailInput(view, isEmailValid);
		}
		return true;
	}

	void handleValidPasswordInput(View view, boolean isAllowed) {
		if (isAllowed) {
			isLoginEnabled.set(true);
			focusOnNextView.call(view);
		}
	}

	void handleValidEmailInput(View view, boolean isAllowed) {
		if (isAllowed) {
			focusOnNextView.call(view);
		}
	}

	public void onLoginClick(View view) {
		if (isLoginEnabled.get()) {
			showLoginAction.call(view);
			isLoginEnabled.set(false);
		}
	}

	boolean isEmailValid(String email) {
		return email.contains("@");
	}

	boolean isPasswordValid(String password) {
		return password.length() >= 4;
	}

	String getEmailErrorMsg(boolean isNullOrEmpty) {
		return isNullOrEmpty ? context.getText(error_field_required).toString() : context.getText(error_invalid_email).toString();
	}

	String getPassErrorMsg(boolean isNullOrEmpty) {
		return isNullOrEmpty ? context.getText(error_field_required).toString() : context.getText(error_invalid_password).toString();
	}

	boolean validateEmail(ObservableString email, ObservableField<String> emailError) {
		emailError.set(null);
		if (email == null || email.isNullOrEmpty() || !isEmailValid(email.get())) {
			emailError.set(getEmailErrorMsg(email == null || email.isNullOrEmpty()));
			return false;
		}
		return true;
	}

	boolean validatePassword(ObservableString password, ObservableField<String> passError) {
		passError.set(null);
		isLoginEnabled.set(false);
		if (password == null || password.isNullOrEmpty() || !isPasswordValid(password.get())) {
			passError.set(getPassErrorMsg(password == null || password.isNullOrEmpty()));
			return false;
		}
		return true;
	}
}
