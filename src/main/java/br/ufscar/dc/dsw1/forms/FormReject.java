package br.ufscar.dc.dsw1.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FormReject {

    private String valor;
    private String condPag;
    private String mensagem;
}
