package br.com.sitemadecontas.usuario;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sitemadecontas.conta.Conta;
import br.com.sitemadecontas.conta.ContaController;
import br.com.sitemadecontas.conta.ContaRepository;
import br.com.sitemadecontas.exceptions.CriptoException;
import br.com.sitemadecontas.exceptions.NomeExistsException;
import br.com.sitemadecontas.exceptions.ServiceLoginException;
import br.com.sitemadecontas.util.Util;

@Controller
@RequestMapping("")
public class UsuarioController {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
		
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("usuario/login");
		
		return mv;
	}
	
	@PostMapping("/contas/home")
	public ModelAndView logar(@Valid UsuarioRequest usuarioRequest, HttpSession session) throws NoSuchAlgorithmException, ServiceLoginException{
		ModelAndView mv = new ModelAndView("usuario/login");
		
		Usuario usuario = usuarioService.login(usuarioRequest.getNome(),Util.md5(usuarioRequest.getSenha()));
		
		
		if(usuario == null) {
			mv.addObject("msg", "Usuário ou senha inválido");
		}else {
			session.setAttribute("usuarioLogado", usuario);
			session.setAttribute("count",0);
			ContaController contaController = new ContaController(contaRepository,session);
			return contaController.index();
		}
		
		
		
		return mv;
	}

	@GetMapping("usuarios/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView("usuario/cad_usuario");
				
		return mv;
	}
	
	@PostMapping("usuarios/cadastrar/create")
	public ModelAndView cadastrar(@Valid UsuarioRequest usuarioRequest) throws Exception{
		ModelAndView mv = new ModelAndView();
		Usuario usuario = usuarioRequest.toModel();
		try {
			usuarioService.salva(usuario);
		}catch (NomeExistsException e) {
			mv.setViewName("usuario/cad_usuario");
			mv.addObject("msg",e.getMessage());
			return mv;
		}catch (CriptoException e){
			mv.setViewName("usuario/cad_usuario");
			mv.addObject("msg",e.getMessage());
			return mv;
		}
		
		mv.setViewName("redirect:/login");
		
		return mv;
	}
	
}
