package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class Loja extends Usuario implements Serializable {

	@NotBlank
	//todo arrumar a mensagem
	@Size(min = 11, max = 20, message = "{Size.editora.CNPJ}")
	@Column(nullable = false)
	protected String cnpj;

	@Column(nullable = true, length = 256)
	private String descricao;

}
