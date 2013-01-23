package com.beat;

import java.util.Vector;

import com.beat.library.Data;
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

public class ListaCompraActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private Button btn_newList;
	private String usuario;
	private Vector listas;
	private Vector productos;
	private ProgressDialog pDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		usuario = Data.getUsuario();//getIntent().getExtras().getString("usuario");
		// usuario=getIntent().getExtras().getString("id_lista");
		System.out.println("usuario llega " + usuario);
		btn_newList = (Button) findViewById(R.id.btn_newList);
		btn_newList.setOnClickListener(this);

		// Cargamos las listas del usuario

		listas = DataAccess.getLists(usuario);

		System.out.println("v lengt " + listas.size());
		String[] data;

		if (listas.size() > 0) {
			data = (String[]) listas.toArray(new String[0]);
		} else {

			data = new String[0];
		}

		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);

		ListView lstOpciones = (ListView) findViewById(R.id.listasV);

		lstOpciones.setAdapter(adaptador);
		lstOpciones.setOnItemClickListener(this);

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

		System.out.println(listas.elementAt(position));
		Intent intent = new Intent(ListaCompraActivity.this,
				ProductosActivity.class);
		 
		intent.putExtra("listName", listas.elementAt(position).toString());
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
	 System.out.println("Pulsa back");
 
	}

}
