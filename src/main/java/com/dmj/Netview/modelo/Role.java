package com.dmj.Netview.modelo;

public class Role {
	
	//Clase para diferenciar los Usuarios de Administradores
	
	private String nombre;
	
	public Role() {
		
	}
		
	public Role(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}