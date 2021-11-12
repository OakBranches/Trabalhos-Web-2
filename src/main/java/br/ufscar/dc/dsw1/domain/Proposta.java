package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "Proposta")
@Inheritance(strategy = InheritanceType.JOINED)
public class Proposta implements Serializable {

	@NotNull(message = "{NotBlank}")
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private int status;

	@NotBlank(message = "{NotBlank}")
	@Column
	private String condPag;

	//todo arrumar a mensagem
	@Column(nullable = false)
	@NotNull(message = "{NotBlank}")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date data;

	//todo arrumar a mensagem
	@ManyToOne
	@JoinColumn(name = "carro_id")
	private Carro carro;

	//todo arrumar a mensagem
	@ManyToOne
	@JoinColumn(name = "cliente_id")

	private Cliente cliente;
	public Proposta(){};
	public Proposta(BigDecimal valor, int status, String condPag, Date data, Carro carro, Cliente cliente) {
		this.valor = valor;
		this.status = status;
		this.condPag = condPag;
		this.data = data;
		this.carro = carro;
		this.cliente = cliente;
	}

	public String getDataStr(Locale locale) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL, locale);
		return dateFormat.format(this.data);
	}

	@Override
	public String toString() {
		return "Proposta{" +
				"valor=" + valor +
				", id=" + (id == null? "null": id) +
				", status=" + status +
				", condPag='" + condPag + '\'' +
				", data=" + data +
				", carro=" + carro +
				", cliente=" + cliente +
				'}';
	}
}
