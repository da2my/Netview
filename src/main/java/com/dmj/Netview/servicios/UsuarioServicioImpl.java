package com.dmj.Netview.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dmj.Netview.dto.UsuarioRegistroDTO;
import com.dmj.Netview.modelo.Role;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;
import com.dmj.Netview.repositorios.UsuarioRepositorio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;


@Service
public class UsuarioServicioImpl implements UsuarioRepositorio{
	
    @Autowired
	private Firestore database;

	@Lazy
	@Autowired
	private BCryptPasswordEncoder codificarContrasena;
	


	@Override
	public ApiFuture<WriteResult> guardar(UsuarioRegistroDTO registrationDto) throws Exception {
		
		Role roleUsuario = new Role();
		boolean pago = false;
		
		
		if(!checkEmail(registrationDto.getEmail())){
			
			// Creacion identificacion de roles de usuario (inicio nombre con n@tvi@w name -> admin)
			String[] adminRol = registrationDto.getNombre().split(" ");

			if (adminRol[0].equals("n@tvi@w")) {
				roleUsuario.setNombre("ROLE_ADMIN");
				registrationDto.setNombre(adminRol[1]);
				pago = true;
			} else {
				roleUsuario.setNombre("ROLE_USER");
			}

			Usuario user = new Usuario(registrationDto.getNombre(), registrationDto.getApellidos(),
					registrationDto.getEmail(), codificarContrasena.encode(registrationDto.getContrasena()), pago,
					Arrays.asList(roleUsuario), new ArrayList<Video>(), new ArrayList<Video>(), new ArrayList<Video>());// debe de estar a false xq un user no puede pagar sin antes registrarse

			return database.collection("Usuarios").document(user.getEmail()).set(user);
						
			
		}
		
		return null;

	}

	@SuppressWarnings("unused")
	public UserDetails loadUserByUsername(String username) {

		try {

			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", username).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			DocumentSnapshot doc = documents.get(0);
			Usuario user = new Usuario();
			user = doc.toObject(Usuario.class);
			
			if (user == null) {
				throw new UsernameNotFoundException("Nombre o contraseña incorrecta.");
			}

			
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getContrasena(),
					mapRolesToAuthorities(user.getRoles()));

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
	
	public boolean checkEmail(String email) {

		try {

			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", email).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			
			if(!documents.isEmpty()) {
				return true;
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;

	}

	@Override
	public Usuario buscarPorEmail(String email) {
				
		try {
			
			ApiFuture<QuerySnapshot> future = database.collection("Usuarios").whereEqualTo("email", email).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			DocumentSnapshot doc = documents.get(0);
			Usuario user = new Usuario();
			user = doc.toObject(Usuario.class);

			return user;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public void updateFavoritos(Usuario usuario) {
		
		database.collection("Usuarios").document(usuario.getEmail()).update("favoritos", usuario.getFavoritos());

	}

	@Override
	public void deleteFavorito(Usuario usuario, String tituloVideoFavUser) {
		
		List<Video> favUser = usuario.getFavoritos();
		
		favUser.removeIf(v->v.getTituloVideo().equals(tituloVideoFavUser));

		database.collection("Usuarios").document(usuario.getEmail()).update("favoritos", favUser);
	
	}

	@Override
	public void actualizarpago(Usuario usuario, Boolean success) {

		database.collection("Usuarios").document(usuario.getEmail()).update("pago", success);

	}
}
