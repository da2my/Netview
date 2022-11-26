package com.dmj.Netview.modelo;

public class Video {
	
	
	private String tituloVideo;
	
	private String caratulaVideo;
	
	private String urlVideo;
	
	//private Categoria categoria;
	
	
	public Video() {
		
	}


	public Video(String tituloVideo, String caratulaVideo, String urlVideo) {
		super();
		this.tituloVideo = tituloVideo;
		this.caratulaVideo = caratulaVideo;
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

	public String getCaratulaVideo() {
		return caratulaVideo;
	}

	public void setCaratulaVideo(String caratulaVideo) {
		this.caratulaVideo = caratulaVideo;
	}

}