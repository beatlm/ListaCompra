package com.beat;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListaAdapter extends BaseAdapter{

 
		  protected Activity activity;
		  protected ArrayList<Lista> items;
		         
		  public ListaAdapter(Activity activity, ArrayList<Lista> items) {
		    this.activity = activity;
		    this.items = items;
		  }
		 
		  @Override
		  public int getCount() {
		    return items.size();
		  }
		 
		  @Override
		  public Object getItem(int position) {
		    return items.get(position);
		  }
		 
		  @Override
		  public long getItemId(int position) {
		    return items.get(position).getId();
		  }
		 
		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View vi=convertView;
		         
		    if(convertView == null) {
		      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		      vi = inflater.inflate(R.layout.listas_layout, null);
		    }
		             
		    Lista item = items.get(position);
		         
		   /* CheckBox shared=(CheckBox)vi.findViewById(R.id.shared);
			shared.setChecked(item.isCompartida());
		*/
		         
		    TextView nombre = (TextView) vi.findViewById(R.id.listName);
		    nombre.setText(item.getNombre());
		         
		    TextView tipo = (TextView) vi.findViewById(R.id.numProductos);
		    tipo.setText(item.getNumElementos()+" productos");
		
		    return vi;
		  }



	 

	
		}

