package br.ufscar.dc.dsw1.forms;

import br.ufscar.dc.dsw1.validation.Multipart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CarroForm {
    @NotNull (message = "{nome}")
    private float valor;

    @NotNull (message = "{nome}")
    private float km;

    @NotBlank (message = "{nome}")
    @Size(min = 7, max = 7, message = "{size.carro.placa}")
    private String placa;

    @NotBlank (message = "{nome}")
    private String modelo;

    @NotBlank (message = "{nome}")
    private String descricao;

    @NotBlank (message = "{nome}")
    private String chassi;

    @NotNull (message = "{nome}")
    private int ano;

    @NotNull (message = "{nome}")
    @NotEmpty (message = "{nome}")
    @Multipart (message = "{imagens}")
    private List<MultipartFile> imagens;

    @Override
    public String toString() {
        return "CarroForm{" +
                "valor=" + valor +
                ", km=" + km +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", chassi='" + chassi + '\'' +
                ", ano=" + ano +
                ", imagens=" + imagens +
                '}';
    }
}
