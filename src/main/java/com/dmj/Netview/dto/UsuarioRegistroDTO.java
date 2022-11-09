package com.dmj.Netview.dto;

public class UsuarioRegistroDTO {

	private String nombre;
	private String apellido;
	private String email;
	private String contrasena;
	
	public UsuarioRegistroDTO() {
		
	}
	
	public UsuarioRegistroDTO(String nombre, String apellido, String email, String contrasena) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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
