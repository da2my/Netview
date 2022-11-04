package com.dmj.Netview.repositorios;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import com.dmj.Netview.modelo.Usuario;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;


@Repository
public interface UsuarioRepositorio extends UserDetailsService {
	
	ApiFuture<DocumentReference> guardar(Usuario registrationUser) throws Exception;

}
