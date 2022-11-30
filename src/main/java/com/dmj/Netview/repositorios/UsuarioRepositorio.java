package com.dmj.Netview.repositorios;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import com.dmj.Netview.dto.UsuarioRegistroDTO;
import com.dmj.Netview.modelo.Usuario;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;



@Repository
public interface UsuarioRepositorio extends UserDetailsService {
	
	ApiFuture<WriteResult> guardar(UsuarioRegistroDTO registrationDTO) throws Exception;
	
	Usuario buscarPorEmail(String email);

	void updateFavoritos(Usuario usuario);

	void deleteFavorito(Usuario usuario, String tituloVideoFavUser);

	void actualizarpago(Usuario usuario, Boolean success);

}
