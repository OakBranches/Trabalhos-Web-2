package br.ufscar.dc.dsw1.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
public class FormAccept {

    @NotBlank (message = "{NotBlank}")
    private String data;

    @NotBlank (message = "{NotBlank}")
    private String link;

    @NotBlank (message = "{NotBlank}")
    private String mensagem;


}
