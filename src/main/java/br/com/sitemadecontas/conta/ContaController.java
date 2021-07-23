package br.com.sitemadecontas.conta;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import br.com.sitemadecontas.usuario.Usuario;

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
	@GetMapping("/marcarPaga/{id}")
	public ModelAndView marcarP(@PathVariable int id ) {
		ModelAndView mv = new ModelAndView();
		
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			
			if(usuario != null) {
				Conta conta = contaRepository.findById(id);
				if(conta != null && conta.getUsuario().getId() == usuario.getId()) {
					conta.setPaga(true);
					conta.setDataPagamento(new Date());
					contaRepository.save(conta);
				}
				return index();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	@GetMapping("/editar/{id}")
	public ModelAndView editarC(@PathVariable int id ) {
		ModelAndView mv = new ModelAndView();
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			if(usuario != null) {
				Conta conta = contaRepository.findById(id);
				if(conta.getUsuario().getId() == usuario.getId()) {
					mv.setViewName("conta/edita_conta");
					mv.addObject("usuario",usuario);
					mv.addObject("conta", conta);
					return mv;
				}
				return index();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	@PostMapping("/update")
	public ModelAndView updateConta(@Valid ContaRequest contaRequest) {
		ModelAndView mv = new ModelAndView();
		Conta conta = contaRequest.toModel();
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			
			if(usuario != null) {
				if(conta != null) {
					contaRepository.save(conta);
				}
				return index();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
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
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluirConta(@PathVariable int id) {
		ModelAndView mv = new ModelAndView();
		
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			
			if(usuario != null) {
				Conta conta = contaRepository.findById(id);
				if(conta != null && conta.getUsuario().getId() == usuario.getId()) {
					contaRepository.delete(conta);
				}
				return index();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@GetMapping("/nova")
	public ModelAndView nConta() {
		ModelAndView mv = new ModelAndView();
		Usuario  usuario = (Usuario) session.getAttribute("usuarioLogado");
		if(session != null) {
			if(usuario != null) {
				mv.addObject("usuario", usuario);
				mv.setViewName("conta/cad_conta");
				return mv;
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView cadrastarConta(@Valid ContaRequest contaRequest ) {
		ModelAndView mv = new ModelAndView();
		Conta conta = contaRequest.toModel();
		if(session != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
			
			if(usuario != null) {
				if(conta != null) {
					conta.setPaga(false);
					conta.setUsuario(usuario);
					contaRepository.save(conta);
				}
				return index();
			}
		}
		
		mv.setViewName("redirect:/login");
		return mv;
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
