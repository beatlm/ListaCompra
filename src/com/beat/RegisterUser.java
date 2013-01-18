package com.beat;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beat.LoginActivity.UserLoginTask;
import com.beat.library.DataAccess;
import com.beat.library.Httppostaux;
import com.beat.library.Messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends Activity {


	private ProgressDialog pDialog;
	private String usuario;
	private String contraseña;
	private String contraseña2;
	private String email;

	private EditText mUsuarioView;
	private EditText mContraseñaView;
	private EditText mContraseña2View;
	private EditText mEmailView;

	private UserRegisterTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	

		mUsuarioView = (EditText) findViewById(R.id.new_user_name);
		mContraseñaView = (EditText) findViewById(R.id.new_password);
		mContraseña2View = (EditText) findViewById(R.id.new_password_repeat);
		mEmailView = (EditText) findViewById(R.id.new_email);

		// Boton registrar
		findViewById(R.id.new_user_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						registrar();
					}
				});
	}


	private void registrar() {

		boolean cancel = false;
		View focusView = null;

		mUsuarioView.setError(null);
		mContraseñaView.setError(null);
		mContraseña2View.setError(null);
		mEmailView.setError(null);

		usuario = mUsuarioView.getText().toString();
		contraseña = mContraseñaView.getText().toString();
		contraseña2 = mContraseña2View.getText().toString();
		email = mEmailView.getText().toString();

		// Validacion de datos
		if (TextUtils.isEmpty(usuario)) {
			mUsuarioView.setError(getString(R.string.error_field_required));
			focusView = mUsuarioView;
			cancel = true;
		} else if (TextUtils.isEmpty(contraseña)) {
			mContraseñaView.setError(getString(R.string.error_field_required));
			focusView = mContraseñaView;
			cancel = true;
		} else if (TextUtils.isEmpty(contraseña2)) {
			mContraseña2View.setError(getString(R.string.error_field_required));
			focusView = mContraseña2View;
			cancel = true;
		} else if (!contraseña.equals(contraseña2)) {
			mContraseña2View.setError(getString(R.string.error_field_required));
			focusView = mContraseña2View;
			cancel = true;
		} else if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {

			focusView.requestFocus();
		} else {
			new UserRegisterTask().execute(usuario, contraseña, email);
		}
	}

	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserRegisterTask extends AsyncTask<String, String, String> {
		private String user, pass, email;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(RegisterUser.this);
			pDialog.setMessage("Registrando....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			user = params[0];
			pass = params[1];
			email = params[2];
			System.out.println(user + "-" + "pass" + pass + "-" + email);
			if (DataAccess.registerStatus(user, pass, email) == true) {
				return "ok";
			} else {
				return "err";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();// ocultamos progess dialog.
			if (result.equals("ok")) {

				Messages.mostrarDialogoAlerta("El registro se ha realizado con éxito.",new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent intent = new Intent(RegisterUser.this,LoginActivity.class);
						startActivity(intent);
					}
				},RegisterUser.this);
				
			} else {
				Messages.mostrarDialogoAlerta("No se ha podido registrar. Inténtelo más tarde",new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				},RegisterUser.this);

			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			// showProgress(false);
		}
	}


}
