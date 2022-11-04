package com.dmj.Netview.servicios;

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

import com.dmj.Netview.modelo.Role;
import com.dmj.Netview.modelo.Usuario;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class UsuarioServicioImpl implements UsuarioRepositorio{
    @Autowired
	private Firestore database;

	@Lazy
	@Autowired
	private BCryptPasswordEncoder codificarContrasena;
	


	@Override
	public ApiFuture<DocumentReference> guardar(Usuario registrationUser) throws Exception {
		
		Role roleUsuario = new Role();
		
		//Creacion identificacion de roles de usuario (inicio nombre con n@tvi@w -> admin)
		String[] adminRol = registrationUser.getNombre().split(" "); 
		
		if(adminRol[0].equals("n@tvi@w")) {
			roleUsuario.setNombre("ROLE_ADMIN");
			registrationUser.setNombre(adminRol[1]);
		}else {
			roleUsuario.setNombre("ROLE_USER");
		}
		
		
		Usuario user = new Usuario(registrationUser.getNombre(), registrationUser.getApellidos(),
				registrationUser.getEmail(), codificarContrasena.encode(registrationUser.getContrasena()),
				Arrays.asList(roleUsuario));
		
		
		


		return database.collection("Usuarios").add(user);


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
}
