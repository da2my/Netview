package com.dmj.Netview.modelo;

public class Caratula {
	
	private String tituloCaratula;
	
	private String urlCaratula;
	
	public Caratula() {
		
	}

	public Caratula(String tituloCaratula, String urlCaratula) {
		super();
		this.tituloCaratula = tituloCaratula;
		this.urlCaratula = urlCaratula;
	}

	public String getTituloCaratula() {
		return tituloCaratula;
	}

	public void setTituloCaratula(String tituloCaratula) {
		this.tituloCaratula = tituloCaratula;
	}

	public String getUrlCaratula() {
		return urlCaratula;
	}

	public void setUrlCaratula(String urlCaratula) {
		this.urlCaratula = urlCaratula;
	}

}
