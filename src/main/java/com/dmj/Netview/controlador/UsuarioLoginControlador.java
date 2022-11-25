package com.dmj.Netview.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmj.Netview.modelo.Usuario;

@Controller
public class UsuarioLoginControlador {

	@GetMapping("/app/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());// recogemos el usuario creado en el form
		return "login";
	}

	@GetMapping("/")
	public String welcome() {
		return "redirect:/app/login";// http://localhost:7000/app/login
	}
	
	


	

}
