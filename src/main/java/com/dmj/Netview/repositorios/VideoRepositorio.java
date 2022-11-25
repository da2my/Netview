package com.dmj.Netview.repositorios;


import java.util.List;
import org.springframework.stereotype.Repository;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;


@Repository
public interface VideoRepositorio {

	List<Video> findAll();
	
	Video findById(String tituloVideo);
	
	List<Video> favoritosDeUsuario(Usuario usuario);
	
	
}
