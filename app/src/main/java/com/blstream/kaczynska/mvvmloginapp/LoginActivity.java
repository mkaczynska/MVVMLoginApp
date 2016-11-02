package com.blstream.kaczynska.mvvmloginapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blstream.kaczynska.mvvmloginapp.databinding.ActivityLoginBinding;

/**
 * A login screen that offers login via email with user's password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        final ViewModel viewModel = new ViewModel(getBaseContext());
        binding.setViewModel(viewModel);
    }
}


