package br.ufscar.dc.dsw1.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Carro")
public class Carro implements Serializable {

	@NotNull
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@NotNull
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal km;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String placa;

	@NotBlank
	@Column(nullable = false, length = 20)
	private String modelo;

	@Column
	private String descricao;

	@NotBlank
	@Column(nullable = false, length = 20)
	private String chassi;

	@NotBlank
	@Column(nullable = false)
	private int ano;

	@NotNull
	@OneToOne
	@JoinColumn(name = "cnpj")
	private Loja loja;


	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getKm() {
		return km;
	}

	public void setKm(BigDecimal km) {
		this.km = km;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
}
