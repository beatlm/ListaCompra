package com.beat.library;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beat.Lista;

import android.annotation.SuppressLint;
import android.util.Log;

public class DataAccess {
	private static Httppostaux post = new Httppostaux();
	private static String IP_Server = "beatlm.hol.es";

	private static String URL_connect;

	public static boolean loginStatus(String user, String password) {
		URL_connect = "http://" + IP_Server + "/login.php";
		int logStatus = -1;

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("usuario", user));
		postParameters.add(new BasicNameValuePair("password", password));

		Log.e("postParameters", postParameters.toString());
		Log.e("url", URL_connect);

		JSONArray jData = post.getServerData(postParameters, URL_connect);

		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {
				jsonData = jData.getJSONObject(0);
				logStatus = jsonData.getInt("logstatus");
			} catch (JSONException e) {
				e.printStackTrace();

			}

			if (logStatus == -1) {
				Log.e("logstatus", "invalido");
				return false;
			} else {
				Data.setUser_id( Integer.toString(logStatus));
				Log.e("logstatus", Integer.toString(logStatus));
				return true;
			}

		} else {
			return false;
		}

	}

	public static boolean registerStatus(String user, String password,
			String email) {
		URL_connect = "http://" + IP_Server + "/register.php";
		int regStatus = -1;

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("user", user));
		postParameters.add(new BasicNameValuePair("password", password));
		postParameters.add(new BasicNameValuePair("email", email));

		JSONArray jData = post.getServerData(postParameters, URL_connect);

		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {
				jsonData = jData.getJSONObject(0);
				regStatus = jsonData.getInt("regstatus");
			} catch (JSONException e) {
				e.printStackTrace();

			}

			if (regStatus == 0) {
				Log.e("regStatus", "invalido");
				return false;
			} else {
				Log.e("regStatus", "valido");
				return true;
			}

		} else {
			return false;
		}

	}

	public static Vector<String> getLists(String user) {
		URL_connect = "http://" + IP_Server + "/getLists.php";
		Vector<String> result=new Vector<String>();
		 

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("user", user));

		JSONArray jData = post.getServerData(postParameters, URL_connect);
	 
		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {

				for (int i = 0; i < jData.length(); i++) {
					jsonData = jData.getJSONObject(i);
					result.add( jsonData.getString("listName"));
			 
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

		} 
		return result;
	}
	
	public static ArrayList<Lista> getLists2(String user) {
		URL_connect = "http://" + IP_Server + "/getLists.php";
		ArrayList<Lista> result=new ArrayList<Lista>();
		 

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("user", user));

		JSONArray jData = post.getServerData(postParameters, URL_connect);
	 
		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {

				for (int i = 0; i < jData.length(); i++) {
					
					jsonData = jData.getJSONObject(i);
					
					//Consulta para conocer el numero de productos
					
					URL_connect = "http://" + IP_Server + "/getNumProducts.php";
					postParameters.clear();
					postParameters.add(new BasicNameValuePair("id_list", jsonData.getString("id_list")));
					JSONArray jDataNum = post.getServerData(postParameters, URL_connect);
					JSONObject jsonDataNum= jDataNum.getJSONObject(0);

					
					
					System.out.println(jsonData.getInt("id_list")+"-"+jsonData.getString("listName")+"-"+jsonDataNum.getInt("num")+"-"+jsonData.getInt("shared"));
					Lista l= new Lista(jsonData.getInt("id_list"),jsonData.getString("listName"),jsonDataNum.getInt("num"),jsonData.getInt("shared")==1);
					result.add(l);
			 
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
		return result;
	}
	
	public static Vector<String> getProducts(String list_name) {
		URL_connect = "http://" + IP_Server + "/getProducts.php";
		Vector<String> result=new Vector<String>();
		 

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("listname", list_name));

		JSONArray jData = post.getServerData(postParameters, URL_connect);
	 
		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {

				for (int i = 0; i < jData.length(); i++) {
					jsonData = jData.getJSONObject(i);
					result.add( jsonData.getString("productname"));
			 
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

		} 
		return result;
	}
	
	
	public static boolean newListStatus(String list_name,String user) {
		URL_connect = "http://" + IP_Server + "/newlist.php";
		int regStatus = -1;

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("listName", list_name));
		postParameters.add(new BasicNameValuePair("user", user));

		JSONArray jData = post.getServerData(postParameters, URL_connect);

		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {
				jsonData = jData.getJSONObject(0);
				regStatus = jsonData.getInt("insert");
			} catch (JSONException e) {
				e.printStackTrace();

			}

			if (regStatus == 0) {
				Log.e("insert", "invalido");
				return false;
			} else {
				Log.e("insert", "valido");
				return true;
			}

		} else {
			return false;
		}

	}

	
	public static boolean newProductStatus(String list_name,String product_name) {
		URL_connect = "http://" + IP_Server + "/newproduct.php";
		int regStatus = -1;

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("listName", list_name));
		postParameters.add(new BasicNameValuePair("productName", product_name));

		JSONArray jData = post.getServerData(postParameters, URL_connect);

		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {
				jsonData = jData.getJSONObject(0);
				regStatus = jsonData.getInt("insert");
			} catch (JSONException e) {
				e.printStackTrace();

			}

			if (regStatus == 0) {
				Log.e("insert", "invalido");
				return false;
			} else {
				Log.e("insert", "valido");
				return true;
			}

		} else {
			return false;
		}

	}
	
	
	public static ArrayList<Vector> getUser(String user) {
		URL_connect = "http://" + IP_Server + "/getUser.php";
		ArrayList<Vector> result=new ArrayList<Vector>();
		 

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("user", user));

		JSONArray jData = post.getServerData(postParameters, URL_connect);
	 
		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {

				for (int i = 0; i < jData.length(); i++) {
					Vector<String> v=new Vector<String>();
					jsonData = jData.getJSONObject(i);
					v.add(jsonData.getString("username"));
					v.add(jsonData.getString("id_user"));
					result.add(v);
					
					//result.add( jsonData.getString("username"));
			 
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

		} 
		return result;
	}
	
	
	public static boolean compartir(String userId,String listId ) {
		URL_connect = "http://" + IP_Server + "/share.php";
		int logStatus = -1;

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("userId", userId));
		postParameters.add(new BasicNameValuePair("listId", listId));

		Log.e("postParameters", postParameters.toString());
		Log.e("url", URL_connect);

		JSONArray jData = post.getServerData(postParameters, URL_connect);

		if (jData != null && jData.length() > 0) {
			JSONObject jsonData;

			try {
				jsonData = jData.getJSONObject(0);
				logStatus = jsonData.getInt("share");
			} catch (JSONException e) {
				e.printStackTrace();

			}

			if (logStatus !=0) {
				Log.e("share", "invalido");
				return false;
			} else {
				Data.setUser_id( Integer.toString(logStatus));
				Log.e("share", Integer.toString(logStatus));
				return true;
			}

		} else {
			return false;
		}

	}
	
}
