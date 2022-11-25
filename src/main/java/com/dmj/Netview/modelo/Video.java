package com.dmj.Netview.modelo;

public class Video {
	
	
	private String tituloVideo;
	
	//private String descripcion;
	
	private String urlVideo;//url video
	
	//private Categoria categoria;
	
	
	public Video() {
		
	}

	public Video(String tituloVideo, String urlVideo) {
		super();
		this.tituloVideo = tituloVideo;
		this.urlVideo = urlVideo;
	}

	public String getTituloVideo() {
		return tituloVideo;
	}

	public void setTituloVideo(String tituloVideo) {
		this.tituloVideo = tituloVideo;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

}