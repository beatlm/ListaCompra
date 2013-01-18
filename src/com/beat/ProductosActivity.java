package com.beat;

import java.util.Vector;

import com.beat.LoginActivity.UserLoginTask;
import com.beat.library.DataAccess;
import com.beat.library.Messages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProductosActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private Button btn_newList;
	private String list_name;
	private Vector productos;
	private ProgressDialog pDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productos);

		list_name = getIntent().getExtras().getString("list_name");
		System.out.println("list_name llega " + list_name);
		btn_newList = (Button) findViewById(R.id.btn_newList);
		btn_newList.setOnClickListener(this);

		// Cargamos las listas del usuario

		/*
		 * listas = DataAccess.getLists(usuario);
		 * 
		 * System.out.println("v lengt " + listas.size()); String[] data;
		 * 
		 * if (listas.size() > 0) { data = (String[]) listas.toArray(new
		 * String[0]); } else {
		 * 
		 * data = new String[0]; }
		 * 
		 * ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, data);
		 * 
		 * ListView lstOpciones = (ListView) findViewById(R.id.listasV);
		 * 
		 * lstOpciones.setAdapter(adaptador);
		 * lstOpciones.setOnItemClickListener(this);
		 */

		new ProductsTask().execute(list_name);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, NewList.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println(position + "-" + id);

		// System.out.println(listas.elementAt(position));

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class ProductsTask extends AsyncTask<String, String, String> {
		private String list_name;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(ProductosActivity.this);
			pDialog.setMessage("Cargando Productos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			list_name = params[0];
			// pass = params[1];
			System.out.println("doinbackground");
			productos = DataAccess.getProducts(list_name);
			if (productos.size() > 0) {
				return "ok";
			} else {
				return "empty";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();// ocultamos progess dialog.
			System.out.println("onpost " + result);
			if (result.equals("ok")) {

				String[] data;

				data = (String[]) productos.toArray(new String[0]);

				ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
						ProductosActivity.this,
						android.R.layout.simple_list_item_1, data);

				ListView lstOpciones = (ListView) findViewById(R.id.listasV);

				lstOpciones.setAdapter(adaptador);
			} else {
				/*Messages.mostrarDialogoAlerta(
						"El usuario/contrase–a no son v‡lidos",
						new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}, LoginActivity.this);*/
				System.out.println("No hay productos que mostrar");
			}
		}

	}

}
