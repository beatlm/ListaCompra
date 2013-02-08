package com.beat.library;

public class Data {
	
	private static String usuario;
	private static String user_id;
	private static String list_id;
	
	public static String getUsuario(){
		return usuario;
	}
	
	public static void setUsuario(String s)
	{
		 usuario=s;
	}

	public static String getUser_id() {
		return user_id;
	}

	public static void setUser_id(String user_id) {
		Data.user_id = user_id;
	}

	public static void setList_id(String id) {
		Data.list_id=id;
		
	}
	public static String getList_id() {
		return list_id;
	}
}
