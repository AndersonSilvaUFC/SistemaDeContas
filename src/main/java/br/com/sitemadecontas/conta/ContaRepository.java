package br.com.sitemadecontas.conta;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sitemadecontas.usuario.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer>{
	@Query("select c from Conta c where c.usuario = :usuario and c.paga = :paga")
	List<Conta> findByUsuarioAndPaga(Usuario usuario, boolean paga);
	
	Conta findById(int id);
	
	@Transactional
	@Modifying
	@Query("delete from Conta c where c.usuario = :usuario and c.paga = :paga")
	void deleteAllByUsuarioAndPaga(Usuario usuario, boolean paga);
}
