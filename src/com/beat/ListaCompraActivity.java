package com.beat;

import java.util.ArrayList;
import java.util.Vector;

import com.beat.LoginActivity.UserLoginTask;
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
	private ProgressDialog pDialog;
	private String usuario;
	//private Vector<String> listas;
	private ArrayList<Lista> itemsCompra;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		usuario = Data.getUsuario();
		btn_newList = (Button) findViewById(R.id.btn_newList);
		btn_newList.setOnClickListener(this);
		new GetListsTask().execute();

	}
	
	

	@Override
	public void onClick(View v) {
		 Intent intent = null;
		 switch(v.getId()){		
         case R.id.btn_newList:
        	  intent = new Intent(this, NewList.class);    		
        	 break;
         case R.id.btn_searchUser:
        	 intent = new Intent(this, SearchUser.class);    	
     }   
		 startActivity(intent);
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println(position + "-" + id);

		System.out.println(itemsCompra.get(position).getNombre());
		
		//Guardamos el id de la lista
		
		Data.setList_id(String.valueOf(itemsCompra.get(position).getId()));
		System.out.println("ALMACENAMOS ID LISTA "+itemsCompra.get(position).getId());
		Intent intent = new Intent(ListaCompraActivity.this,
				ProductosActivity.class);
		 
		intent.putExtra("listName", itemsCompra.get(position).getNombre());
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
	 System.out.println("Pulsa back");
 
	}
	
	public class GetListsTask extends AsyncTask<String, String, String> {
	//	private String user, pass;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(ListaCompraActivity.this);
			pDialog.setMessage("Obteniendo datos....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			//user = params[0];
			//pass = params[1];
			System.out.println("doinbackground");
			/*if (DataAccess.loginStatus(user, pass) == true) {
				return "ok";
			} else {
				return "err";
			}*/
			  itemsCompra = DataAccess.getLists2(usuario);
			return "ok";
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();// ocultamos progess dialog.
			System.out.println("onpost " + result);
			ListaAdapter adaptador=new ListaAdapter(ListaCompraActivity.this,itemsCompra);
			ListView lstOpciones = (ListView) findViewById(R.id.listasV);
			lstOpciones.setAdapter(adaptador);
			lstOpciones.setOnItemClickListener(ListaCompraActivity.this);
		}

	}

}
