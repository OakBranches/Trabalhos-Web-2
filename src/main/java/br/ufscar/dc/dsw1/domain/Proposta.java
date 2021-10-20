package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "Proposta")
@Inheritance(strategy = InheritanceType.JOINED)
public class Proposta implements Serializable {

	@NotNull
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column
	private int status;

	@NotBlank
	@Column
	private String condPag;


	//todo arrumar a mensagem
	@Column(nullable = false)
	private Date data;

	//todo arrumar a mensagem
	@NotNull
	@OneToOne
	@JoinColumn(name = "cnpj")
	private Loja loja;

	//todo arrumar a mensagem
	@NotNull
	@OneToOne
	@JoinColumn(name = "carro_id")
	private Carro carro;

	//todo arrumar a mensagem
	@NotNull(message = "")
	@OneToOne
	@JoinColumn(name = "cpf")
	private Cliente cliente;
	public Proposta(){};
	public Proposta(BigDecimal valor, int status, String condPag, Date data, Loja loja, Carro carro, Cliente cliente) {
		this.valor = valor;
		this.status = status;
		this.condPag = condPag;
		this.data = data;
		this.loja = loja;
		this.carro = carro;
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Proposta{" +
				"valor=" + valor +
				", id=" + (id == null? "null": id) +
				", status=" + status +
				", condPag='" + condPag + '\'' +
				", data=" + data +
				", loja=" + loja +
				", carro=" + carro +
				", cliente=" + cliente +
				'}';
	}
}
