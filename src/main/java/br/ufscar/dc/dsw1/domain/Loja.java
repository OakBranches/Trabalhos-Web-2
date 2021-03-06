package br.ufscar.dc.dsw1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//import br.ufscar.dc.dsw.validation.UniqueCNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
@Getter
@Setter

public class Loja extends Usuario implements Serializable {


	@NotBlank(message= "{NotBlank}")

	//todo arrumar a mensagem
	@Size(min = 18, max = 18, message = "{Size.Loja.cnpj}")
	@Column(nullable = false, unique = true)
	protected String cnpj;

	@NotBlank(message= "{NotBlank}")
	@Size(max = 256, message = "{Size.Loja.descricao}")
	@Column(nullable = true, length = 256)
	private String descricao;

	public Loja(){};

	public Loja(String email, String senha, String nome, String cnpj, String descricao) {
		super(email, senha, nome, 2);
		this.cnpj = cnpj;
		this.descricao = descricao;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "loja")
	private List<Carro> carros;

	@Override
	public String toString() {
		return "Loja{" +
				"cnpj='" + cnpj + '\'' +
				", descricao='" + descricao + '\'' +
				", id=" + (id == null ? "null": id) +
				'}';
	}
}
