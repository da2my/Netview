package com.dmj.Netview.dto;

public class UsuarioRegistroDTO {
	
	private String nombre;
	private String apellidos;
	private String email;
	private String contrasena;
	
	
	public UsuarioRegistroDTO() {
		
	}

	public UsuarioRegistroDTO(String nombre, String apellidos, String email, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
	}
	

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
	
}
