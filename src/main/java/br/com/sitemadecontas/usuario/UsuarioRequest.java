package br.com.sitemadecontas.usuario;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	@Column(unique=true)
	private String nome;
	
	@NotBlank
	@Size(min=3)
	private String senha;

	public UsuarioRequest(int id, @NotBlank String nome, @NotBlank String senha) {
		this.id = id;
		this.nome = nome;
		this.senha = senha;
	}
	
	public UsuarioRequest() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Usuario toModel() {
		return new Usuario(this.id,this.nome,this.senha);
	}
}
