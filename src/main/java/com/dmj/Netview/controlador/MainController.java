package com.dmj.Netview.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmj.Netview.modelo.Caratula;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;
import com.dmj.Netview.repositorios.UsuarioRepositorio;
import com.dmj.Netview.servicios.CaratulaServicio;
import com.dmj.Netview.servicios.VideoServicio;

@Controller
public class MainController {

	@Autowired
	private VideoServicio videoServicio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private CaratulaServicio caratulaServicio;

	private Usuario usuario;

	// CARTELERA
	@ModelAttribute("cartelera")
	public List<Caratula> caratulasCartelera() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		return caratulaServicio.findAll();
	}

	@GetMapping("/app/login/NetView")
	public String cartelera(Model model, @RequestParam(name = "q", required = false) String query) {

		if(query != null) {
			model.addAttribute("cartelera", videoServicio.buscarMisVideos(query));
			model.addAttribute("usuarioPago", usuario);
		}else {
			model.addAttribute("cartelera");
			model.addAttribute("usuarioPago", usuario);
		}
		
		return "NetView";
	}
	
	//TOP VALORADAS
	@ModelAttribute("topValoradas")
	public List<Caratula> caratulasValoradas() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		return caratulaServicio.findValoradas();
	}

	@GetMapping("/app/login/NetView/top")
	public String listTop(Model model, @RequestParam(name = "q", required = false) String query) {

		if(query != null) {
			model.addAttribute("topValoradas", videoServicio.buscarMisVideosTop(query));
			model.addAttribute("usuarioPago", usuario);
		}else {
			model.addAttribute("topValoradas");
			model.addAttribute("usuarioPago", usuario);
		}
		
		return "NetView_top";
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

	// AGREGAR BORRAR FAVORITOS
	@GetMapping("/app/login/NetView/add/{addFav}")
	public String addFavorito(@PathVariable String addFav) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		usuario.addVideoFavorito(videoServicio.findById(addFav));
		usuarioRepositorio.updateFavoritos(usuario);
		return "redirect:/app/login/NetView/favs";
	}

	@GetMapping("/app/login/NetView/del/{delFav}")
	public String borrarFavorito(Model model, @PathVariable String delFav) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		usuarioRepositorio.deleteFavorito(usuario, delFav);
		return "redirect:/app/login/NetView/favs";
	}

	// REPRODUCCION VIDEOS
	@GetMapping("/app/login/NetView/cartVIP/{titleCV}")
	public String carteleraVip(Model model, @PathVariable String titleCV) {
		model.addAttribute(videoServicio.findById(titleCV));
		// objeto usuario para gestion de ROLE_ADMIN
		model.addAttribute(usuario);
		return "NetView_sala";
	}

	// PASARELA DE PAGOS
	@GetMapping("/app/login/NetView/pasarela")
	public String pasarela() {
		return "pasarela";
	}

	// PAGO EXITO
	@GetMapping("/app/login/NetView/pagoexito/{success}")
	public String pagoexito(@PathVariable boolean success) {
		usuarioRepositorio.actualizarpago(usuario, success);
		return "pagoexito";
	}
}
