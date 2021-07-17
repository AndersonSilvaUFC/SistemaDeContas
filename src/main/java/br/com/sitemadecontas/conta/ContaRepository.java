package br.com.sitemadecontas.conta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sitemadecontas.usuario.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer>{
	@Query("select c from Conta c where c.usuario = :usuario and c.paga = :paga")
	List<Conta> findByUsuarioAndPaga(Usuario usuario, boolean paga);
}
