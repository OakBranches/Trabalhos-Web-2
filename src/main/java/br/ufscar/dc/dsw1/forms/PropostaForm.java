package br.ufscar.dc.dsw1.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
public class PropostaForm {
    @NotNull
    private String condPag;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
    @NotNull
    private float valor;
    @NotNull
    private int cli_id;
    @NotNull
    private int car_id;

    public PropostaForm() {
    }
}