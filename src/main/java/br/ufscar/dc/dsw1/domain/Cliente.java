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

}
