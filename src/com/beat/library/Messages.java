package com.beat.library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class Messages {
	public static void mostrarDialogoAlerta(String texto,OnClickListener onClick,Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setTitle("MisListas");
		builder.setMessage(texto);
		builder.setPositiveButton("OK", onClick);
		builder.create();
		builder.show();
	}
}
