package com.beat;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beat.library.Data;
import com.beat.library.DataAccess;
import com.beat.library.Httppostaux;
import com.beat.library.Messages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	
	private ProgressDialog pDialog;
	private String mUsuario;
	private String mPassword;

	// UI references.
	private EditText mUsuarioView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		
		// Set up the login form.
		mUsuarioView = (EditText) findViewById(R.id.usuario);
		mUsuarioView.setText(mUsuario);

		mPasswordView = (EditText) findViewById(R.id.password);

		/*
		 * mLoginFormView = findViewById(R.id.login_form); mLoginStatusView =
		 * findViewById(R.id.login_status); mLoginStatusMessageView = (TextView)
		 * findViewById(R.id.login_status_message);
		 */
		// Boton entrar
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});

		// Boton registrar
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						register();
					}
				});
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * super.onCreateOptionsMenu(menu);
	 * getMenuInflater().inflate(R.menu.activity_login, menu); return true; }
	 */

	public void register() {
		Intent intent = new Intent(this, RegisterUser.class);
		startActivity(intent);

	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		/*
		 * if (mAuthTask != null) { return; }
		 */

		// Reset errors.
		mUsuarioView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsuario = mUsuarioView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		System.out.println("Recogemos datos " + mUsuario + "-" + mPassword);
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(mUsuario)) {
			mUsuarioView.setError(getString(R.string.error_field_required));
			focusView = mUsuarioView;
			cancel = true;
		}

		if (cancel) {

			focusView.requestFocus();
		} else {

			new UserLoginTask().execute(mUsuario, mPassword);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	/*
	 * @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2) private void
	 * showProgress(final boolean show) { // On Honeycomb MR2 we have the
	 * ViewPropertyAnimator APIs, which allow // for very easy animations. If
	 * available, use these APIs to fade-in // the progress spinner. if
	 * (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) { int
	 * shortAnimTime = getResources().getInteger(
	 * android.R.integer.config_shortAnimTime);
	 * 
	 * mLoginStatusView.setVisibility(View.VISIBLE);
	 * mLoginStatusView.animate().setDuration(shortAnimTime) .alpha(show ? 1 :
	 * 0) .setListener(new AnimatorListenerAdapter() {
	 * 
	 * @Override public void onAnimationEnd(Animator animation) {
	 * mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE); } });
	 * 
	 * mLoginFormView.setVisibility(View.VISIBLE);
	 * mLoginFormView.animate().setDuration(shortAnimTime) .alpha(show ? 0 : 1)
	 * .setListener(new AnimatorListenerAdapter() {
	 * 
	 * @Override public void onAnimationEnd(Animator animation) {
	 * mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE); } }); }
	 * else { // The ViewPropertyAnimator APIs are not available, so simply show
	 * // and hide the relevant UI components.
	 * mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
	 * mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE); } }
	 */
	// vibra y muestra un Toast
	public void showError() {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast toast1 = Toast.makeText(getApplicationContext(),
				"Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
		toast1.show();
	}



	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<String, String, String> {
		private String user, pass;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Comprobando datos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			user = params[0];
			pass = params[1];
			System.out.println("doinbackground");
			if (DataAccess.loginStatus(user, pass) == true) {
				return "ok";
			} else {
				return "err";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();// ocultamos progess dialog.
			System.out.println("onpost " + result);
			if (result.equals("ok")) {
			Data.setUsuario(mUsuario);
				
				Intent intent = new Intent(LoginActivity.this,ListaCompraActivity.class);
				 intent.putExtra("usuario", mUsuario);
				startActivity(intent);
			} else {
				Messages.mostrarDialogoAlerta(
						"El usuario/contrase–a no son v‡lidos",
						new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}, LoginActivity.this);
			}
		}

	}
}
