package br.com.sitemadecontas.conta;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.sitemadecontas.usuario.Usuario;
import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
@RequestMapping("/contas")
public class ContaController {
	
	private HttpSession session ;
	
	@Autowired
	private ContaRepository contaRepository;
	
	public ContaController(ContaRepository contaRepository, HttpSession session) {
		this.contaRepository = contaRepository;
		this.session = session;
	}
	
	@GetMapping("/historico")
	public ModelAndView historico() {
		ModelAndView mv = new ModelAndView();
		
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				mv.setViewName("conta/historico");
				List<Conta> contasPagas = contaRepository.findByUsuarioAndPaga(usuario, true);
				mv.addObject("contasPagas", contasPagas);
				
				return mv;
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	@PostMapping("/contas/marcarPaga/{id}")
	public ModelAndView marcarP(@RequestParam("id") int id ) {
		Conta conta = contaRepository.findById(id);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(conta == null || usuario == null || conta.getUsuario() != usuario )
			return ( new ModelAndView("/contas"));
		conta.setPaga(true);
		contaRepository.save(conta);
		return (new ModelAndView("/contas"));
	}
	@GetMapping("/contas/editar/{id}")
	public ModelAndView editarC(@RequestParam("id") int id ) {
		Conta conta = contaRepository.findById(id);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(conta == null || usuario == null || conta.getUsuario() != usuario )
			return ( new ModelAndView("/contas"));
		
		return (new ModelAndView("/contas/update"));
	}
	@PostMapping("/contas/update")
	public ModelAndView updateConta(@Valid ContaRequest contaRequest) {
		
		contaRepository.save(contaRequest.toModel());
		return (new ModelAndView("/contas"));
	}
	@GetMapping("/historico/excluir/{id}")
	public ModelAndView excluirContaHistorico(@PathVariable int id){
		ModelAndView mv = new ModelAndView();
		
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				Conta conta = contaRepository.findById(id);
				if(conta != null && conta.isPaga() && conta.getUsuario().getId() == usuario.getId()) {
					contaRepository.delete(conta);
				}
				
				
				return historico();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	@GetMapping("/contas/excluir/{id}")
	public ModelAndView excluirConta(@RequestParam("id") int id) {
		ModelAndView mv = new ModelAndView("redirect :/home");
		Conta conta= contaRepository.findById(id);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(conta==null) return mv;
		if(!conta.isPaga() && conta.getUsuario().getId() == usuario.getId()) 
			contaRepository.delete(conta);
		return mv;
		
	}
	@GetMapping("/contas/nova")
	public ModelAndView nConta() {
		return ((new ModelAndView("/conta/cad_conta")).addObject(new Conta()));
	}
	@GetMapping("/conta/cad_conta")
	public ModelAndView cadrastarConta(@Valid ContaRequest contaRequest ) {
		Conta conta = contaRequest.toModel();
		Usuario usuario =  (Usuario) session.getAttribute("usuarioLogado");
		if(usuario == null)  return (new ModelAndView("/usuario/login"));
		conta.setUsuario(usuario);
		contaRepository.save(conta);
		return (new ModelAndView("/contas/nova").addObject("mensagem", 
				"Conta Cadrastada Com Sucesso"));
	}

	
	@GetMapping("/historico/excluirHistorico")
	public ModelAndView excluirHistorico() {
		ModelAndView mv = new ModelAndView();
		
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				
				contaRepository.deleteAllByUsuarioAndPaga(usuario, true);
				
				
				return historico();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@GetMapping("/home")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				int count = (int) session.getAttribute("count");
				mv.setViewName("index");
				List<Conta> contasPendentes = contaRepository.findByUsuarioAndPaga(usuario,false);
				mv.addObject("contasPendentes",contasPendentes);
				session.setAttribute("count", count+1);
				return mv;
			}
		}
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@PostMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("usuario/login");
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				session.setAttribute("usuarioLogado", null);
				session.setAttribute("count", 0);
				session.invalidate();
			}
			mv.addObject("msg", "Seção encerrada");
		}
		return mv;
	}
}
