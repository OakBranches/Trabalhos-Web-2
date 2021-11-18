package br.ufscar.dc.dsw1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
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

	@NotNull(message = "{NotBlank}")
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@NotNull(message = "{NotBlank}")
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal km;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{NotBlank}")
	@Size(min = 7, max = 7, message = "{Size.Carro.placa}")
	@Column(nullable = false, length = 10)
	private String placa;

	@NotBlank(message = "{NotBlank}")
	@Column(nullable = false)
	private String modelo;

	@Column
	private String descricao;

	@NotBlank(message = "{NotBlank}")
	@Column(nullable = false)
	private String chassi;

	@NotNull(message = "{NotBlank}")
	@Column(nullable = false)
	private int ano;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Loja loja;

	@JsonIgnore
	@OneToMany(mappedBy = "carro")
	private List<Proposta> propostas;

	@JsonIgnore
	@OneToMany(mappedBy = "carro")
	private List<FileEntity> imagens;

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
		return "{" +
				"\"valor\":" + valor +
				", \"km\":" + km +
				", \"imagens\":" + (imagens != null ? imagens.toString() : "null") +
				", \"id\":" + (id == null ? "null": id) +
				", \"placa\":\"" + placa + "\"" +
				", \"modelo\":\"" + modelo + "\"" +
				", \"descricao\":\"" + descricao + "\"" +
				", \"chassi\":\"" + chassi + "\"" +
				", \"ano\":" + ano +
				", \"loja\":\"" + loja.getNome() + "\"" +
				'}';
	}
}
