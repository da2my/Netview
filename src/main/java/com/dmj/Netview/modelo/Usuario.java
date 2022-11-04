package com.dmj.Netview.modelo;

import java.util.List;

public class Usuario {
		
	//el id se autogenera en firebase, no creamos un atributo para ello

	private String nombre;

	private String apellidos;
	
	private String email;
	
	private String contrasena;
	
	
	//No esta importado el DATE tenemos 4 imports(revisar cual es el correcto)
	//private Date fechaAlta;//para controlar pasarela de pagos ¿?
	
	
	private List<Role> roles;
	
	
	//constructor por defecto
	public Usuario() {
		
	}
	
	//constructor
	public Usuario(String nombre, String apellidos, String email, String contrasena, List<Role> roles) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.roles = roles;
	}

	//Getters Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
