package com.blstream.kaczynska.mvvmloginapp;


import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

@BindingMethods({
        @BindingMethod(type = TextView.class,
                attribute = "android:onEditorAction",
                method = "setOnEditorActionListener")
})

public class EditTextAdapter {
    @BindingAdapter({"android:text"})
    public static void bindEditText(EditText view, final ObservableString observableString) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                observableString.set(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        String newValue = observableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }
}
