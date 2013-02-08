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
import android.widget.TextView;

public class ProductosActivity extends Activity implements OnClickListener {
	private Button btn_newList;
	private Button btn_searchUser;
	private TextView titulo;
	private String list_name;
	private Vector productos;
	private ProgressDialog pDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productos);

		list_name = getIntent().getExtras().getString("listName");
		System.out.println("listName llega " + list_name);
		btn_newList = (Button) findViewById(R.id.btn_newList);
		btn_newList.setOnClickListener(this);
		titulo = (TextView) findViewById(R.id.listName);
		titulo.setText(list_name);
		btn_searchUser = (Button) findViewById(R.id.btn_searchUser);
		btn_searchUser.setOnClickListener(this);

		new ProductsTask().execute(list_name);
	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_newList:
			intent = new Intent(this, NewProduct.class);
			intent.putExtra("listName", list_name);
			break;
		case R.id.btn_searchUser:
			intent = new Intent(this, SearchUser.class);
		}
		startActivity(intent);

	}

	@Override
	public void onBackPressed() {
		System.out.println("Pulsa back");
		Intent setIntent = new Intent(this, ListaCompraActivity.class);
		setIntent.putExtra("listName", list_name);
		startActivity(setIntent);
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

				System.out.println("No hay productos que mostrar");
			}
		}

	}

}
