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

public class NewProduct extends Activity implements OnClickListener{
private Button btn_new;
private EditText product_name;
private String listName;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_product);
		btn_new=(Button)findViewById(R.id.newProductBtn);
		btn_new.setOnClickListener(this);
		product_name=(EditText)findViewById(R.id.newProductName);
		
		  listName=	getIntent().getExtras().getString("listName");
		
	}


	@Override
	public void onClick(View v) {
		boolean result=DataAccess.newProductStatus(listName,product_name.getText().toString());
		
		if(result){
			Intent intent = new Intent(this, ProductosActivity.class);
			intent.putExtra("listName",listName);
			startActivity(intent);
		}else{
			Messages.mostrarDialogoAlerta("Ya existe un producto con ese nombre", null, this);
		}
		
		
	}

}
