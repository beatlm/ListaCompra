package com.beat;

import java.util.ArrayList;
import java.util.Vector;

import com.beat.library.Data;
import com.beat.library.DataAccess;
import com.beat.library.Messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchUser extends Activity implements OnClickListener,OnItemClickListener{
private Button btn_search;
private EditText user_name;
private ArrayList<Vector> users;
private ProgressDialog pDialog;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_user);
		btn_search=(Button)findViewById(R.id.searchUserBtn);
		btn_search.setOnClickListener(this);
		user_name=(EditText)findViewById(R.id.userName);
		
	}


	@Override
	public void onClick(View v) {
		users=DataAccess.getUser(user_name.getText().toString());
		
		System.out.println("v lengt " + users.size());
		String[] data = new String[users.size()];

	
		if (users.size() > 0) {
			
			// Obtenemos los nombres para el adapter
			for (int i = 0; i < users.size(); i++) {
				Vector<String> ve = users.get(i);
				data[i] = (String) ve.elementAt(0);
			}
			
			
			//data = (String[]) users.toArray(new String[0]);
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);

				ListView lstOpciones = (ListView) findViewById(R.id.listUsers);

				lstOpciones.setAdapter(adaptador);
				lstOpciones.setOnItemClickListener(this);
			} else {
				Messages.mostrarDialogoAlerta("No existe ningun usuario con ese nombre", null, this);
				//data = new String[0];
			}	
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println(position + "-" + id);

		System.out.println("Nombre:"+users.get(position).elementAt(0));
		System.out.println("ID:"+users.get(position).elementAt(1));
		new CompartirTask().execute(users.get(position).elementAt(1).toString(),Data.getList_id());
	 
	}
	
	
	public class CompartirTask extends AsyncTask<String, String, String> {
		 	private String userId,listId;

			@Override
			protected void onPreExecute() {
				pDialog = new ProgressDialog(SearchUser.this);
				pDialog.setMessage("Compartiendo....");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			@Override
			protected String doInBackground(String... params) {
				userId = params[0];
				listId = params[1];
				System.out.println("doinbackground");
				if(DataAccess.compartir(userId,listId)){
					return "ok";
				}else{
					return "ko";
				}
			
				
			}

			@Override
			protected void onPostExecute(String result) {
				Toast toast1;
				pDialog.dismiss();// ocultamos progess dialog.
				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(200);
				if(result.equals("ok")){
			
					 toast1 = Toast.makeText(getApplicationContext(),
							"La lista se ha compartido", Toast.LENGTH_SHORT);
				}else{
				
					 toast1 = Toast.makeText(getApplicationContext(),
							"No se ha podido compartir la lista :(", Toast.LENGTH_SHORT);
					
				}
				toast1.show();
				System.out.println("onpost " + result);
		 
			}

		}


}
