package br.com.sitemadecontas.usuario;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("usuario/login");
		
		return mv;
	}
	
	@GetMapping("usuarios/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView("usuario/cad_usuario");
				
		return mv;
	}
	
	@PostMapping("usuarios/cadastrar/create")
	public ModelAndView cadastrar(@Valid UsuarioRequest usuarioRequest) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/login");
		Usuario usuario = usuarioRequest.toModel();
		usuarioService.salva(usuario);
		return mv;
	}
	
}
