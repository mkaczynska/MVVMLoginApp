package com.blstream.kaczynska.mvvmloginapp;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.functions.Action1;

import static com.blstream.kaczynska.mvvmloginapp.R.string.error_field_required;
import static com.blstream.kaczynska.mvvmloginapp.R.string.error_invalid_email;
import static com.blstream.kaczynska.mvvmloginapp.R.string.error_invalid_password;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewModelTest {

	@Mock
	Context context;
	@Mock
	ViewModel viewModel;
	@Mock
	View view;
	@Mock
	ViewModel viewModelMock;
	@Mock
	Action1<View> focusOnNextView;
	@Mock
	Action1<View> showLoginAction;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		viewModel = new ViewModel(context, focusOnNextView, showLoginAction);

		when(context.getText(R.string.error_field_required)).thenReturn("This field is required");
		when(context.getText(R.string.error_invalid_email)).thenReturn("This email address is invalid");
		when(context.getText(R.string.error_invalid_password)).thenReturn("This password is too short");
	}

	@Test
	public void isEmailValidWhenInvalid() {
		// Given
		String email = "my_email.com";
		// When
		boolean isValid = viewModel.isEmailValid(email);
		// Then
		assertFalse(isValid);
	}

	@Test
	public void isEmailValidWhenValid() {
		// Given
		String email = "my_sdf@email.com";
		// When
		boolean isValid = viewModel.isEmailValid(email);
		// Then
		assertTrue(isValid);
	}

	@Test
	public void isPasswordValidWhenValid() {
		// Given
		String password = "4983";
		// When
		boolean isValid = viewModel.isPasswordValid(password);
		// Then
		assertTrue(isValid);
	}

	@Test
	public void isPasswordValidWhenInValid() {
		// Given
		String password = "02";
		// When
		boolean isValid = viewModel.isPasswordValid(password);
		// Then
		assertFalse(isValid);
	}

	@Test
	public void getEmailErrorMsgWhenIsNullOrEmpty() {
		// Given
		String expectedEmailError = context.getText(error_field_required).toString();
		// When
		String actualEmailError = viewModel.getEmailErrorMsg(true);
		// Then
		assertEquals(expectedEmailError, actualEmailError);
	}

	@Test
	public void getEmailErrorMsgWhenIsInvalid() {
		// Given
		String expectedEmailError = context.getText(error_invalid_email).toString();
		// When
		String actualEmailError = viewModel.getEmailErrorMsg(false);
		// Then
		assertEquals(expectedEmailError, actualEmailError);
	}

	@Test
	public void getPassErrorMsgWhenIsNullOrEmpty() {
		// Given
		String expectedEmailError = context.getText(error_field_required).toString();
		// When
		String actualPassError = viewModel.getPassErrorMsg(true);
		// Then
		assertEquals(expectedEmailError, actualPassError);
	}

	@Test
	public void getPassErrorMsgWhenIsInvalid() {
		// Given
		String expectedPassError = context.getText(error_invalid_password).toString();
		// When
		String actualPassError = viewModel.getPassErrorMsg(false);
		// Then
		assertEquals(expectedPassError, actualPassError);
	}

	@Test
	public void validateEmailWhenIsNull() {
		// Given
		ObservableField<String> emailError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validateEmail(null, emailError);
		// Then
		assertFalse(actualIsValid);
	}

	@Test
	public void validateEmailWhenIsEmpty() {
		// Given
		ObservableString email = new ObservableString();
		email.set("");
		ObservableField<String> emailError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validateEmail(email, emailError);
		// Then
		assertFalse(actualIsValid);
	}

	@Test
	public void validateEmailWhenValid() {
		// Given
		ObservableString email = new ObservableString();
		email.set("test@test.com");
		ObservableField<String> emailError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validateEmail(email, emailError);
		// Then
		assertTrue(actualIsValid);
	}

	@Test
	public void validateEmailGetErrorWhenNull() {
		// Given
		ObservableField<String> actualEmailError = new ObservableField<>();
		String expectedEmailError = context.getText(R.string.error_field_required).toString();
		// When
		viewModel.validateEmail(null, actualEmailError);
		boolean isErrorCorrect = expectedEmailError.equals(actualEmailError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validateEmailGetErrorWhenIsEmpty() {
		// Given
		ObservableString email = new ObservableString();
		email.set("");
		ObservableField<String> actualEmailError = new ObservableField<>();
		String expectedEmailError = context.getText(R.string.error_field_required).toString();
		// When
		viewModel.validateEmail(email, actualEmailError);
		boolean isErrorCorrect = expectedEmailError.equals(actualEmailError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validateEmailGetErrorWhenIsInvalid() {
		// Given
		ObservableString email = new ObservableString();
		email.set("test.test.com");
		ObservableField<String> actualEmailError = new ObservableField<>();
		String expectedEmailError = context.getText(R.string.error_invalid_email).toString();
		// When
		viewModel.validateEmail(email, actualEmailError);
		boolean isErrorCorrect = expectedEmailError.equals(actualEmailError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validateEmailGetErrorWhenIsValid() {
		// Given
		ObservableString email = new ObservableString();
		email.set("test@test.com");
		ObservableField<String> actualEmailError = new ObservableField<>();
		// When
		viewModel.validateEmail(email, actualEmailError);
		boolean isErrorNull = actualEmailError.get() == null;
		// Then
		assertTrue(isErrorNull);
	}

	@Test
	public void validatePassWhenIsNull() {
		// Given
		ObservableField<String> passError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validateEmail(null, passError);
		// Then
		assertFalse(actualIsValid);
	}

	@Test
	public void validatePasslWhenEmpty() {
		// Given
		ObservableString password = new ObservableString();
		password.set("");
		ObservableField<String> passError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validatePassword(password, passError);
		// Then
		assertFalse(actualIsValid);
	}

	@Test
	public void validatePassWhenValid() {
		// Given
		ObservableString password = new ObservableString();
		password.set("45674524545");
		ObservableField<String> passError = new ObservableField<>();
		// When
		boolean actualIsValid = viewModel.validatePassword(password, passError);
		// Then
		assertTrue(actualIsValid);
	}

	@Test
	public void validatePassGetErrorWhenNull() {
		// Given
		ObservableField<String> actualPassError = new ObservableField<>();
		String expectedPassError = context.getText(R.string.error_field_required).toString();
		// When
		viewModel.validatePassword(null, actualPassError);
		boolean isErrorCorrect = expectedPassError.equals(actualPassError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validatePassGetErrorWhenIsEmpty() {
		// Given
		ObservableString password = new ObservableString();
		password.set("");
		ObservableField<String> actualPassError = new ObservableField<>();
		String expectedPassError = context.getText(R.string.error_field_required).toString();
		// When
		viewModel.validatePassword(password, actualPassError);
		boolean isErrorCorrect = expectedPassError.equals(actualPassError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validatePassGetErrorWhenIsInvalid() {
		// Given
		ObservableString password = new ObservableString();
		password.set("012");
		ObservableField<String> actualPassError = new ObservableField<>();
		String expectedPassError = context.getText(R.string.error_invalid_password).toString();
		// When
		viewModel.validatePassword(password, actualPassError);
		boolean isErrorCorrect = expectedPassError.equals(actualPassError.get());
		// Then
		assertTrue(isErrorCorrect);
	}

	@Test
	public void validatePassGetErrorWhenIsValid() {
		// Given
		ObservableString password = new ObservableString();
		password.set("9273");
		ObservableField<String> actualPassError = new ObservableField<>();
		// When
		viewModel.validatePassword(password, actualPassError);
		boolean isErrorNull = actualPassError.get() == null;
		// Then
		assertTrue(isErrorNull);
	}

	@Test
	public void onClickWhenLoginEnabledIsTrue() {
		// Given
		viewModel.isLoginEnabled = new ObservableBoolean();
		viewModel.isLoginEnabled.set(true);
		Mockito.doNothing().when(showLoginAction).call(view);
		// When
		viewModel.onLoginClick(view);
		// Then
		assertFalse(viewModel.isLoginEnabled.get());
	}

	@Test
	public void onClickWhenLoginEnabledShowLoginCalled() {
		// Given
		Mockito.doNothing().when(showLoginAction).call(view);
		viewModel.isLoginEnabled = new ObservableBoolean();
		viewModel.isLoginEnabled.set(true);
		// When
		viewModel.onLoginClick(view);
		// Then
		verify(showLoginAction).call(view);
	}

	@Test
	public void onClickWhenLoginEnabledShowLoginNotCalled() {
		// Given
		Mockito.doNothing().when(showLoginAction).call(view);
		viewModel.isLoginEnabled = new ObservableBoolean();
		viewModel.isLoginEnabled.set(false);
		// When
		viewModel.onLoginClick(view);
		// Then
		verify(showLoginAction, never()).call(view);
	}

	@Test
	public void handleValidPasswordInputWhenAllowed() {
		// Given
		Mockito.doNothing().when(focusOnNextView).call(view);
		// When
		viewModel.handleValidPasswordInput(view, true);
		boolean isLoginEnabled = viewModel.isLoginEnabled.get();
		// Then
		assertTrue(isLoginEnabled);
	}

	@Test
	public void handleValidPasswordInputWhenNotAllowed() {
		// Given
		viewModel.isLoginEnabled.set(false);
		// When
		viewModel.handleValidPasswordInput(view, false);
		boolean isLoginEnabled = viewModel.isLoginEnabled.get();
		// Then
		assertFalse(isLoginEnabled);
	}

	@Test
	public void handleValidPasswordInputWhenMethodCalled() {
		// Given
		Mockito.doNothing().when(focusOnNextView).call(view);
		// When
		viewModel.handleValidPasswordInput(view, true);
		// Then
		verify(focusOnNextView, Mockito.times(1)).call(view);
	}

	@Test
	public void handleValidPasswordInputWhenMethodNotCalled() {
		// Given
		Mockito.doNothing().when(focusOnNextView).call(view);
		// When
		viewModel.handleValidPasswordInput(view, false);
		// Then
		verify(focusOnNextView, Mockito.never()).call(view);
	}

	@Test
	public void handleValidEmailInputWhenMethodCalled() {
		// Given
		Mockito.doNothing().when(focusOnNextView).call(view);
		// When
		viewModel.handleValidEmailInput(view, true);
		// Then
		verify(focusOnNextView, Mockito.times(1)).call(view);
	}

	@Test
	public void handleValidEmailInputWhenMethodNotCalled() {
		// Given
		Mockito.doNothing().when(focusOnNextView).call(view);
		// When
		viewModel.handleValidEmailInput(view, false);
		// Then
		verify(focusOnNextView, Mockito.never()).call(view);
	}

	@Test
	public void onEditorActionTestWhenEmailValidEmailValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test@test.com");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("9763453");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.emailTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, times(1)).handleValidEmailInput(view, true);
		verify(viewModelSpy, never()).handleValidPasswordInput(view, true);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenEmailInValidEmailValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("9763453");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.emailTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, times(1)).handleValidEmailInput(view, false);
		verify(viewModelSpy, never()).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenEmailValidEmailInValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test@gmail.com");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("000");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.emailTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, times(1)).handleValidEmailInput(view, true);
		verify(viewModelSpy, never()).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenEmailInvalidEmailInvalidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test.test");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("9");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.emailTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, times(1)).handleValidEmailInput(view, false);
		verify(viewModelSpy, never()).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenPasswordInvalidEmailValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("245636");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.passwordTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, never()).handleValidEmailInput(view, false);
		verify(viewModelSpy, times(1)).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenPasswordValidEmailValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test@test.com");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("245636");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.passwordTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, never()).handleValidEmailInput(view, true);
		verify(viewModelSpy, times(1)).handleValidPasswordInput(view, true);
		assertTrue(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenPasswordValidEmailInValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test@test.com");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("000");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.passwordTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, never()).handleValidEmailInput(view, true);
		verify(viewModelSpy, times(1)).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}

	@Test
	public void onEditorActionTestWhenPasswordInValidEmailInValidPassword() {
		// Given
		ViewModel viewModelSpy = Mockito.spy(viewModel);
		viewModelSpy.email = new ObservableString();
		viewModelSpy.email.set("test.com");
		viewModelSpy.password = new ObservableString();
		viewModelSpy.password.set("632");
		viewModelSpy.isLoginEnabled = new ObservableBoolean();
		viewModelSpy.isLoginEnabled.set(false);
		when(view.getTag()).thenReturn(viewModel.passwordTag);
		// When
		viewModelSpy.onEditorAction(view);
		// Then
		verify(viewModelSpy, never()).handleValidEmailInput(view, false);
		verify(viewModelSpy, times(1)).handleValidPasswordInput(view, false);
		assertFalse(viewModelSpy.isLoginEnabled.get());
	}
}