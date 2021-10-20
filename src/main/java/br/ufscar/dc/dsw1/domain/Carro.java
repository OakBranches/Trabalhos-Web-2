package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
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
	public Carro(){};
	public Carro(BigDecimal valor, BigDecimal km, String placa, String modelo, String descricao, String chassi, int ano, Loja loja) {
		this.valor = valor;
		this.km = km;
		this.placa = placa;
		this.modelo = modelo;
		this.descricao = descricao;
		this.chassi = chassi;
		this.ano = ano;
		this.loja = loja;
	}

	@Override
	public String toString() {
		return "Carro{" +
				"valor=" + valor +
				", km=" + km +
				", id=" + (id == null ? "null": id) +
				", placa='" + placa + '\'' +
				", modelo='" + modelo + '\'' +
				", descricao='" + descricao + '\'' +
				", chassi='" + chassi + '\'' +
				", ano=" + ano +
				", loja=" + loja +
				'}';
	}
}
