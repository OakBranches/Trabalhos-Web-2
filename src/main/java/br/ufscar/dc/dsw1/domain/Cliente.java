package br.ufscar.dc.dsw1.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente implements Serializable {


	//todo arrumar a mensagem
	@NotNull(message = "")
	@OneToOne
	@JoinColumn(name = "cpf")
	private Usuario usuario;

	//todo arrumar a mensagem
	@NotBlank(message = "")
	@Column(nullable = true, length = 256)
	private String telefone;

	//todo arrumar a mensagem
	@Id
	@NotBlank(message = "")
	@Column(nullable = false, length = 20)
	private String cpf;

	//todo arrumar a mensagem
	@NotBlank(message = "")
	@Column(nullable = false, length = 256)
	private String sexo;


	//todo arrumar a mensagem
	@Column(nullable = false)
	private Date nascimento;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
}
