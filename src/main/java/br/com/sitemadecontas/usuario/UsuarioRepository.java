package br.com.sitemadecontas.usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	@Query("select u from Usuario u where u.nome = :nome")
	public Usuario findByNome(String nome);
	
	@Query("select u from Usuario u where u.nome = :nome and u.senha = :senha")
	public Usuario buscarLogin(String usuario, String senha);
}
