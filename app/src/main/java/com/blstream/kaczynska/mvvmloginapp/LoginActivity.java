package com.blstream.kaczynska.mvvmloginapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blstream.kaczynska.mvvmloginapp.databinding.ActivityLoginBinding;

import rx.functions.Action1;

/**
 * A login screen that offers login via email with user's password.
 */
public class LoginActivity extends AppCompatActivity {

	private Action1<View> focusOnNextViewAction;
	private Action1<View> showLoginAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
		setCallbackActions();
		final ViewModel viewModel = new ViewModel(getBaseContext(), focusOnNextViewAction, showLoginAction);
		binding.setViewModel(viewModel);
	}

	private void setCallbackActions() {
		focusOnNextViewAction = new Action1<View>() {
			@Override
			public void call(@NonNull View view) {
				setNextFocus(view);
			}
		};
		showLoginAction = new Action1<View>() {
			@Override
			public void call(@NonNull View view) {
				showLogin(view);
			}
		};
	}

	private void setNextFocus(@NonNull View currentView) {
		View nextField = currentView.focusSearch(View.FOCUS_DOWN);
		nextField.requestFocus();
	}

	private void showLogin(View view) {
		Snackbar.make(view, getText(R.string.login), Snackbar.LENGTH_SHORT).show();
	}
}


