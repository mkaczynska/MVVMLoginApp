package com.blstream.kaczynska.mvvmloginapp;

import android.databinding.BaseObservable;


public class ObservableString extends BaseObservable {

	private String value;

	String get() {
		return value;
	}

	void set(String value) {
		this.value = value;
		notifyChange();
	}

	boolean isNullOrEmpty() {
		return value == null || value.isEmpty();
	}
}
