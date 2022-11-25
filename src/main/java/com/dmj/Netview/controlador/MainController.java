package com.dmj.Netview.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;
import com.dmj.Netview.repositorios.UsuarioRepositorio;
import com.dmj.Netview.servicios.VideoServicio;

@Controller
public class MainController {

	@Autowired
	private VideoServicio videoServicio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	private Usuario usuario;

	// CARTELERA
	@ModelAttribute("cartelera")
	public List<Video> videosCartelera() {
		return videoServicio.findAll();
	}

	@GetMapping("/app/login/NetView")
	public String cartelera(Model model) {
		model.addAttribute("cartelera");
		return "NetView";
	}

	// FAVORITOS
	@ModelAttribute("favoritos")
	public List<Video> misFavoritos() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		return videoServicio.favoritosDeUsuario(usuario);
	}

	@GetMapping("/app/login/NetView/favs")
	public String list(Model model) {
		model.addAttribute("favoritos", videoServicio.favoritosDeUsuario(usuario));
		return "NetViewFav";
	}

	// AGREGAR A FAVORITOS
	@GetMapping("/app/login/NetView/add/{addFav}")
	public String addFavorito(Model model, @PathVariable String addFav) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		usuario.addVideoFavorito(videoServicio.findById(addFav));
		usuarioRepositorio.updateFavoritos(usuario);
		return "NetView";
	}

	@GetMapping("/app/login/NetView/del/{delFav}")
	public String borrarFavorito(Model model, @PathVariable String delFav) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		usuarioRepositorio.deleteFavorito(usuario, delFav);
		return "NetView";
	}

	// Ruta ejemplo para acceder a la pasarela
	@GetMapping("/app/login/NetView/pasarela")
	public String pasarela() {
		return "pasarela";
	}
}
