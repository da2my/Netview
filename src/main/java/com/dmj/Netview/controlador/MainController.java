package com.dmj.Netview.controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dmj.Netview.modelo.Caratula;
import com.dmj.Netview.modelo.Usuario;
import com.dmj.Netview.modelo.Video;
import com.dmj.Netview.repositorios.UsuarioRepositorio;
import com.dmj.Netview.servicios.CaratulaServicio;
import com.dmj.Netview.servicios.VideoServicio;
import com.dmj.Netview.utils.DataBucketUtil;
import com.dmj.Netview.utils.DeleteObject;
import com.dmj.Netview.utils.UploadObject;

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

		if (query != null) {
			model.addAttribute("cartelera", videoServicio.buscarMisVideos(query));
			model.addAttribute("usuarioPago", usuario);
		} else {
			model.addAttribute("cartelera");
			model.addAttribute("usuarioPago", usuario);
		}

		return "NetView";
	}

	// TOP VALORADAS
	@ModelAttribute("topValoradas")
	public List<Caratula> caratulasValoradas() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioRepositorio.buscarPorEmail(email);
		return caratulaServicio.findValoradas();
	}

	@GetMapping("/app/login/NetView/top")
	public String listTop(Model model, @RequestParam(name = "q", required = false) String query) {

		if (query != null) {
			model.addAttribute("topValoradas", videoServicio.buscarMisVideosTop(query));
			model.addAttribute("usuarioPago", usuario);
		} else {
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
		
		if(videoServicio.findById(titleCV).getTituloVideo() != null) {
			//esto es para seguir viendo
			videoServicio.seguirViendo(videoServicio.findById(titleCV), usuario);
			//esto es para agregar al historial
			usuario.addHistorialVideos(videoServicio.findById(titleCV));
			videoServicio.agregarHistorial(usuario);
		}
		
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

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
			RedirectAttributes attributes) throws IOException {

		// check if file is empty
		if (file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor, seleccione un archivo a subir.");
			return "redirect:/NetViewMensaje";
		}

		File archivo = null;

		DataBucketUtil dataBucketUtil = new DataBucketUtil();
		archivo = dataBucketUtil.convertFile(file);

		// objectName
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// guardar archivo en storage
		UploadObject uploadObject = new UploadObject();
		UploadObject.uploadObject("netviewtest2", "netview_test2", fileName, archivo.getAbsolutePath());

		//https://storage.googleapis.com/netview_test2/Black%20Adam.mp4
		videoServicio.agregarVideoAdmin(new Video(fileName.replace(".mp4", ""), "https://storage.googleapis.com/netview_test2/"+fileName));

		// borrar archivo
		archivo.delete();

		// return success response
		attributes.addFlashAttribute("message", "Tu archivo se ha subido correctamente" + fileName + '!');
		return "redirect:/NetViewMensaje";
	}


	@GetMapping("/delete/{buckDel}")
	public String borrarVideoStorage(RedirectAttributes attributes, @PathVariable String buckDel){
		
		DeleteObject deleteObject = new DeleteObject();
		DeleteObject.deleteObject("netviewtest2", "netview_test2", buckDel + ".mp4");

		videoServicio.eliminarVideoAdminBbdd(buckDel);
		
		attributes.addFlashAttribute("message", "Tu archivo se ha borrado corectamente");

		return "redirect:/NetViewMensaje";
	}
	


	@GetMapping("/NetViewMensaje")
	public String paginaMensaje() {
		return "NetViewMensaje";
	}
	//configuracion usuario
    @GetMapping("/app/login/NetView/cfgUsu")
    public String configuracionUsuario() {
        return "NetViewcfgUsu";
    }

    //configuracion suscripcion
    @GetMapping("/app/login/NetView/cfgSusc")
    public String configuracion() {
        return "NetViewSusc";
    }

    //configuracion avatar
    @GetMapping("/app/login/NetView/avt")
    public String ConfiguracionAvatar() {
        return "NetViewAvt";
    }
	
	//cancelar suscripcion
    @GetMapping("/app/login/NetView/cfgSusc/cancel")
    public String CancelarSuscripcion() {
		usuarioRepositorio.actualizarpago(usuario, false);
		return "redirect:https://billing.stripe.com/p/login/test_8wM3f91T90hg7kI144";
    }
    
	// SEGUIR VIENDO GUARDAR
	@GetMapping("/app/login/NetView/favs/{ultVid}")
	public String guardarSeguirViendo(@PathVariable String ultVid) {
		videoServicio.findById(ultVid);
		videoServicio.seguirViendo(videoServicio.findById(ultVid), usuario);
		
		//esto es para agregar al historial
		usuario.addHistorialVideos(videoServicio.findById(ultVid));
		videoServicio.agregarHistorial(usuario);
		
		return "redirect:/app/login/NetView/favs";
	}
	
	//SEGUIR VIENDO REPRODUCIR
	@GetMapping("/app/login/NetView/sala")
	public String reproducirSeguirViendo(Model model) {
		
		if(!usuario.getSeguirViendo().isEmpty()) {
			model.addAttribute(usuario.getSeguirViendo().get(0));
		}else {
			model.addAttribute(new Video());
		}
		// objeto usuario para gestion de ROLE_ADMIN
		model.addAttribute(usuario);
		
		return "NetView_sala";
	}

	// HISTORIAL VIDEOS
	@ModelAttribute("historialVideos")
	public List<Video> historialVideo() {
		return videoServicio.historialVideos(usuario);
	}
	
	@GetMapping("/app/login/NetView/hist")
	public String listHistorialVideo(Model model) {
		model.addAttribute("historialVideos", videoServicio.historialVideos(usuario));
		return "NetViewHist";
	}
}
