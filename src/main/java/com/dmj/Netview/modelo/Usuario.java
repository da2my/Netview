package com.dmj.Netview.modelo;

import java.util.List;


public class Usuario {
		

	private String nombre;

	private String apellidos;

	private String email;

	private String contrasena;

	private boolean pago;

	private List<Role> roles;
	
	private List<Video> favoritos;
	
	private List<Video> seguirViendo;
	
	private List<Video> historialVideos;
	

	// constructor por defecto
	public Usuario() {

	}

	// constructor
	public Usuario(String nombre, String apellidos, String email, String contrasena, boolean pago, List<Role> roles,
			List<Video> favoritos, List<Video> seguirViendo, List<Video> historialVideos) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.pago = pago;
		this.roles = roles;
		this.favoritos = favoritos;
		this.setSeguirViendo(seguirViendo);
		this.historialVideos = historialVideos;
	}


	// Getters Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
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

	public List<Video> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Video> favoritos) {
		this.favoritos = favoritos;
	}
	
	//agregar a favoritos
	public void addVideoFavorito(Video v) {
		this.favoritos.add(v);
	}

	public List<Video> getSeguirViendo() {
		return seguirViendo;
	}

	public void setSeguirViendo(List<Video> seguirViendo) {
		this.seguirViendo = seguirViendo;
	}

	public List<Video> getHistorialVideos() {
		return historialVideos;
	}

	public void setHistorialVideos(List<Video> historialVideos) {
		this.historialVideos = historialVideos;
	}

}
