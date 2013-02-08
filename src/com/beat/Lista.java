package com.beat;



public class Lista {
	private int id;
	private String nombre;
	private int numElementos;
	private boolean compartida;
	
	public Lista(int i,String n, int m, boolean s){
		id=i;
		nombre=n;
		numElementos=m;
		compartida=s;
	}
	public boolean isCompartida() {
		return compartida;
	}
	public void setCompartida(boolean compartida) {
		this.compartida = compartida;
	}
	public int getNumElementos() {
		return numElementos;
	}
	public void setNumElementos(int numElementos) {
		this.numElementos = numElementos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
