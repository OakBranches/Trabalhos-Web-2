package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Size(min = 7, max = 7, message = "{size.carro.placa}")
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

	@NotNull
	@Column(nullable = false)
	private int ano;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Loja loja;

	@OneToMany(mappedBy = "carro")
	private List<Proposta> propostas;

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
