package br.com.sitemadecontas.usuario;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sitemadecontas.exceptions.CriptoException;
import br.com.sitemadecontas.exceptions.NomeExistsException;
import br.com.sitemadecontas.util.Util;

@Service
public class UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void salva(Usuario usuario) throws Exception{
		try {
			if(usuarioRepository.findByNome(usuario.getNome()) == null) {
				throw new NomeExistsException("Nome de usuário já existente");
			}
			
			usuario.setSenha(Util.md5(usuario.getSenha()));
						
		}catch(NoSuchAlgorithmException e){
			throw new CriptoException("Erro na criptografia da senha");
		}
		
		usuarioRepository.save(usuario);
	}
	
	
}
