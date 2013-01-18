package com.beat;

import com.beat.library.Data;
import com.beat.library.DataAccess;
import com.beat.library.Messages;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewList extends Activity implements OnClickListener{
private Button btn_new;
private EditText list_name;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_list);
		btn_new=(Button)findViewById(R.id.newListBtn);
		btn_new.setOnClickListener(this);
		list_name=(EditText)findViewById(R.id.newListName);
		
	}


	@Override
	public void onClick(View v) {
		boolean result=DataAccess.newListStatus(list_name.getText().toString(),Data.getUser_id());
		
		if(result){
			Intent intent = new Intent(this, ListaCompraActivity.class);
			startActivity(intent);
		}else{
			Messages.mostrarDialogoAlerta("Ya existe una lista con ese nombre", null, this);
		}
		
		
	}

}
