package br.com.sitemadecontas.conta;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sitemadecontas.usuario.Usuario;
import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
@RequestMapping("/contas")
public class ContaController {
	
	private HttpSession session;
	
	@Autowired
	private ContaRepository contaRepository;
	
	public ContaController(ContaRepository contaRepository, HttpSession session) {
		this.contaRepository = contaRepository;
		this.session = session;
	}
	
	@GetMapping("/home")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		int count = (int) session.getAttribute("count");
		if(usuario != null) {
			mv.setViewName("index");
			List<Conta> contasPendentes = contaRepository.findByUsuarioAndPaga(usuario,false);
			mv.addObject("contasPendentes",contasPendentes);
			session.setAttribute("count", count+1);
			return mv;
		}else {
			mv.setViewName("usuario/login");
			return mv;
		}
	}
	
	@PostMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("usuario/login");
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(usuario != null) {
			session.setAttribute("usuarioLogado", null);
			session.invalidate();
		}
		mv.addObject("msg", "Seção encerrada");
		return mv;
	}
}
