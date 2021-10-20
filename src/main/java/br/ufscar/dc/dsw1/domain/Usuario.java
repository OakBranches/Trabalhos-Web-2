package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {

	@NotBlank
    @Column(nullable = false, length = 256, unique = true)
    private String email;
    
	@NotBlank
    @Column(nullable = false, length = 256)
    private String senha;
       
    @NotBlank
    @Column(nullable = false, length = 256)
    private String nome;

    @Id
    @NotBlank
	//todo arrumar a mensagem
	@Size(min = 11, max = 20, message = "{Size.editora.CNPJ}")
    @Column(nullable = false)
    protected String codigo;
    
    @NotNull
    @Column(nullable = false)
    private int papel;

}