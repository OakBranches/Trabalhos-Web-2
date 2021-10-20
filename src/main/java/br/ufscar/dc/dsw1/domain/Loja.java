package br.ufscar.dc.dsw1.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import br.ufscar.dc.dsw.validation.UniqueCNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
public class Loja implements Serializable {

	//todo arrumar a mensagem

	@NotNull(message = "")
	@OneToOne
	@JoinColumn(name = "cnpj")
	private Usuario usuario;

	@Id
	@Column(nullable = false, length = 20)
	private String cnpj;

	@Column(nullable = true, length = 256)
	private String descricao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
