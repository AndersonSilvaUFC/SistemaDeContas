package br.com.sitemadecontas.conta;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.sitemadecontas.usuario.Usuario;

@Entity
@Table(name="contas")
public class Conta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private double valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataMaxima;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataPagamento;
	
	@NotNull
	private boolean paga;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	public Conta() {}

	public Conta(int id, @NotBlank String descricao, @NotNull double valor, Date dataMaxima, Date dataPagamento,
			@NotNull boolean paga, @NotNull Usuario usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.dataMaxima = dataMaxima;
		this.dataPagamento = dataPagamento;
		this.paga = paga;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDataMaxima() {
		return dataMaxima;
	}

	public void setDataMaxima(Date dataMaxima) {
		this.dataMaxima = dataMaxima;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public boolean isPaga() {
		return paga;
	}

	public void setPaga(boolean paga) {
		this.paga = paga;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
