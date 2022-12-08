package com.dmj.Netview.repositorios;


import java.util.List;
import org.springframework.stereotype.Repository;
import com.dmj.Netview.modelo.Caratula;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;


@Repository
public interface VideoRepositorio {

	List<Video> findAll();
	
	Video findById(String tituloVideo);
	
	List<Video> favoritosDeUsuario(Usuario usuario);
	
	List<Caratula> buscarMisVideos(String tituloVideo);
	
	List<Caratula> buscarMisVideosTop(String tituloVideo);
	
	void seguirViendo(Video video, Usuario usuario);
	
	List<Video> historialVideos(Usuario usuario);
	
	void agregarHistorial(Usuario usuario);
	
	List<Video> quitarRepetidos(List<Video> videosSinRep);
	
}