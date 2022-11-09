package com.dmj.Netview.repositorios;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import com.dmj.Netview.dto.UsuarioRegistroDTO;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;


@Repository
public interface UsuarioRepositorio extends UserDetailsService {
	
	ApiFuture<DocumentReference> guardar(UsuarioRegistroDTO registrationDTO) throws Exception;

}
