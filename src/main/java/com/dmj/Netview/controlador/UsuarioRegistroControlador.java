package com.dmj.Netview.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmj.Netview.repositorios.UsuarioRepositorio;

@Controller
public class UsuarioRegistroControlador {
	private UsuarioRepositorio useRepository;

	public UsuarioRegistroControlador(UsuarioRepositorio useService) {
		super();
		this.useRepository = useService;
	}

	public UsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}

	@PostMapping("/app/register")
	public String registerUserAccount(@ModelAttribute UsuarioRegistroDTO registrationDTO) {
		useRepository.guardar(registrationDTO);
		return "redirect:/app/login?reg_OK";

	}
}