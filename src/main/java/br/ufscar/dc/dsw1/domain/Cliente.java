package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "Cliente")
public class Cliente extends Usuario implements Serializable {

	@NotBlank
	//todo arrumar a mensagem
	@Size(min = 11, max = 20, message = "{Size.editora.CNPJ}")
	@Column(nullable = false, unique = true)
	protected String cpf;

	//todo arrumar a mensagem
	@NotBlank(message = "")
	@Column(nullable = true, length = 256)
	private String telefone;

	//todo arrumar a mensagem
	@NotBlank(message = "")
	@Column(nullable = false, length = 256)
	private String sexo;


	//todo arrumar a mensagem
	@Column(nullable = false)
	private Date nascimento;
	public Cliente(){};
	public Cliente(String email, String senha, String nome, int papel, String cpf, String telefone, String sexo, Date nascimento) {
		super(email, senha, nome, papel);
		this.cpf = cpf;
		this.telefone = telefone;
		this.sexo = sexo;
		this.nascimento = nascimento;
	}

	@Override
	public String toString() {
		return "Cliente{" +
				"cpf='" + cpf + '\'' +
				", telefone='" + telefone + '\'' +
				", sexo='" + sexo + '\'' +
				", nascimento=" + nascimento +
				", id=" + (id == null ? "null": id) +
				'}';
	}
}
