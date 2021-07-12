package br.com.sitemadecontas.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView("usuario/cad_usuario");
				
		return mv;
	}
	
}
